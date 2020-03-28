package com.you.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

/**
 * 
    * @ClassName: AnnotationUtil  
    * @Description: 注解工具类
    * @author you  
    * @date 2020年3月28日  
    *
 */

@Component
public class AnnotationUtil
{
     /**
      * 
         * @Title: getAnnotation  
         * @Description: 获取自定义注解中的值
         * @param @param object 拦截器中的定义参数
         * @param @param annotationClass 自定义的注解类型
         * @param @return    参数  
         * @return T    返回类型  返回一个自定义注解类（继承Annotation接口的注解类）
         * @throws
      */
     public <T extends Annotation> T getAnnotation(Object object, Class<T> annotationClass) {
        // 判断是不是映射到controller层的方法上
        if(object instanceof HandlerMethod)
        {
            HandlerMethod handlerMethod = (HandlerMethod) object;
            Method method = handlerMethod.getMethod();
            return method.getAnnotation(annotationClass);
        }
        return null;
     }
     
     /**
      * 
         * @Title: isAnnotationPresent  
         * @Description: 判断controller层的方法是否有自定义注解
         * @param @param object 拦截器中的定义参数
         * @param @param annotationClass annotationClass 自定义的注解类型（继承Annotation接口的注解类）
         * @param @return    参数  
         * @return boolean    返回类型  true或者false
         * @throws
      */
     public boolean isAnnotationPresent(Object object, Class<? extends Annotation> annotationClass) {
         boolean flag = false;
         // 判断是不是映射到controller层的方法上
         if(object instanceof HandlerMethod)
         {
             HandlerMethod handlerMethod = (HandlerMethod) object;
             Method method = handlerMethod.getMethod();
             flag =  method.isAnnotationPresent(annotationClass);
             return flag;
         }
         return flag;
      }
}
