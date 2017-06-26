package com.lclc.test.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 集合帮助类
 * 
 * @author yinjun
 * @date 2015-11-20 14:33:00
 * 
 */
public class CollectionUtil {

    /**
     * list 去重
     * 
     * @param list
     *            list集合
     */
    public static <T> List<T> removeOrder(List<T> list) {

        HashSet<T> set = new HashSet<T>();
        ArrayList<T> newList = new ArrayList<T>();
        for (T item : list) {
            if (set.add(item)) {
                newList.add(item);
            }
        }
        set.clear();
        list.clear();
        return newList;
    }
}
