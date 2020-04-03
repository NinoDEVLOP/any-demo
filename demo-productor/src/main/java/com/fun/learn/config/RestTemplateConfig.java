package com.fun.learn.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author LittlePart
 * @version 1.0
 * @date 2020/03/2020/3/18 10:34
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public IRule ribbonRule() {
        return new RandomRule();
    }

    //Ribbon配置有三个步骤式：
    //1. 在配置文件中配置相关属性
    //    {serviceName}.ribbon.NFLoadBalancerRuleClassName={LoadBalancerRule的实现类类名(包含完整路径)}
    //2. 在Configuration中配置相关bean
    //    @Bean
    //    public IRule ribbonRule(){
    //        return new RandomRule();
    //    }
    //3. 在发起请求时，设置Rule
    //
}
