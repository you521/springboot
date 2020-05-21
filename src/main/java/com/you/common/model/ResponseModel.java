package com.you.common.model;


import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 
    * @ClassName: ResponseModel  
    * @Description: 定义响应实体类（不含返回实体类数据）
    * @author you  
    * @date 2020年3月12日  
    *
 */

@Data
public class ResponseModel {
    
    private static final String timeStamp;
    
    // 静态代码块初始化响应时间
    static {
        // 获取当前时间
        LocalDateTime localDateTime = LocalDateTime.now();
        // 设置要转换成的时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 获取当前时间字符串
        timeStamp = formatter.format(localDateTime);
    }
    
    /*
     * 无参构造函数（显式）
     */
    
    public ResponseModel() {
       
    }
    
    /*
     * 有参构造函数
     */
    public ResponseModel(Integer resultCode) {
        this.resultCode = resultCode;
        this.resultTime = timeStamp;
    }
    
    /*
     * 有参构造函数
     */
    public ResponseModel(String resultMessage) {
        this.resultMessage = resultMessage;
        this.resultTime = timeStamp;
    }
    
    /*
     * 有参构造函数
     */
    public ResponseModel(Integer resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.resultTime = timeStamp;
    }
    
    /*
     * 处理结果返回码
     */
    private Integer  resultCode;
    
    /*
     * 处理结果返回说明
     */
    private String resultMessage;

    /*
     * 时间戳
     */
    private String resultTime;
}
