package com.you.util;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;


/**
 * 
    * @ClassName: FastJsonUtil  
    * @Description:  使用阿里json解析工具类   
    * @author you  
    * @date 2019年7月5日  
    *
 */

@Component
public class FastJsonUtil
{
    
    /**
     * 
        * @Title: json2JSONObject  
        * @Description: 简单的JSON字符串转JSON对象
        * @param @param json
        * @param @return    参数  
        * @return JSONObject    返回类型  
        * @throws
     */
    public JSONObject json2JSONObject(String json) {
        return JSON.parseObject(json);
    }
    
    /**
     * 
        * @Title: jsonToJSONArray  
        * @Description: json字符串转json数组
        * @param @param json
        * @param @return    参数  
        * @return JSONArray    返回类型  
        * @throws
     */
    public JSONArray jsonToJSONArray(String json) {
        return JSON.parseArray(json);
    }
    
    /**
     * 
        * @Title: getBeanToJson  
        * @Description: 把java对象转换成JSON数据  
        * @param @param object
        * @param @return    参数  
        * @return String    返回类型  
        * @throws
     */
    public String getBeanToJson(Object object) {
        return JSON.toJSONString(object);
    }
    
    /**
     * 
        * @Title: jsonToBean  
        * @Description: json字符串转java复杂对象 
        * @param @param json
        * @param @return    参数  
        * @return T    返回类型  
        * @throws
     */
    public <T> T jsonToBean(String json) {
        return JSON.parseObject(json,new TypeReference<T>(){});  
    }
    
    /**
     * 
        * @Title: jsonToBean  
        * @Description: json字符串转java简单对象   
        * @param @param json
        * @param @param clazz
        *               调用方式：fastJsonUtil.jsonToBean(json,User.class);
        * @param @return    参数  
        * @return T    返回类型  
        * @throws
     */
    public <T> T jsonToBean(String json,Class<T> clazz) {
        return JSON.parseObject(json,clazz);
    }
    
    /**
     * 
        * @Title: mapToString  
        * @Description: map转成string字符串  
        * @param @param map
        * @param @return    参数  
        * @return String    返回类型  
        * @throws
     */
    public String mapToString(Map<String , Object> map) {
        return JSON.toJSONString(map);
    }
    
    /**
     * 
        * @Title: stringToMap  
        * @Description:   将string字符串转成map
        * @param @param str
        * @param @return    参数  
        * @return Map<String,Object>    map  
        * @throws
     */
    @SuppressWarnings("unchecked")
    public Map<String , Object> stringToMap(String str) {
        return JSON.parseObject(str, Map.class);
    }
    
    /**
     * 
        * @Title: listToJson  
        * @Description: list集合转json字符串
        * @param @param list
        * @param @return    参数  
        * @return String    返回类型  
        * @throws
     */
    public <T> String listToJson(List<T> list) {
        return JSON.toJSONString(list); 
    }
    
    /**
     * 
        * @Title: jsonToList  
        * @Description: json字符串转List集合  
        * @param json
        * @param @return    参数  
        * @return List<T>    返回类型  
        * @throws
     */
    public <T> List<T> jsonToList(String json) {
        return JSON.parseObject(json,new TypeReference<List<T>>(){});  
    }
    
    /**
     * 
        * @Title: jsonToList  
        * @Description: json字符串转list集合
        * @param @param json
        * @param @param clazz class类的泛型参数
        *               调用方式：fastJsonUtil.jsonToList(json,User.class);
        * @param @return    参数  
        * @return List<T>    返回类型  
        * @throws
     */
    public <T> List<T> jsonToList(String json,Class<T> clazz) {
        return JSON.parseArray(json,clazz);
    }
}
