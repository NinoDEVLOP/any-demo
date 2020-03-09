package com.fun.learn.config.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author littlePart
 * @version 1.0
 * @date 2020/03/2020/3/7 22:40
 */
@ConfigurationProperties(prefix = "service.datasource")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDataSourceProperties {
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private int initialSize;
    private int maxActive;
    private long maxWait;
    private int minIdle;
    private long timeBetweenEvictionRunsMillis;
    private long minEvictableIdleTimeMillis;
    private String validationQuery;
    private boolean testWhileIdle;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private String filters;


}
