package com.you.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.you.bean.User;
import com.you.bean.configbean.JwtConfigBean;

import com.you.common.model.ResponseModel;
import com.you.service.TokenManagerService;
import com.you.util.ResultUtil;

@RestController
@RequestMapping("/api/v1/token")
public class TokenController
{
    
     @Autowired
     private JwtConfigBean jwtConfigBean;
     @Autowired
     private TokenManagerService tokenService;
    
     /**
      * 
         * @Title: getApiToken  
         * @Description: 用户获取token
         * @param @param map
         * @param @return    参数  
         * @return ResponseModel    返回类型  
         * @throws
      */
     @PostMapping(value="/api_token",produces = "application/json;charset=UTF-8")
     public ResponseModel getApiToken(@RequestBody Map<String , String> map) {
        String appId = map.get("appId");
        String password = map.get("password");
        // isBlank方法当时空白字符时也返回true
        if(StringUtils.isBlank(appId))
        {
           return ResultUtil.error("appId不能为空");
        }
        User user = getUser(appId);
        if(user == null)
        {
           return ResultUtil.error("appId不存在");
        } else if (!password.equals(user.getPassword().trim())) 
        {
           return ResultUtil.error("用户密码错误");
        }
        // 获取token
        String token = tokenService.createToken(user);
        if(token == null)
        {
            return ResultUtil.error("获取token令牌失败");
        }
        return ResultUtil.success(token);
     }
     
     /**
      * 
         * @Title: loginOut  
         * @Description: 用户退出登录  
         * @param @param request
         * @param @return    参数  
         * @return ResponseModel    返回类型  
         * @throws
      */
     @PostMapping(value="/api_loginout",produces = "application/json;charset=UTF-8")
     public ResponseModel loginOut(HttpServletRequest request) {
         String token = request.getHeader("Authorization");
         boolean flag = tokenService.loginOut(token);
         if(flag)
        {
          return ResultUtil.success("退出登录成功");
        } else {
          return ResultUtil.error("退出登录失败"); 
        }
     }
     
     private User getUser(String appId) {
         List<User> listUser = jwtConfigBean.getUserlist();
         User user = null;
         // 判断用户是否存在
         for (User str : listUser)
         {
             if(appId.equals(str.getAppId().trim()))
             {
                user = str;
                break;
             }
         }
         return user;
     }
}
