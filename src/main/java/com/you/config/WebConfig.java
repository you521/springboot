package com.you.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.you.bean.configbean.InterceptorPathBean;
import com.you.interceptor.TokenInterceptor;


/**
 * 
    * @ClassName: WebConfig  
    * @Description: 拦截器相关配置
    * @author you  
    * @date 2020年3月6日  
    *
 */

@Configuration
public class WebConfig implements WebMvcConfigurer
{
    // 不拦截路径如果是多个，要设置成数组形式或者是集合形式
    //private static final String[] excludePathPatterns  = {"/api/token/api_token","/api/yibaotong/save"};
    
    @Autowired
    private InterceptorPathBean interceptorPathBean;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor())
                 // 设置需要拦截的路径
                 // 这里add"/**",下面的exclude才起作用，且不管controller层是否能匹配客户端请求,拦截器都起作用拦截
                 //.addPathPatterns("/**")
                 //.excludePathPatterns(excludePathPatterns);
                 // 如果add为具体的拦截,如“/hello”时,exclude不起作用
                 // 且controller层不匹配客户端请求时,拦截器不起作用
                 //.addPathPatterns("/api/v1/token/api_token");
                 .excludePathPatterns(interceptorPathBean.getExclude())   //  不拦截
                 .addPathPatterns(interceptorPathBean.getInclude()); //  拦截
    }
    @Bean
    public TokenInterceptor tokenInterceptor() {
        return new TokenInterceptor();
    }
}
