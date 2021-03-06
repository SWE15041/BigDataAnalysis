package com.bigdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ComponentScan({"com.bigdata"})
@EnableAutoConfiguration
@EnableWebMvc
//@EnableAsync //利用@EnableAsync注解开启异步任务支持

//@EntityScan(basePackages = {"com.bigdata.core","com.bigdata.controller"})
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


}
