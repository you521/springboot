package com.you.bean.configbean;

import java.io.Serializable;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.you.bean.User;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "jwt") // 配置 文件的前缀
public class JwtConfigBean implements Serializable
{
    private static final long serialVersionUID = 1L;
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
    /*
     * 签发者
     */
    private String issuer;
    /**
     * 用户列表
     */
    private List<User> userlist;
}
