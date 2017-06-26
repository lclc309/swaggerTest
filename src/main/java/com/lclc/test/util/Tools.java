package com.lclc.test.util;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @name Tools
 * @discription 常用工具类
 * @author lichao
 * @date 2015年9月6日
 */
public class Tools {

    /**
     * 判断ajax请求
     * 
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request) {

        return (request.getHeader("X-Requested-With") != null
                && "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString()));
    }

    /**
     * 判断集合是否为空
     * 
     * @param collection
     * @return
     * @date 2015年9月16日
     */
    public static <T> boolean isEmpty(Collection<T> collection) {

        return collection == null || collection.size() <= 0;
    }

    public static String makeMD5Key(String md5prifix, String password, String affter) {

        return md5prifix + password + affter;
    }

    public static boolean isNullOrEmpty(String... strs) {

        if (strs != null) {
            for (int i = 0; i < strs.length; i++) {
                if (strs[i] == null || strs[i].trim().length() <= 0) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    // 获取查询的昵称的方法
    public static String getSearchName(String name) {

        name = name.replaceAll("\\s", "").toLowerCase();

        return name;
    }

    // 获取32位随机字符
    public static String getRandom() {

        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }

    // 设置默认值
    public static <T> T getDefault(T obj, T defaultVal) {

        return obj != null ? obj : defaultVal;
    }

    // 判断空
    public static Boolean isNull(Object... obj) {

        if (obj != null) {
            for (Object object : obj) {
                if (object == null) {
                    return true;
                }
            }
        } else {
            return true;
        }
        return false;
    }

    public static Boolean isNullOrZero(Number... number) {

        if (number != null) {
            for (Number num : number) {
                if (num == null || num.doubleValue() == 0) {
                    return true;
                }
            }
        } else {
            return true;
        }
        return false;
    }

    public static String makePrize(Double prizeMarketLow, Double prizeMarketGreat) {

        if (prizeMarketGreat == null || Objects.equals(prizeMarketLow, prizeMarketGreat)) {
            return prizeMarketLow + "";
        } else {
            return prizeMarketLow + "~" + prizeMarketGreat;
        }
    }

    // 随机数对象
    private final static Random rand = new Random();

    // 获取有最大值的随机整数
    public static Integer getRandom(Integer maxPrice) {

        // 从1到最大值
        int randNum = rand.nextInt(maxPrice) + 1;
        return randNum;
    }

    // 元转分
    public static Integer yuanConvertfen(Double yuan) {

        if (yuan != null) {
            return (int) (yuan * 100);
        }
        return null;
    }

    // 分转元
    public static Double fenConvertyuan(Integer fen) {

        if (fen != null) {
            BigDecimal bg = new BigDecimal((double) (fen * 0.01));
            double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            return f1;
        }
        return null;
    }

    // 获取当前整数的负数
    public static Integer unAbs(Integer price) {

        if (price != null) {
            return (price > 0) ? -price : price;
        }
        return null;
    }

    // 保留两位小数
    public static Double doubleRound(Double pay2user) {

        BigDecimal bg = new BigDecimal(pay2user);
        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }
}
