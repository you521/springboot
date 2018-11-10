package com.you.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
    * @ClassName: SampleController  
    * @Description: spring boot 测试类 
    * @author you  
    * @date 2018年11月10日  
    *
 */
@RestController
public class SampleController
{
       @RequestMapping(value="/index")
       public String home() {
           return "Welcome to spring boot!";
       }
}
