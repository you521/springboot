package com.you.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @ClassName: PassToken  
 * @Description: 该注解用来跳过验证的Token,也就是说如果controller层的某个方法上 使用该注解，请求该方法时则不会校验token值
 * @author you  
 * @date 2020年3月28日  
 *
 */

/**
 * 自定义注解类编写的一些规则:
 *   1.Annotation型 (注解类) 需定义为@interface, 所有的注解类会自动继承java.lang.Annotation接口,并且不能再去继承别的类或是接口.
 *   2.参数成员只能用public或默认(default)这两个访问权修饰
 *   3.参数成员只能用基本类型byte,short,char,int,long,float,double,boolean八种基本数据类型和String、Enum、Class、annotations等数据类型,以及这一些类型的数组.
 *   4.要获取类方法和字段的注解信息，必须通过Java的反射机制来获取 -Annotation对象,因为除此之外没有别的方法获取注解对象
 */

/*
 * @Documented注解：标明自定义注解会被javadoc或者其他类似工具文档化，只负责标记，没有成员取值
 */

/*
 * @Retention注解：标明自定义的注解的生存周期，即会保留到哪个阶段，它的取值有三种；
 * RetentionPolicy.SOURCE:这种类型的Annotations只在源代码级别保留,编译时就会被忽略,在class字节码文件中不包含。
 * RetentionPolicy.CLASS:这种类型的Annotations编译时被保留,默认的保留策略,在class文件中存在,但JVM将会忽略,运行时无法获得。
 * RetentionPolicy.RUNTIME:这种类型的Annotations将被JVM保留,所以他们能在运行时被JVM或其他使用反射机制的代码所读取和使用。
 */

/*
 * @Target注解:标明了自定义的这个注解的使用范围，即被描述的注解可以用在哪里
 * @Target(ElementType.TYPE) ——————>接口、类、枚举、注解
 * @Target(ElementType.FIELD) ——————>字段、枚举的常量
 * @Target(ElementType.METHOD) ——————>方法
 * @Target(ElementType.PARAMETER) ——————>方法参数
 * @Target(ElementType.CONSTRUCTOR) ——————>构造函数
 * @Target(ElementType.LOCAL_VARIABLE) ——————>局部变量
 * @Target(ElementType.ANNOTATION_TYPE) ——————>注解类型
 * @Target(ElementType.PACKAGE) ——————>包
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.FIELD})
public @interface PassToken
{
    // 默认值为true
    boolean value() default true;
}
