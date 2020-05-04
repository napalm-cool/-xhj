package com.mp.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author ycn
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.mp.demo.dao"}) //扫描DAO配置文件中指定扫描包时，配置路径有问题。例如：spring配置文件中”basePackage” 属性包名的指定一定要具体到接口所在包，而不要写父级甚至更高级别的包 ，否则可能出现问题；cn.dao 与cn.*也可能导致错误；注解扫描时，可能没有扫描到包等。
public class DemoApplication extends SpringBootServletInitializer{

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // war启动类，注意这里要指向原先用main方法执行的Application启动类
        return builder.sources(DemoApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
