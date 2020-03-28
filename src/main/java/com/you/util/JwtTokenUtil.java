package com.you.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.you.bean.User;
import com.you.bean.configbean.JwtConfigBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 
    * @ClassName: JwtUtil  
    * @Description: jwt生成token工具类 
    * @author you  
    * @date 2020年3月7日  
    *
 */

@Component
public class JwtTokenUtil
{
    private final static Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);
    
    // java8中的base64编码
    private final Base64.Encoder encoder = Base64.getEncoder();
    // java8中的base64解码
    private final Base64.Decoder decoder = Base64.getDecoder();
    
    @Autowired
    private JwtConfigBean jwtConfigBean;
   
    /**
        * @Title: generateToken  
        * @Description: 初始化生成token的参数claims,可以将基本不重要的对象信息放到claims
        * @param @param user
        * @param @return    参数  
        * @return String    返回类型  
        * @throws
     */
    public String generateToken(User user) {
        String token = null;
        try
        {
            Map<String, Object> claims = new HashMap<>();
            // 将用户Id进行base64编码
            claims.put("userId", encoder.encodeToString(user.getAppId().getBytes("UTF-8")));
            token = this.createJWT(claims);
        } catch (UnsupportedEncodingException e)
        {
            logger.error("初始化生成token的参数claims失败：{}"+e);
        }
        return token;
    }
    
    /**
     * 创建JWT
     */
    public String createJWT(Map<String, Object> claims) {
        String token = null;
        try
        {
            // 设置签名的时候使用的签名算法,也就是header那部分,jjwt已经将这部分内容封装好了。
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; 
            // 记录生成JWT的时间
            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);
            System.out.println("签发时间------------》"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now));
            // 下面就是在为payload添加各种标准声明和私有声明了
            JwtBuilder builder = Jwts.builder() // 这里其实就是new一个JwtBuilder，设置jwt的body
                     // 设置自己的私有声明
                    .setClaims(claims)
                     // jti(JWT ID)：设置tocken的Id，用于防止tocken重复
                    .setId(UUID.randomUUID().toString())
                     // iss：设置token签发者
                    .setIssuer(jwtConfigBean.getIssuer())
                     // iat: 设置token的签发时间
                    .setIssuedAt(now)
                     // sub：设置tocken的签发对象
                    .setSubject("system")
                     // aud：设置token的接收对象
                    .setAudience((String) claims.get("userId"))
                     // 设置签名使用的签名算法和签名使用的秘钥
                    .signWith(signatureAlgorithm, jwtConfigBean.getSecret());
            //添加Token过期时间
            int TTLMillis = jwtConfigBean.getExpire();
            if (TTLMillis >= 0) {
                // 设置过期时间
                long expMillis = nowMillis + TTLMillis * 1000;
                Date exp = new Date(expMillis);
                System.out.println("打印过期时间------------》"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(exp));
                // 设置这个JWT的过期时间；
                builder.setExpiration(exp)
                        // 设置这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的
                        .setNotBefore(now); 
            }
            token = jwtConfigBean.getTokenPrefix() + " " + builder.compact();
        } catch (Exception e)
        {
           logger.error("生成token令牌失败：{}"+e);
        }
        return token;
    }
    
    /**
     * 解析token,获取信息
     */
    public Claims parseTocken(String token) {
        Claims claims = null;
        try {
            // 获取tocken中的声明部分
            claims = Jwts.parser()
                    // 设置签名的秘钥
                    .setSigningKey(jwtConfigBean.getSecret())
                    .parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
           logger.error("token解析失败：{}"+e);
           claims = e.getClaims();
        }
        // 设置需要解析的jwt
        return claims;
    }
    
    /**
     * 从token中获取用户ID
     * @param token
     * @return
     */
    public String getUserIdByToken(String token) {
        String userId = null;
        try
        {
            Claims claims = this.parseTocken(token);
            String str = claims.get("userId", String.class);
            userId = new String(decoder.decode(str),"UTF-8");
        } catch (UnsupportedEncodingException e)
        {
           logger.error("获取用户Id失败：{}"+e);
        }
        return userId;
    }
    
    /**
     * 判断Token是否过期
     * @param token
     * @return true 过期
     */
    public boolean isTokenExpired(String token) {
        return this.parseTocken(token).getExpiration().before(new Date());
    }

    /**
        * @Title: refreshToken  
        * @Description: 刷新token
        * @param @param token
        * @return String    返回类型  
        * @throws
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = this.parseTocken(token);
            refreshedToken = this.createJWT(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }
    
    public static void main(String[] args) {
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzeXN0ZW0iLCJhdWQiOiJXbWhwUzJVPSIsIm5iZiI6MTU4NDM3MjU5MywiaXNzIjoic2xlYiIsImV4cCI6MTU4NDM3NjE5MywidXNlcklkIjoiV21ocFMyVT0iLCJpYXQiOjE1ODQzNzI1OTMsImp0aSI6ImZhN2ZlN2JiLTI4ZTUtNGZmZS1iYmIyLTA1YzA3MzRiZmI1NCJ9.eGxPEhwx7KMWE5J7SIazfmnKwDuEm3e09ttIuwN_BDk";
        // 截取token
        token = token.substring(7);
        System.out.println(token);
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        Claims claims = jwtTokenUtil.parseTocken(token);
        System.out.println("claims------->"+claims);
        System.out.println("--------------签发日期-------------"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getIssuedAt()));
        System.out.println("-----------token接受对象----------"+claims.getAudience());
        System.out.println("-------------token的Id---------------"+claims.getId());
        System.out.println("------------token的签发者--------------"+claims.getIssuer());
        System.out.println("----------用户id---------"+jwtTokenUtil.getUserIdByToken(token));
        System.out.println("-------------是否过期-------------"+jwtTokenUtil.isTokenExpired(token));
        System.out.println("--------刷新token-------"+jwtTokenUtil.refreshToken(token));
    }
}
