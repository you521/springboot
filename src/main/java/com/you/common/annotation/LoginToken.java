package com.you.common.annotation;

/**
 * 
    * @ClassName: LoginToken  
    * @Description: 该注解是需要登录才能进行操作，也是说如果controller层的某个方法使用了该注解
    *               当请求该方法时，必须先进行登录操作获取权限
    * @author you  
    * @date 2020年3月28日  
    *
 */

public @interface LoginToken
{
    // 默认值为true
    boolean value() default true;
}
