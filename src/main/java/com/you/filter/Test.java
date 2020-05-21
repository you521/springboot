package com.you.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;



public class Test
{

    public static void main(String[] args)
    {
        // 定义list集合
        List<String> list = new ArrayList<String>();
        list.add("jia");
        list.add("shao");
        list.add("you");
        // 将list集合转成字符串
        String string = StringUtils.join(list.toArray(),",");
        System.out.println(string);
        // 先将字符串变成数组
        String[] strArray = string.split(",");
        // 数组转成list集合
        List<String> list1 = Arrays.asList(strArray);
        System.out.println(list1);
        boolean flag = false;
        if(list1.contains("jia"))
        {
            flag = true;
        }
        System.out.println(flag);
        if(!list1.contains("1"))
        {
            flag = false;
        }
        System.out.println(flag);
    }

}
