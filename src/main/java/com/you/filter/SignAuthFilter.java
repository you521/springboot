package com.you.filter;

import java.io.IOException;

import javax.servlet.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.you.config.SparkConfigBean;



/**
 * 
    * @ClassName: SignAuthFilter  
    * @Description: 签名过滤器 
    * @author you  
    * @date 2020年2月22日  
    *
 */

public class SignAuthFilter implements  Filter
{
    
    private static final Logger logger = LoggerFactory.getLogger(SignAuthFilter.class);
    
    /**
     * spring容器初始化bean对象的顺序是listener-->filter-->servlet
     * 所以不能使用@Autowired或@Resource注入spring容器bean
     */
    @Autowired
    private SparkConfigBean sparkConfigBean;
    
    /**
     * 可以初始化Filter在web.xml里面配置的初始化参数，当然spring boot是没有web.xml文件的
     * filter对象只会创建一次，init方法也只会执行一次
     */
    @Override
    public void init(FilterConfig filterConfig) {
        logger.info("------------初始化 SignAuthFilter------------");
        // 往filter过滤器中注入spring容器bean
        ServletContext servletContext  = filterConfig.getServletContext();
        ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(servletContext );
        sparkConfigBean = ac.getBean(SparkConfigBean.class);
    }

    /**
     * 主要业务代码编写方法
     */
    @Override
    public void doFilter(ServletRequest request , ServletResponse response , FilterChain filterChain)
                    throws IOException , ServletException
    {
        System.out.println("我是过滤器的执行方法，客户端向Servlet发送的请求被我拦截到了");
        logger.info("--------sparkConfigBean--------"+sparkConfigBean.getMaster());
        filterChain.doFilter(request, response);
        System.out.println("我是过滤器的执行方法，Servlet向客户端发送的响应被我拦截到了");
    }

    /**
     * 在销毁Filter时自动调用
     */
    @Override
    public void destroy() {
        logger.info("-------------销毁 SignAuthFilter------------");
    }
}
