package com.you.util;


import com.you.model.HttpStatus;
import com.you.model.ResponseDataModel;
import com.you.model.ResponseModel;


/**
 * 
    * @ClassName: ResultUtil  
    * @Description: 定义返回响应工具类  
    * @author you  
    * @date 2020年3月12日  
    *
 */

public class ResultUtil
{
    /*
     * 默认返回调用成功，不需要自己设置状态码和信息说明(无实体数据data返回)
     */
    public static ResponseModel success(){
        HttpStatus httpStatus = HttpStatus.SUCCESS;
        return new ResponseModel(httpStatus.getCode(),httpStatus.getMessage());
    }
    
    /*
     * 返回调用成功，需要自己设置成功信息说明(无实体数据data返回)
     */
    public static ResponseModel success(String resultMessage){
        HttpStatus httpStatus = HttpStatus.SUCCESS;
        return new ResponseModel(httpStatus.getCode(),resultMessage);
    }
    
    /*
     * 返回调用成功，需要自己设置状态码和成功信息说明(无实体数据data返回)
     */
    public static ResponseModel success(Integer resultCode, String resultMessage){
        return new ResponseModel(resultCode,resultMessage);
    }
    
    /*
     * 默认返回调用失败，不需要自己设置状态码和信息说明(无实体数据data返回)
     */
    public static ResponseModel error(){
        HttpStatus httpStatus = HttpStatus.FAIL;
        return new ResponseModel(httpStatus.getCode(),httpStatus.getMessage());
    }
    
    /*
     * 返回调用失败，需要自己设置失败信息说明(无实体数据data返回)
     */
    public static ResponseModel error(String resultMessage){
        HttpStatus httpStatus = HttpStatus.FAIL;
        return new ResponseModel(httpStatus.getCode(),resultMessage);
    }
    
    /*
     * 返回调用失败，需要自己设置状态码和信息说明(无实体数据data返回)
     */
    public static ResponseModel error(Integer resultCode, String resultMessage){
        return new ResponseModel(resultCode,resultMessage);
    }
    
    /*
     * 默认返回调用成功，不需要自己设置状态码和信息说明(有实体数据data返回)
     */
    public static ResponseDataModel success(Object object){
        HttpStatus httpStatus = HttpStatus.SUCCESS;
        return new ResponseDataModel(httpStatus.getCode(),httpStatus.getMessage(), object);
    }
    
    /*
     * 返回调用成功，需要自己设置成功信息说明(有实体数据data返回)
     */
    public static ResponseDataModel success(String resultMessage, Object object){
        HttpStatus httpStatus = HttpStatus.SUCCESS;
        return new ResponseDataModel(httpStatus.getCode(),resultMessage,object);
    }
    
    /*
     * 返回调用成功，需要自己设置状态码和成功信息说明(有实体数据data返回)
     */
    public static ResponseDataModel success(Integer resultCode, String resultMessage, Object object){
        return new ResponseDataModel(resultCode,resultMessage, object);
    }
}
