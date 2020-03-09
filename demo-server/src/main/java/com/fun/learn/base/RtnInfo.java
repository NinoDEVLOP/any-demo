package com.fun.learn.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fun.learn.base.exception.BaseException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author littlePart
 * @version 1.0
 * @date 2020/02/2020/2/29 16:57
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RtnInfo {

    public static final RtnInfo SUCCESS_RES = RtnInfo.builder().code("0").msg("success").build();

    public static final RtnInfo FAILED_RES = RtnInfo.builder().code("1").msg("failed").build();

    @Getter
    private String code;

    @Getter
    private String msg;

    @Getter
    private Object data;

    @Getter
    private Long count;

    public static RtnInfo error(BaseException se) {
        return RtnInfo.builder()
                .code(se.getCode())
                .msg(se.getMsg()).build();
    }

    public static RtnInfo error(GlobalErrorEnum se) {
        return RtnInfo.builder()
                .code(se.getCode())
                .msg(se.getMsg()).build();
    }

    public static RtnInfo success(Object data) {
        return RtnInfo.builder()
                .code("0")
                .msg("成功")
                .data(data).build();
    }

    public static RtnInfo success() {
        return success(null);
    }

}
