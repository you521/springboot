package com.you.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
    * @ClassName: TokenInterceptor  
    * @Description: 自定义获取token拦截器  
    * @author you  
    * @date 2020年3月6日  
    *
 */

@Component
public class TokenInterceptor implements HandlerInterceptor
{
    
    /*
     * 进入controller层之前拦截请求
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      
//        System.out.println("getContextPath:" + request.getContextPath());
//        System.out.println("getServletPath:" + request.getServletPath());
//        System.out.println("getRequestURI:" + request.getRequestURI());
//        System.out.println("getRequestURL:" + request.getRequestURL());
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

    }
    
    /*
     * 视图渲染之后的操作,同样需要preHandle返回true
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }
}
