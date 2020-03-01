package com.you.util;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


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
    
}
