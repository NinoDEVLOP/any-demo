package com.fun.learn.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author LittlePart
 * @version 1.0
 * @date 2020/03/2020/3/12 14:05
 */
@EnableEurekaClient
@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
@RefreshScope
public class ConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
