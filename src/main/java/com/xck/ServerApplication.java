package com.xck;

import com.xck.util.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        ApplicationContext apx = SpringApplication.run(ServerApplication.class, args);
        SpringContextUtil.setApx(apx);
    }
}
