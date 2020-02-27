package com.you;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * 
    * @ClassName: MySpringBootApplication  
    * @Description: 程序主入口
    * @author you  
    * @date 2020年2月27日  
    *
 */

@SpringBootApplication
public class MySpringBootApplication {
      /**
        * @Title:   
        * @Description: springboot项目的启动类
        * @param @param args  参数  
        * @return void    返回类型  
        * @throws
     */
	public static void main(String[] args) {
	    SpringApplication.run(MySpringBootApplication.class, args);
	}
}
