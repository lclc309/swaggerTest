package com.lclc.test.util;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.lclc.test.util.codec.AES;

/**
 * 编码工具类 1.将byte[]转为各种进制的字符串 2.base 64 encode 3.base 64 decode 4.获取byte[]的md5值
 * 5.获取字符串md5值 6.结合base64实现md5加密 7.AES加密 8.AES加密为base 64 code 9.AES解密 10.将base
 * 64 code AES解密
 * 
 * @author uikoo9
 * @version 0.0.7.20140601
 */
public class QEncodeUtil {

    private static Logger log = LoggerFactory.getLogger(QEncodeUtil.class);

    /**
     * 将byte[]转为各种进制的字符串
     * 
     * @param bytes
     *            byte[]
     * @param radix
     *            可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
     * @return 转换后的字符串
     */
    public static String binary(byte[] bytes, int radix) {

        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
    }

    /**
     * base 64 encode
     * 
     * @param bytes
     *            待编码的byte[]
     * @return 编码后的base 64 code
     */
    public static String base64Encode(byte[] bytes) {

        return Base64.encodeBase64String(bytes);
    }

    /**
     * base 64 decode
     * 
     * @param base64Code
     *            待解码的base 64 code
     * @return 解码后的byte[]
     * @throws Exception
     */
    public static byte[] base64Decode(String base64Code) {

        return Strings.isNullOrEmpty(base64Code) ? null : Base64.decodeBase64(base64Code);
    }

    /**
     * 获取byte[]的md5值
     * 
     * 
     * @throws Exception
     */
    public static String md5(String data) {

        return DigestUtils.md5Hex(data);
    }

    /**
     * AES加密
     * 
     * @param content
     *            待加密的内容
     * @param encryptKey
     *            加密密钥
     * @return 加密后的byte[]
     * @throws Exception
     */
    public static final String VIPARA = "1234567890123456";

    public static final String bm = "utf-8";

    public static byte[] aesEncryptToBytes(String content, String encryptKey) {

        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(encryptKey.getBytes(Charset.forName("utf-8")));
            kgen.init(128, random);
            IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes(bm));

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"), zeroIv);
            return cipher.doFinal(content.getBytes("utf-8"));
        } catch (Exception e) {
            log.error("this make aes encrypt fail content:{},encryptKey:{}", content, encryptKey);
            return null;
        }

    }

    /**
     * AES加密为base 64 code
     * 
     * @param content
     *            待加密的内容
     * @param encryptKey
     *            加密密钥
     * @return 加密后的base 64 code
     * @throws Exception
     */
    public static String aesEncrypt(String content, String encryptKey) {

        // return base64Encode(aesEncryptToBytes(content, encryptKey));
        try {
            return AES.newInstance(encryptKey).encrypt(content.getBytes("utf-8"));
        } catch (Exception e) {

            log.error("this make aes encrypt fail content:{},encryptKey:{},err:{}", content, encryptKey,
                    e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * AES解密
     * 
     * @param encryptBytes
     *            待解密的byte[]
     * @param decryptKey
     *            解密密钥
     * @return 解密后的String
     * @throws Exception
     */
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) {

        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(decryptKey.getBytes(Charset.forName("utf-8")));

            kgen.init(128, random);
            IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes(bm));
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"), zeroIv);
            byte[] decryptBytes = cipher.doFinal(encryptBytes);
            return new String(decryptBytes, Charset.forName("utf-8"));
        } catch (Exception e) {
            log.error("this make aes Decrypt fail content:{},encryptKey:{},err:{}", base64Encode(encryptBytes),
                    decryptKey, e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将base 64 code AES解密
     * 
     * @param encryptStr
     *            待解密的base 64 code
     * @param decryptKey
     *            解密密钥
     * @return 解密后的string
     * @throws Exception
     */
    public static String aesDecrypt(String encryptStr, String decryptKey) {

        try {
            return Strings.isNullOrEmpty(encryptStr) ? null : AES.newInstance(decryptKey).decrypt(encryptStr);
        } catch (Exception e) {
            log.error("this make aes Decrypt fail content:{},encryptKey:{},err:{}", encryptStr, decryptKey,
                    e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
