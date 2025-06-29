package com.example.unity_backend.Interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthInterceptorConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    @Autowired
    public AuthInterceptorConfig(AuthInterceptor authInterceptor1){
        this.authInterceptor=authInterceptor1;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//注册拦截器
        registry.addInterceptor(authInterceptor)
                .order(2)
                //.addPathPatterns("/**")//拦截所有
                .excludePathPatterns("/login","/registered","/api/product/**","/static/**","/webjars/**","/login.html","/main.html");
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 确保静态资源目录正确映射
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");  // 映射 static 文件夹
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");  // 映射到静态资源的根目录

        // 如果你使用了 webjars，也要确保它们被正确映射
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


}
