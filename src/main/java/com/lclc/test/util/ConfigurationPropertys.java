package com.lclc.test.util;

/**
 * 全局属性
 * 
 * @name ConfigurationPropertys
 * @discription
 * @author lichao
 * @date 2015年9月16日
 */
public interface ConfigurationPropertys {

    public static String MD5PRIFIX = "morequ";

    public static String AESKEY = "automorequ";

    public static int ENABLE = 0;

    public static int NOTENABLE = 1;

    // 公用分页条数
    public static int commonPageSize = 20;

    public static final int RECOMMENDIMAGESIZE = 3;

    // 社区活动相关的KEY
    // 活动最新队列KEY的前缀
    public static final String newPicKey = "HD_NEW_PIC_";

    // 活动排名队列KEY的前缀
    public static final String indexSet = "HD_INDEX_";

    // 活动去重后的排名用户KEY的前缀
    public static final String distinctSet = "HD_DISTINCT_";

    // 排名更新时间前缀
    public static final String updateDate = "HD_UPDATETIME_";

    // 活动是否有人参加标记缓存key的前缀
    public static final String isJoin = "HD_ISJOIN_";
}
