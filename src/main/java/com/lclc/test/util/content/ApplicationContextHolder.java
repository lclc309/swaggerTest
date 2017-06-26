package com.lclc.test.util.content;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * Spring 上下文获取器
 * 
 * @author crane
 *
 */
@Service
public class ApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @SuppressWarnings("all")
    public void setApplicationContext(ApplicationContext context) {

        if (this.applicationContext != null) {
            throw new IllegalStateException("ApplicationContextHolder already holded 'applicationContext'.");
        }
        this.applicationContext = context;
    }

    public static ApplicationContext getApplicationContext() {

        if (applicationContext == null)
            throw new IllegalStateException(
                    "'applicationContext' property is null,ApplicationContextHolder not yet init.");
        return applicationContext;
    }

    public static Object getBean(String beanName) {

        return getApplicationContext().getBean(beanName);
    }

    public static <T> T getBean(Class<T> clazz) {

        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {

        return getApplicationContext().getBean(beanName, clazz);
    }

    public static void cleanHolder() {

        applicationContext = null;
    }
}