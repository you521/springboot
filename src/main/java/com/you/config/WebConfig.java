package com.you.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.you.bean.configbean.InterceptorPathBean;
import com.you.interceptor.TokenInterceptor;


/**
 * 
    * @ClassName: WebConfig  
    * @Description: 拦截器相关配置,相当于spring mvc的配置文件
    * @author you  
    * @date 2020年3月6日  
    *
 */

@Configuration
public class WebConfig implements WebMvcConfigurer
{
   
    @Autowired
    private InterceptorPathBean interceptorPathBean;


    // 以下WebMvcConfigurerAdapter 比较常用的重写接口
    // /** 解决跨域问题 **/
    // public void addCorsMappings(CorsRegistry registry);
    // /** 添加拦截器 **/
    // void addInterceptors(InterceptorRegistry registry);
    // /** 配置视图解析器 **/
    // void configureViewResolvers(ViewResolverRegistry registry);
    // /** 配置内容裁决的一些选项 **/
    // void configureContentNegotiation(ContentNegotiationConfigurer configurer);
    // /** 视图跳转控制器 **/
    // void addViewControllers(ViewControllerRegistry registry);
    // /** 静态资源处理 **/
    // void addResourceHandlers(ResourceHandlerRegistry registry);
    // /** 默认静态资源处理器 **/
    // void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer);
    
    /**
     * 表示这些配置的表示静态文件所处路径， 不用拦截
     * 注意：这段代码同时也指定了项目静态资源的访问路径，所以可以不用在application.yml文件中配置：spring.mvc.static-path-pattern=/static/**
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // addResourceLocations指的是文件放置的目录，addResoureHandler指的是对外暴露的访问路径
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/templates/**")
                .addResourceLocations("classpath:/templates/");
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor())
                 // 设置需要拦截的路径
                 // 这里add"/**"(拦截所有请求),下面的exclude才起作用，且不管controller层是否能匹配客户端请求,拦截器都起作用拦截
                 .addPathPatterns(interceptorPathBean.getInclude())   //  拦截
                 .excludePathPatterns(interceptorPathBean.getExclude());  //  不拦截
    }
    
    @Bean
    public TokenInterceptor tokenInterceptor() {
        return new TokenInterceptor();
    }
}
