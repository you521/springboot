package com.you.common.model;

import lombok.Data;

/**
 * 
    * @ClassName: ResponseDataModel  
    * @Description: 定义响应实体类（含返回实体类数据）  
    * @author you  
    * @date 2020年3月12日  
    *
 */

@Data
public class ResponseDataModel extends ResponseModel {
    
    /*
     * 无参构造函数（显式）
     */
    public ResponseDataModel() {
        
    }
    
    /*
     * 有参构造函数
     */
    public ResponseDataModel(Integer resultCode, String resultMessage, Object object) {
        // 调用父类的有参构造函数
        super(resultCode,resultMessage);        
        this.data = object;
    }
    
    /**
     * 返回结果
     */
    private Object data;
}
