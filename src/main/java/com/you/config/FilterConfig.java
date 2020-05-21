package com.you.config;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.you.filter.CharacterEncodingFilter;
import com.you.filter.SignAuthFilter;



/**
 * 
    * @ClassName: FilterConfig  
    * @Description: 过滤器配置类，当有多个过滤器时，1、如果指定了 Order 属性，执行的顺序与注册的顺序是无关的；2、数字越小，优先级越高；
    * @author you  
    * @date 2020年2月22日  
    *
 */

@Configuration
public class FilterConfig
{
    
    
    /**
     * 可能有多个Filter,所有建议都给名字,否则会冲突
     * 设置成了泛型
     * @return
     */
    
    
    @Bean
    public FilterRegistrationBean<?> signAuthFilterBean() {
      FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
      // 设置过滤器
      filterRegistrationBean.setFilter(signAuthFilter());
      // 过滤器的名称
      filterRegistrationBean.setName("signAuthFilter");
      // 设置初始化参数
      //filterRegistrationBean.addInitParameter("paramName", "paramValue");
      // 过滤器过滤路径配置，这里设置的是所有的请求都进行过滤
      filterRegistrationBean.addUrlPatterns("/*");
      //doFilter 设置Order为2，第二个执行
      filterRegistrationBean.setOrder(2);
      return filterRegistrationBean;
    }
    
    @Bean
    public FilterRegistrationBean<?> characterFilterBean() {
      FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
      // 设置过滤器
      filterRegistrationBean.setFilter(characterFilter());
      // 过滤器的名称
      filterRegistrationBean.setName("characterFilter");
      // 设置初始化参数
      filterRegistrationBean.addInitParameter("charset", "UTF-8");
      // 过滤器过滤路径配置，这里设置的是所有的请求都进行过滤
      filterRegistrationBean.addUrlPatterns("/*");
      //doFilter 设置Order为1，第一个执行
      filterRegistrationBean.setOrder(1);
      return filterRegistrationBean;
    }
    
    @Bean
    public Filter signAuthFilter() {
      return new SignAuthFilter();
    }
    
    @Bean
    public Filter characterFilter() {
      return new CharacterEncodingFilter();
    }
}
