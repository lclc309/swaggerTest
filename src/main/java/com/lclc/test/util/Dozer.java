package com.lclc.test.util;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

/**
 * 
 * @ClassName: Dozer
 * @Description: 用于类间映射的工具
 * @author lclc
 * @date 2016年2月29日 下午10:32:27
 *
 */
public class Dozer {

    private static DozerBeanMapper mapper = new DozerBeanMapper();

    /**
     * 
     * @Title: mapper2new @Description:
     *         创建一个destinationClass类型的对象，并将源对象进行映射入新对象 @param @param source
     *         源对象 @param @param destinationClass 目标对象类型 @param @return @return
     *         T 目标对象实例 @throws
     */
    public static <T> T mapper2new(Object source, Class<T> destinationClass) {

        if (source == null) {
            return null;
        }
        return mapper.map(source, destinationClass);
    }

    /**
     * 
     * @Title: mapper2Object @Description: 将源对象映射入目标对象 @param @param source
     *         源对象 @param @param destination 目标对象 @return void 返回类型 @throws
     */
    public static void mapper2Object(Object source, Object destination) {

        mapper.map(source, destination);
    }

    /**
     * 
     * @Title: mapper2list @Description: List之间的映射 @param @param source
     *         源 @param @param destinationClass 目标类型 @param @return 设定文件 @return
     *         List<T> 返回类型 @throws
     */
    public static <T> List<T> mapper2list(List<? extends Object> source, final Class<T> destinationClass) {

        if (Tools.isEmpty(source)) {
            return new ArrayList<T>(0);
        } else {
            return Lists.transform(source, new Function<Object, T>() {

                @Override
                public T apply(Object input) {

                    return mapper.map(input, destinationClass);
                }
            });
        }
    }
}
