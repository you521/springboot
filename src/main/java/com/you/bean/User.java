package com.you.bean;

import java.io.Serializable;

import lombok.Data;

/**
 * 
    * @ClassName: User  
    * @Description: 用户实体类  
    * @author you  
    * @date 2020年3月7日  
    *
 */
@Data
public class User implements Serializable
{  
        
    private static final long serialVersionUID = 1L;
    
    private String appId;
    
    private String password;

}
