package com.fun.learn.modules.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

/**
 * @author LittlePart
 * @version 1.0
 * @date 2020/03/2020/3/18 10:20
 */
@Controller
public class RestLoadBalanceController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/test/info")
    @ResponseBody
    public String info() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://" + "consumer" + "/test/info", String.class);
        return forEntity.getBody();
    }


}
