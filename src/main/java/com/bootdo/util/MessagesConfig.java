package com.bootdo.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource( name="messages.properties",value= {"classpath:message/messages.properties"},ignoreResourceNotFound=false,encoding="UTF-8")
@ConfigurationProperties(prefix = "msg")
public class MessagesConfig {
    public Map<String, String> map = new HashMap<String, String>();

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
