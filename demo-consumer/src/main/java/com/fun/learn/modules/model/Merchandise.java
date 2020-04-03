package com.fun.learn.modules.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author LittlePart
 * @version 1.0
 * @date 2020/04/2020/4/2 19:36
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Merchandise {
    private String code;

    private String name;

    private Long price;

    private String shopName;

    private String shopCode;

    private List<String> features;

    private String merchandiseType;

    private String supplyAddress;

    private Long pv;

    private Long uv;

    private Long orderHeat;

}
