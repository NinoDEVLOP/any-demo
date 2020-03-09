package com.fun.learn.modules.login.model.entry;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author littlePart
 * @version 1.0
 * @date 2020/03/2020/3/1 16:35
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonFormat
public class CustomerInfo {

    private Long id;

    private String username;

    @JsonIgnore
    @Expose(serialize = false)
    private String password;

    private int isDelete;

    public static void main(String[] args) throws JsonProcessingException {
        CustomerInfo test = CustomerInfo.builder()
                .id(1L)
                .username("test")
                .password("123")
                .isDelete(0).build();
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(test));
    }

}
