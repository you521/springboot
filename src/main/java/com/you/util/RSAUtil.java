package com.you.util;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
    * @ClassName: RSAUtil  
    * @Description: RSA加解密工具类
    *               RSA加密是一种非对称加密,可以在不直接传递密钥的情况下,完成解密;
    *               它是由一对密钥来进行加解密的过程,分别称为公钥和私钥
    *               总结：公钥加密、私钥解密、私钥签名、公钥验签
    *               在实际应用中,要根据情况使用,也可以同时使用加密和签名;
    *               比如A和B都有一套自己的公钥和私钥,当A要给B发送消息时,先用B的公钥对消息加密,再对加密的消息使用A的私钥加签名,达到既不泄露也不被篡改,更能保证消息的安全性
    * @author you  
    * @date 2020年3月5日  
    *
 */

@Component
public class RSAUtil
{
    
    private static final Logger logger = LoggerFactory.getLogger(RSAUtil.class);
                    
    public static final String KEY_ALGORITHM = "RSA";
    
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
 
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;
    
    /**
     * 
        * @Title: getKeyPair  
        * @Description: 获取秘钥对 
        * @param @return
        * @param @throws Exception    参数  
        * @return KeyPair   密钥对  
        * @throws
     */
    public KeyPair getKeyPair() throws Exception {
        KeyPair keyPair = null;
        try
        {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            // 设置秘钥长度
            generator.initialize(1024);
            keyPair= generator.generateKeyPair();
        } catch (Exception e)
        {
            logger.error("获取秘钥对失败：{}",e);
        }
        return keyPair;
    }
    
    /**
     * 
        * @Title: getPublicKeyStr  
        * @Description: 获得公钥字符串  
        * @param @param keyPair
        * @param @return
        * @param @throws Exception    参数  
        * @return String    返回类型  
        * @throws
     */
    public String getPublicKeyStr(KeyPair keyPair) throws Exception {
        // 获得keyPair中的公钥对象 转为key对象
        Key key = (Key) keyPair.getPublic();
        // 字节编码返回字符串
        return new String(Base64.encodeBase64(key.getEncoded()));
    }
 
    /**
     * 获取公钥
     * 
     * @param publicKey 公钥字符串
     * @return
     */
    public PublicKey getPublicKey(String publicKeyStr) throws Exception {
        // 字符串解码转成字节byte
        byte[] decodedKey = Base64.decodeBase64(publicKeyStr.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generatePublic(keySpec);
    }
 
    /**
     * 
        * @Title: getPrivateKeyStr  
        * @Description: 获得私钥字符串 
        * @param @param keyPair
        * @param @return
        * @param @throws Exception    参数  
        * @return String    返回类型  
        * @throws
     */
    public String getPrivateKeyStr(KeyPair keyPair) throws Exception {
        // 获得keyPair中的私钥对象 转为key对象
        Key key = (Key) keyPair.getPrivate();
        // 字节编码返回字符串
        return new String(Base64.encodeBase64(key.getEncoded()));
    }
    
    /**
     * 验签
     * 
     * @param srcData 原始字符串
     * @param publicKey 公钥
     * @param sign 签名
     * @return 是否验签通过
     */
    public boolean verify(String srcData, PublicKey publicKey, String sign) throws Exception {
        // 公钥转字节
        byte[] keyBytes = publicKey.getEncoded();
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey key = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(key);
        signature.update(srcData.getBytes());
        // 客户端签名字符串转成字节编码,然后判断两个签名是否一样
        return signature.verify(Base64.decodeBase64(sign.getBytes()));
    }

    /**
     * 获取私钥
     * 
     * @param privateKey 私钥字符串
     * @return
     */
    public PrivateKey getPrivateKey(String privateKeyStr) throws Exception {
        // 字符串解码转成字节byte
        byte[] decodedKey = Base64.decodeBase64(privateKeyStr.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generatePrivate(keySpec);
    }
 
    /**
     * 签名
     * 
     * @param data 待签名数据
     * @param privateKey 私钥
     * @return 签名
     */
    public String sign(String data, PrivateKey privateKey) throws Exception {
        // 私钥转字节 
        byte[] keyBytes = privateKey.getEncoded();
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey key = keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(key);
        signature.update(data.getBytes());
        // 签名字节编码转成字符串
        return new String(Base64.encodeBase64(signature.sign()));
    }

    /**
     * RSA加密
     * 
     * @param data 待加密数据
     * @param publicKey 公钥
     * @return
     */
    public String encrypt(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.getBytes().length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data.getBytes(), offset, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data.getBytes(), offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        // 获取加密内容使用base64进行编码,并以UTF-8为标准转化成字符串
        // 加密后的字符串
        return new String(Base64.encodeBase64String(encryptedData));
    }
 
    /**
     * RSA解密
     * 
     * @param data 待解密数据
     * @param privateKey 私钥
     * @return
     */
    public static String decrypt(String data, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] dataBytes = Base64.decodeBase64(data);
        int inputLen = dataBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(dataBytes, offset, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataBytes, offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        // 解密后的内容 
        return new String(decryptedData, "UTF-8");
    }

    public static void main(String[] args) {
        RSAUtil rsaUtil = new RSAUtil();
        try {
            // 生成密钥对
            KeyPair keyPair = rsaUtil.getKeyPair();
            String privateKey = rsaUtil.getPrivateKeyStr(keyPair);
            String publicKey = rsaUtil.getPublicKeyStr(keyPair);
            System.out.println("私钥字符串:" + privateKey);
            System.out.println("公钥字符串:" + publicKey);
            // RSA加密
            String data = "待加密的文字内容是19941212";
            String encryptData = rsaUtil.encrypt(data, rsaUtil.getPublicKey(publicKey));
            System.out.println("加密后内容:" + encryptData);
            // RSA解密
            String decryptData = decrypt(encryptData, rsaUtil.getPrivateKey(privateKey));
            System.out.println("解密后内容:" + decryptData);
            
            // RSA签名
            String sign = rsaUtil.sign(data, rsaUtil.getPrivateKey(privateKey));
            // RSA验签
            boolean result = rsaUtil.verify(data, rsaUtil.getPublicKey(publicKey), sign);
            System.out.print("验签结果:" + result);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("加解密异常");
        }
    }
}
