package com.you.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
    * @ClassName: CharacterEncodingFilter  
    * @Description: 通过过滤器设置全局请求字符编码  
    * @Order()  设置过滤器的优先执行顺序，数值越小，优先级别越高
    * @WebFilter  声明该类是个过滤器
    * @author you  
    * @date 2020年2月23日  
    *
 */

//@Order(1)
//@WebFilter(urlPatterns = "/*", filterName = "CharacterEncodingFilter",initParams = {@WebInitParam(name = "charset", value = "UTF-8")})
public class CharacterEncodingFilter implements Filter
{
    
    private static final Logger logger = LoggerFactory.getLogger(CharacterEncodingFilter.class);
    // 初始化变量
    private String charset;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("------------初始化 CharacterEncodingFilter------------");
        // 初始化字符编码过滤器时初始化编码参数
        this.charset = filterConfig.getInitParameter("charset");
    }
 
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        request.setCharacterEncoding(charset);
        response.setCharacterEncoding(charset);
        System.out.println("我是字符编码过滤器的执行方法，客户端向Servlet发送的请求被我拦截到了");
        filterChain.doFilter(request, response);
        System.out.println("我是字符编码过滤器的执行方法，Servlet向客户端发送的响应被我拦截到了");
    }
    
    @Override
    public void destroy() {
        logger.info("-------------销毁 CharacterEncodingFilter------------");
    }

}
