package com.you.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.you.bean.configbean.SparkConfigBean;

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
    
    @RequestMapping(value="/save",produces = "application/json;charset=UTF-8")
    public String home() {
        System.out.println("-------------sparkConfigBean------------"+sparkConfigBean.getMaster());
        System.out.println("-------------sparkConfigBean------------"+sparkConfigBean.getSparkName());
       return "Welcome to spring boot!";
    }
}
