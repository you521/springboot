package com.you.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.you.common.annotation.PassToken;
import com.you.bean.configbean.JwtConfigBean;
import com.you.common.constance.Constants;
import com.you.common.model.HttpStatus;
import com.you.common.model.ResponseModel;
import com.you.util.AnnotationUtil;
import com.you.util.FastJsonUtil;
import com.you.util.JwtTokenUtil;
import com.you.util.RedisUtil;
import com.you.util.ResultUtil;

import io.jsonwebtoken.Claims;

/**
 * 
    * @ClassName: TokenInterceptor  
    * @Description: 自定义获取token拦截器  
    * @author you  
    * @date 2020年3月6日  
    *
 */

public class TokenInterceptor implements HandlerInterceptor
{
    private static final Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);
    
    @Autowired
    private JwtConfigBean jwtConfigBean;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private FastJsonUtil fastJsonUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AnnotationUtil annotationUtil;
    
    /*
     * 进入controller层之前拦截请求
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("************TokenInterceptor preHandle executed**********");
        System.out.println("getContextPath:" + request.getContextPath());
        System.out.println("getServletPath:" + request.getServletPath());
        System.out.println("getRequestURI:" + request.getRequestURI());
        System.out.println("getRequestURL:" + request.getRequestURL());
        
        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        // 检查是否有passtoken注释，有注解并且默认值为true则跳过认证，如果注解值为false则不跳过验证
        if (annotationUtil.isAnnotationPresent(handler, PassToken.class)) {
            PassToken passToken = annotationUtil.getAnnotation(handler,PassToken.class);
            if (passToken.value()) {
                return true;
            }
        }
        // 获取request请求头中的token
        String token = request.getHeader(jwtConfigBean.getHeader());
        if(StringUtils.isBlank(token))
        {
            logger.error("=========================token令牌不能为空===========================");
            HttpStatus httpStatus = HttpStatus.TOKEN_ERROR;
            ResponseModel responseModel = ResultUtil.error(httpStatus.getCode(),"token令牌不能为空");
            // 返回错误信息
            this.returnJson(response, responseModel);
            return false;
        }
        token = token.substring(7).trim();
        // 解析token
        Claims claims = jwtTokenUtil.parseTocken(token);
        if(claims == null || claims.isEmpty())
        {
            logger.error("=========================token令牌失效===========================");
            // token令牌失效
            HttpStatus httpStatus = HttpStatus.TOKEN_ERROR;
            ResponseModel responseModel = ResultUtil.error(httpStatus.getCode(),httpStatus.getMessage());
            // 返回错误信息
            this.returnJson(response, responseModel);
            return false; 
        } else {
            /**
             * redis刷新token策略：在登录生成token的时候，将token作为value值存储在redis，在redis设置key的过期时间
             * 是token过期期间的2倍；多出的时间是token的刷新时间
             * 当校验请求中的token时，如果token没过期，然后查询redis中的token是否失效，如果没有失效，则重新刷新生成一个token存储在Redis中，
             * 同时将生成的token返给前端，前端获取到刷新后的token继续请求接口
             * 如果redis中的token也失效，则用户需要重新登录获取token值
             */
            // 获取用户的userId
            String userId = jwtTokenUtil.getUserIdByToken(token);
            String key = Constants.JWT_TOKEN_KEY+userId;
            // 从redis中查询 token
            String redis_token = (String) redisUtil.get(key);
            // 判断token是否过期
            if(jwtTokenUtil.isTokenExpired(token) && redis_token != null && token.equals(redis_token))
            {
                logger.error("=========================进入token令牌刷新策略===========================");
                //生成刷新后的token
                String refresh_token = jwtTokenUtil.refreshToken(token);
                if(redisUtil.set(key,refresh_token.substring(7).trim(),jwtConfigBean.getExpire()*2))
                {
                    logger.error("=========================token令牌刷新成功===========================");
                    HttpStatus httpStatus = HttpStatus.TOKEN_REFRESH;
                    ResponseModel responseModel = ResultUtil.success(httpStatus.getCode(),httpStatus.getMessage(),refresh_token);
                    // 返回刷新token信息
                    this.returnJson(response, responseModel);
                }
                return false;
            }
            // 如果token没过期，redis中的token没有失效，校验两个token是否相等，防止请求token被篡改 
            if(!token.equals(redis_token))
            {
                logger.error("=========================token令牌异常===========================");
                // token令牌异常
                HttpStatus httpStatus = HttpStatus.TOKEN_ERROR;
                ResponseModel responseModel = ResultUtil.error(httpStatus.getCode(),httpStatus.getMessage());
                // 返回错误信息
                this.returnJson(response, responseModel);
                return false; 
            }
        }
        // 返回true,postHandler和afterCompletion方法才能执行
        // 否则false为拒绝执行，起到拦截器控制作用
        return true;
    }
    
    /*
     * 处理请求完成后视图渲染之前的处理操作,但只有preHandle方法返回true的时候才会执行
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        System.out.println("************TokenInterceptor postHandle executed**********");
    }
    
    /*
     * 视图渲染之后的操作,同样需要preHandle返回true
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
       System.out.println("************TokenInterceptor afterCompletion executed**********");
    }
    
    /*
     *  返给前端json数据
     */
    private void returnJson(HttpServletResponse response, ResponseModel responseModel){
        PrintWriter printWriter = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            printWriter = response.getWriter();
            // 将responseModel对象转成json字符串
            printWriter.write(fastJsonUtil.getBeanToJson(responseModel));
        } catch (IOException e){
            logger.error("拦截器输出流异常：{}"+e);
        } finally {
            if(printWriter != null)
            {
                printWriter.close();
            }
        }
    }
}
