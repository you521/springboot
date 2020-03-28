package com.you.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.you.bean.User;
import com.you.bean.configbean.JwtConfigBean;
import com.you.common.constance.Constants;
import com.you.service.TokenManagerService;
import com.you.util.JwtTokenUtil;
import com.you.util.RedisUtil;

@Service
public class TokenManagerServiceImpl implements TokenManagerService
{
    private static final Logger logger = LoggerFactory.getLogger(TokenManagerServiceImpl.class); 
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private JwtConfigBean jwtConfigBean;
    
    @Override
    public String createToken(User user)
    {
        String token = null;
        try
        {
            String key = Constants.JWT_TOKEN_KEY+user.getAppId();
            // 在redis中查询该用户是否登录
            String redis_token = (String) redisUtil.get(key);
            // 如果redis_token为空说明没有登录
            if(redis_token == null)
            {
                String create_token = jwtTokenUtil.generateToken(user);
                boolean flag = redisUtil.set(key, create_token.substring(7).trim(), jwtConfigBean.getExpire()*2);
                if(flag)
                {
                    return create_token;
                }
            }
        } catch (Exception e)
        {
           logger.error("service层生成token失败：{}"+e);
           return token;
        }
        return token;
    }

    @Override
    public boolean loginOut(String token)
    {
        boolean flag = false;
        try
        {
            String userId = jwtTokenUtil.getUserIdByToken(token.substring(7).trim());
            if(redisUtil.hasKey(Constants.JWT_TOKEN_KEY+userId))
            {
                redisUtil.delete(Constants.JWT_TOKEN_KEY+userId);
                flag = true;
            }
        } catch (Exception e)
        {
           logger.error("退出登录抛出异常：{}"+e);
           e.printStackTrace();
           return flag;
        }
        return flag;
    }
}
