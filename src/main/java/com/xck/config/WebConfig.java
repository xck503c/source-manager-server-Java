package com.xck.config;

import com.xck.model.intercept.SessionIntercept;
import com.xck.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web config
 *
 * @author xuchengkun
 * @date 2022/01/06 22:33
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginService loginService;

    @Bean
    public SessionIntercept loginIntercept(){
        return new SessionIntercept(loginService);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginIntercept()).addPathPatterns("/**").excludePathPatterns("/login");
    }
}
