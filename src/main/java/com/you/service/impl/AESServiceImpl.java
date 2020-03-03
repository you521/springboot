package com.you.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.you.service.AESService;
import com.you.util.AESUtil;

@Service
public class AESServiceImpl implements AESService
{
     /*
      * 约定的密钥
      */
     public static final String KEY = "h@xl(1cfM:_Bj%12!)QTlzRYzsD";
    
     @Autowired
     private AESUtil aESUtil;

    @Override
    public String getEncrypt(String str)
    {
        String result = null;
        try
        {
            result = aESUtil.encryptToBase64(str, KEY);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String getDecrypt(String str)
    {
        String result = null;
        try
        {
            result = aESUtil.decryptToBase64(str, KEY);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }
}
