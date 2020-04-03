package com.fun.learn.modules.controller;

import org.springframework.core.SpringProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author LittlePart
 * @version 1.0
 * @date 2020/03/2020/3/18 10:10
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/info")
    @ResponseBody
    public String info() {
        String name = SpringProperties.getProperty("spring.application.name");
        String port = SpringProperties.getProperty("server.port");
        return name + ":" + port;
    }

}
