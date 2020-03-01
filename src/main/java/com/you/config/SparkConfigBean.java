package com.you.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@PropertySource(value ={"classpath:config/spark.properties",
                        "file:${spring.profiles.path}/config/spark.properties"},
                        ignoreResourceNotFound=true) // 加载文件的路径
@ConfigurationProperties(prefix = "spark") // 配置 文件的前缀
public class SparkConfigBean
{
    private String sparkName;
    
    private String master;
}
