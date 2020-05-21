package com.you.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * 
    * @ClassName: AESUtil  
    * @Description: AES加解密工具类  （对称加密）
    *               一方将传输的报文用私钥加密，得到密文。另一方接收到密文，用密钥解密，得到明文
    * @author you  
    * @date 2020年3月3日  
    *
 */

@Component
public class AESUtil
{
    private static Logger logger = LoggerFactory.getLogger(AESUtil.class);
    // 算法名称
    private static final String ALGORITHM = "AES";
    
    private Base64 base64 = new Base64();
    
    /**
     * 
        * @Title: encrypt  
        * @Description: AES字符串加密 
        * @param @param content
        * @param @param password
        * @param @return
        * @param @throws Exception    参数  
        * @return String    返回类型  
        * @throws
     */

    public String encrypt(String content, String password) throws Exception {
        String str = null;
        try
        {
            // 创建算法是AES的密码器
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // 生成加密秘钥
            SecretKey secretKey = this.geneKey(password);
            // 初始化为加密模式的密码器
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            // 获取字符串 字 节 数 组
            byte[] byteContent = content.getBytes("utf-8");
            // 加 密 后 的 字 节 数 组
            byte[] result = cipher.doFinal(byteContent);
            // 将二进制字节数转成16进制数
            str = this.parseByte2HexStr(result);
        } catch (Exception e)
        {
           logger.error("字符串加密失败："+e);
           throw e;
        }
        return str;
    }
    
    /**
     * 
        * @Title: encryptToBase64  
        * @Description: AES字符串加密为Base64编码
        * @param @param content
        * @param @param password
        * @param @return
        * @param @throws Exception    参数  
        * @return String    返回类型  
        * @throws
     */
    public String encryptToBase64(String content, String password) throws Exception {
        String str = null;
        try
        {
            // 创建算法是AES的密码器
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // 生成加密秘钥
            SecretKey secretKey = this.geneKey(password);
            // 初始化为加密模式的密码器
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            // 获取字符串 字 节 数 组
            byte[] byteContent = content.getBytes("utf-8");
            // 加 密 后 的 字 节 数 组
            byte[] result = cipher.doFinal(byteContent);
            // 对 加 密 后 的 字 节 数 组 进 行 Base64 编 码
            str = base64.encodeToString(result);
        } catch (Exception e)
        {
           logger.error("字符串加密失败："+e);
           throw e;
        }
        return str;
    }
    
    /**
     * 
        * @Title: decrypt  
        * @Description: AES解密  
        * @param @param content
        * @param @param password
        * @param @return
        * @param @throws Exception    参数  
        * @return String    返回类型  
        * @throws
     */
    
    public String decrypt(String content, String password) throws Exception {
        String str = null;
        try
        {
            // 将16进制数转成二进制
            byte[] result = this.parseHexStr2Byte(content);
            // 创建算法是AES的密码器
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // 生成加密秘钥
            SecretKey secretKey = this.geneKey(password);
            // 初始化为加密模式的密码器
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            // 将二进制数转成明文
            str = new String(cipher.doFinal(result),"utf-8");
        } catch (Exception e)
        {
           logger.error("字符串解密失败："+e);
           throw e;
        }
        return str.toString();
    }
    
    /**
     * 
        * @Title: decryptToBase64  
        * @Description: Base64编码密文AES解密
        * @param @param content
        * @param @param password
        * @param @return
        * @param @throws Exception    参数  
        * @return String    返回类型  
        * @throws
     */
    public String decryptToBase64(String content, String password) throws Exception {
        String str = null;
        try
        {
            // 创建算法是AES的密码器
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // 生成加密秘钥
            SecretKey secretKey = this.geneKey(password);
            // 初始化为加密模式的密码器
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            // 将Base64解码
            byte[] base64Str = base64.decode(content.getBytes());
            // 将二进制数转成明文
            str =  new String(cipher.doFinal(base64Str),"utf-8");
        } catch (Exception e)
        {
           logger.error("字符串解密失败："+e);
           throw e;
        }
        return str.toString();
    }
    
    /**
     * 根据来源系统获取加解密密钥
     *
     * @param seed
     * @return
     * @throws Exception
     */
    private SecretKey geneKey(String seed) throws Exception {
        KeyGenerator keyGenerator = null;
        SecureRandom random = null;
        SecretKeySpec key = null;
        try {
            // 创建AES的Key生产者
            keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(seed.getBytes());
            keyGenerator.init(128, random);
            // 根据用户密码，生成一个密钥
            SecretKey secretKey = keyGenerator.generateKey();
            // 返回基本编码格式的密钥，如果此密钥不支持编码，则返回
            byte[] encodeFormat = secretKey.getEncoded();
            // 转换为AES专用密钥
            key = new SecretKeySpec(encodeFormat, ALGORITHM);
        } catch (Exception e) {
            logger.error("生成密钥失败:"+e);
            throw e;
        }
        return key;
    }
    
    /**
     * 将二进制转换成16进制 
     * @param buf 
     * @return string
     */  
    private String parseByte2HexStr(byte buf[]) {  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < buf.length; i++) {  
            String hex = Integer.toHexString(buf[i] & 0xFF);  
            if (hex.length() == 1) {  
               hex = '0' + hex;  
            }  
            sb.append(hex.toUpperCase());  
        }  
        return sb.toString();  
    } 
    
    /**
     * 将16进制转换为二进制 
     * @param hexStr 
     * @return 
     */  
    private byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }  
       return result;
    }
}
