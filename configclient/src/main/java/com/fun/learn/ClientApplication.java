package com.fun.learn;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author LittlePart
 * @version 1.0
 * @date 2020/03/2020/3/12 16:36
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@Controller
@RefreshScope
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Value("${say:123}")
    public String say;

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return say;
    }

}
