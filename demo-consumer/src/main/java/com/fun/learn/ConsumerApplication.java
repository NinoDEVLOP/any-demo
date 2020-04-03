package com.fun.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author LittlePart
 * @version 1.0
 * @date 2020/03/2020/3/18 10:06
 */
//@EnableDiscoveryClient
@SpringBootApplication
//@EnableEurekaClient
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

}
