package com.example.springbootpetstore.config;

import com.example.springbootpetstore.interceptors.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 皮皮皮
 * @date 2023/4/18 13:38
 */
@Configuration
public class MyConfig implements WebMvcConfigurer {
    /*
     *addResourceHandler:访问映射路径
     *addResourceLocations:资源绝对路径
     * addResourceHandler("/pet/**")：映射的路径。
       addResourceLocations(“file:C:/Users/pi/Pictures/temp/”)：本地磁盘的路径。
     */
    //映射器
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/pet/**").addResourceLocations("file:C:/Users/pi/Pictures/temp/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/product/**","/transaction/**","/user/**","/main/**").excludePathPatterns();
    }
}

