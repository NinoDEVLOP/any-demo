package com.fun.learn.modules.login.controller;

import com.fun.learn.base.RtnInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author littlePart
 * @version 1.0
 * @date 2020/03/2020/3/8 16:13
 */
@Slf4j
@Controller
public class TestController {

    @RequestMapping("/checkStatus")
    @ResponseBody
    public RtnInfo checkStatus() {
        return RtnInfo.SUCCESS_RES;
    }


}
