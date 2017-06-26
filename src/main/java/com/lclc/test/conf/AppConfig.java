package com.lclc.test.conf;

import org.springframework.core.env.PropertyResolver;

/**
 * 应用配置服务.
 * 
 */
public interface AppConfig extends PropertyResolver {

    String MODE_DEV = "dev";

    String MODE_PROD = "prod";

    String PREFIX = "application.";

    String getMode();

    boolean isDevMode();

    boolean isProdMode();

    String getName();

    String getVersion();

    String getDescription();

    String getHost();

    String getPort();

    String getURL();

    String getStartYear();

    void display();
}
