package com.you.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.you.common.annotation.PassToken;
import com.you.bean.configbean.SparkConfigBean;
import com.you.common.model.ResponseModel;
import com.you.util.ResultUtil;

/**
 * 
    * @ClassName: SampleController  
    * @Description: spring boot 测试类 
    * @author you  
    * @date 2018年11月10日  
    *
 */
@RestController
@RequestMapping("/api/v1/yibaotong")
public class SampleController
{
    @Autowired
    private SparkConfigBean sparkConfigBean;
    
    @PassToken()
    @RequestMapping(value="/save",produces = "application/json;charset=UTF-8")
    public ResponseModel home(@RequestBody JSONObject jsonObject) {
        System.out.println("-------------sparkConfigBean------------"+sparkConfigBean.getMaster());
        System.out.println("-------------sparkConfigBean------------"+sparkConfigBean.getSparkName());
        String name = jsonObject.getString("name");
        return ResultUtil.success(name);
    }
}
