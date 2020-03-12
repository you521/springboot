package com.you.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.you.bean.User;
import com.you.model.HttpStatus;
import com.you.model.ResponseDataModel;
import com.you.model.ResponseModel;
import com.you.util.ResultUtil;

@RestController
@RequestMapping("/api/v1/token")
public class TokenController
{
     @PostMapping(value="/api_token",produces = "application/json;charset=UTF-8")
     public ResponseDataModel getApiToken(HttpServletRequest request) {
         String appId = request.getParameter("appId");
         System.out.println("appId------------->"+appId);
         User user = new User();
         user.setAppId("yibaotong");
         user.setPassword("123456");
         return ResultUtil.success(user);
     }
}
