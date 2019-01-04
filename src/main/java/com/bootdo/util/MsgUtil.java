package com.bootdo.util;

import com.bootdo.BootdoApplication;
import org.springframework.stereotype.Component;

@Component
public class MsgUtil {

    public static String getMsg(String code){
        return BootdoApplication.ac.getBean(MessagesConfig.class).getMap().get(code);
    }
}
