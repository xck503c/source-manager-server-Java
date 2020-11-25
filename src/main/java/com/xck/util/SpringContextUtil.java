package com.xck.util;

import org.springframework.context.ApplicationContext;

public class SpringContextUtil {

    private static ApplicationContext apx;

    public static void setApx(ApplicationContext apx) {
        SpringContextUtil.apx = apx;
    }
}
