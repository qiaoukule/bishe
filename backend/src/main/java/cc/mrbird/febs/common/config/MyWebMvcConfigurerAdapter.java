package cc.mrbird.febs.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfigurerAdapter implements WebMvcConfigurer {

    //自定义静态资源映射目
    // addResourceHandler：指的是对外暴露的访问路径
    //addResourceLocations：指的是内部文件放置的目录
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/imagesWeb/**").addResourceLocations("file:E:/Project/小区物业管理系统/db/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
