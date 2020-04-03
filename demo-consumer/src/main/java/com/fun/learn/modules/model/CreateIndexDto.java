package com.fun.learn.modules.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LittlePart
 * @version 1.0
 * @date 2020/04/2020/4/1 10:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateIndexDto {

    private String indexName;


}
