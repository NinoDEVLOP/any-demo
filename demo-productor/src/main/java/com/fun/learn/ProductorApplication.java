package com.fun.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author LittlePart
 * @version 1.0
 * @date 2020/03/2020/3/18 10:19
 */
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication
@EnableCircuitBreaker
public class ProductorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductorApplication.class, args);
    }

}
