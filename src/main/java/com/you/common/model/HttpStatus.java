package com.you.common.model;

/**
 * 
    * @ClassName: HttpStatus  
    * @Description: 定义响应码枚举类型 
    * @author you  
    * @date 2020年3月12日  
    *
 */

public enum HttpStatus
{
    SUCCESS(1000, "成功"),
    
    FAIL(1001, "失败"),

    PARAM_ERROR(1002, "参数不合法"),
    
    TOKEN_ERROR(1003, "token异常"),
    
    SIGN_ERROR(1004, "签名异常"),

    DATABASE_ERROR(1005, "数据库异常"),
    
    TOKEN_REFRESH(1006, "token重新刷新生成"),
    
    INTERNAL_SERVER_ERROR(500, "服务器内部错误");
    
    private Integer code;
    private String message;

    HttpStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
