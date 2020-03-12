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
     public static final String KEY = "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAoaXCyeSD0opFnU8AtX6PzHKOLqmAC4VlS1ZkMSQwg/YpnEfmXpwcqBQZ1r6pQdycrgVrTpkDzIOvp4teyBP4awIDAQABAkB7j0ggqPL2iUkDILNrCA4E+f+ivV+p7tJpzuSRB5eqFdXkv+iMLaOGiADstsuPdd2jSYdTxL+nUTx3UvuRmxS5AiEAy3ll1CwrH0FmH00bWplB2ZGag/zDkiiFL2+gyc9PNjcCIQDLYD5M0mR7OxyWrH3VBF4rP2rIbSsXGJyNkuhqa6O1bQIhAK94OHV5xZJFrtDNiQpfYidDZpHuN6XipcCbrnh6/B+PAiBGejC8wIAvsDllAZuNkTjs9coUcLhpBY9jBFDkaC7QNQIhALEmWAaacHL6gLeSNdzF7boG/IxAw8VFTAdM0Mcwo5Ja";
    
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
