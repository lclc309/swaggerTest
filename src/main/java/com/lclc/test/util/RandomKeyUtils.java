package com.lclc.test.util;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

/**
 * 
 * @name RandomKeyUtils
 * @discription 生成随机字符串
 * @author lichao
 * @date 2015年9月15日
 */
public class RandomKeyUtils {

    /**
     * 生成字符+数字混合的字符串
     * 
     * @param count
     * @return
     * @date 2015年9月15日
     */
    public static String getRandomString(int count) {

        return RandomStringUtils.randomAlphanumeric(count);
    }

    public static String getRandomString() {

        return getRandomString(6);
    }

    /**
     * 商城圈数字的字符串
     * 
     * @param count
     * @return
     * @date 2015年9月15日
     */
    public static String getRandomInt(int count) {

        return RandomStringUtils.randomNumeric(count);
    }

    public static String getRandomInt() {

        return getRandomInt(6);
    }

    private static String[] chars = new String[] { // 要使用生成URL的字符
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u",
            "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

    /**
     * 生成唯一短id的算法，6位字符+数字。此算法为微博短连接的算法。<br/>
     * ① 将长网址用md5算法生成32位签名串，分为4段,，每段8个字符；
     * 
     * ② 对这4段循环处理，取每段的8个字符, 将他看成16进制字符串与0x3fffffff(30位1)的位与操作，超过30位的忽略处理；
     * 
     * ③ 将每段得到的这30位又分成6段，每5位的数字作为字母表的索引取得特定字符，依次进行获得6位字符串；
     * 
     * ④ 这样一个md5字符串可以获得4个6位串，取里面的任意一个就可作为这个长url的短url地址。
     * 
     * @return
     * @date 2015年9月16日
     */

    public static String getShortUUID() {

        String uuid = UUID.randomUUID().toString();
        String hex = DigestUtils.md5Hex(uuid);
        // 32位 分成4段 随机抽出其中的一段做算法
        int i = RandomUtils.nextInt(0, 4);
        String outChars = "";
        int j = i + 1;
        String subHex = hex.substring(i * 8, j * 8);
        // 随机抽出一段字符串
        long idx = Long.valueOf("3FFFFFFF", 16) & Long.valueOf(subHex, 16);
        for (int k = 0; k < 6; k++) {
            // int index = (int) (Long.valueOf("0000001f", 16) & idx);
            int index = (int) (Long.valueOf("0000003D", 16) & idx);
            // 这里的"000001f"不能大于数组chars的长度，并且让其二进制格式从左到右尽可能多的为1,取值一般为35,61,63
            outChars += chars[index];
            idx = idx >> 5;
        }
        // 生成的字符串前在加4位数字
        return getRandomString(4) + outChars;
    }
}
