package com.lclc.test.conf.jackson;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

/**
 * beanSerializer的修改者，当通过BeanSerializerFactory进行constructBeanSerializer时，
 * 将通过modifyer进行修改整个bean的List<BeanPropertyWriter>，<br/>
 * 此list则包装了此bean下的所有的字段的序列化的方法，<br/>
 * 进行序列化时,将循环此list进行输出<br/>
 * 可以给beanPropertyWriter注册nullSerializer，当为Empty时使用。
 * 
 * @name MyBeanSerializerModifier
 * @discription
 * @author lichao
 * @date 2015年12月15日
 */
public class MyBeanSerializerModifier extends BeanSerializerModifier {

    private JsonSerializer<Object> _nullArrayJsonSerializer = new MyNullArrayJsonSerializer();

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
            List<BeanPropertyWriter> beanProperties) {

        // 循环所有的beanPropertyWriter
        for (int i = 0; i < beanProperties.size(); i++) {
            BeanPropertyWriter writer = beanProperties.get(i);
            // 判断字段的类型，如果是array，list，set则注册nullSerializer
            if (isArrayType(writer)) {
                writer.assignNullSerializer(this.defaultNullArrayJsonSerializer());
            }
        }
        return beanProperties;
    }

    // 判断是什么类型
    protected boolean isArrayType(BeanPropertyWriter writer) {

        Class<?> clazz = writer.getPropertyType();
        return clazz.isArray() || clazz.equals(List.class) || clazz.equals(Set.class);

    }

    protected JsonSerializer<Object> defaultNullArrayJsonSerializer() {

        return _nullArrayJsonSerializer;
    }
}
