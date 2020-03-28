package com.you.bean.configbean;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "interceptorconfig.path") // 配置 文件的前缀
public class InterceptorPathBean
{
    
    /*
     * 需要拦截的路径
     */
    private List<String> include;
    
    /*
     * 不需要拦截的路径
     */
    private List<String> exclude;
}
