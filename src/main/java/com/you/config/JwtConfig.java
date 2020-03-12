package com.you.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.you.bean.User;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt") // 配置 文件的前缀
public class JwtConfig
{
    /**
     * 用户凭证
     */
    private String header;
    /**
     * 加密秘钥
     */
    private String secret;
    /**
     * 过期时间
     */
    private int expire;
    /**
     * token前缀
     */
    private String tokenPrefix;
    /**
     * 用户列表
     */
    private List<User> userlist;
}
