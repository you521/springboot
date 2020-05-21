package com.you.service;

import com.you.bean.User;

public interface TokenManagerService
{

    String createToken(User user);

    boolean loginOut(String token);

}
