package com.fun.learn.modules.login.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author littlePart
 * @version 1.0
 * @date 2020/03/2020/3/1 11:23
 */
@Data
@NoArgsConstructor
public class SignInDto {

    private String username;

    private String password;

    //也可以命名为appId,标识调用系统区分时中台调用还是后台调用，非必须，如过不带视为中台调用
    private String source;

}
