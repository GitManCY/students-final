package com.cy.config;


import com.cy.compoment.LoginHandlerInterceptor;
import com.cy.compoment.MyLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author a123
 * @title: MvcConfig
 * @projectName springboot-restful-crud
 * @description: 扩展SpringMVC的功能
 * @date 2019/4/244:56 PM
 */


//springboot2.0过后WebMvcConfigurerAdapter被废除 继承WebMvcConfigurationSupport 或者 实现WebMvcConfigurer来代替
//在WebMvcConfigurer接口中使用default声明方法后 可以不用实现接口方法

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {


        WebMvcConfigurer configurer = new WebMvcConfigurer() {
            //添加视图解析器
            public void addViewControllers(ViewControllerRegistry registry) {
                //给后面request.getDispatcher返回路径设置试图
                registry.addViewController("/login.html").setViewName("login");
                registry.addViewController("/student.html").setViewName("students");
            }
            //拦截器
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new LoginHandlerInterceptor())
                        .excludePathPatterns(
                                "/asserts/**","/webjars/**","/excel/*",
                               "/login.html","/tologin", "/login","/visitor","/visitorquery", "/downloadExcel"
                        );
            }
        };
        return configurer;
    }


    //添加区域 国际化
    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }

}
