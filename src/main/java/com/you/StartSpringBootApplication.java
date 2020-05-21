package com.you;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

/*
 * 
    * @ClassName: MySpringBootApplication  
    * @Description: 程序主入口
    * @author you  
    * @date 2020年2月27日  
    *
 */
@Slf4j
@SpringBootApplication
public class StartSpringBootApplication {
      /**
        * @Title:   
        * @Description: springboot项目的启动类
        * @param @param args  参数  
        * @return void    返回类型  
        * @throws
     */
	public static void main(String[] args) {
	    log.info("---------------main函数启动开始-----------------");
	    SpringApplication.run(StartSpringBootApplication.class, args);
	    log.info("---------------main函数启动结束-----------------");
	}
}
