package com.fun.learn.base.config;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.NodeSelector;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author LittlePart
 * @version 1.0
 * @date 2020/03/2020/3/31 17:30
 */
@Configuration
public class EsConfig {

    @Value("${es.host:192.168.16.132}")
    private String host;

    @Value("${es.port:9200}")
    private int port;

    @Value("${es.timeout:10}")
    private int timeout;

    @Value("${es.connMax:16}")
    private int connMax;

    @Bean
    public RestHighLevelClient esHighLevelClient() {
        RestClientBuilder builder = RestClient.builder(
                new HttpHost(host, port, "http")
        );

//        builder
//                .setFailureListener(new RestClient.FailureListener() {
//                    private final Logger logger = LoggerFactory.getLogger(EsConfig.class);
//
//                    @Override
//                    public void onFailure(Node node) {
//                        logger.warn("搜索数据失败--{}:{}-{}\n查询参数{}", node.getName(), node.getHost(), node.getVersion(), node.getAttributes());
//                    }
//                })
//                //自定义发送消息规则
//                .setNodeSelector(NodeSelector.ANY)
//                .setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
//                    @Override
//                    public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder builder) {
//                        builder.setAuthenticationEnabled(false);
//                        builder.setConnectionRequestTimeout(timeout);
//                        builder.setSocketTimeout(timeout);
//                        return builder;
//                    }
//                })
//                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
//                    @Override
//                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder builder) {
//                        builder.setConnectionManagerShared(true);
//                        builder.setMaxConnTotal(connMax);
//                        builder.setThreadFactory(new ThreadFactory() {
//                            private AtomicLong id = new AtomicLong(1L);
//
//                            @Override
//                            public Thread newThread(Runnable r) {
//                                if (id.get() == Long.MAX_VALUE) {
//                                    id = new AtomicLong(1L);
//                                }
//                                return new Thread("es" + id.getAndIncrement());
//                            }
//                        });
//                        return builder;
//                    }
//                });

        RestHighLevelClient client = new RestHighLevelClient(builder);
        return client;
    }

    @Bean
    public RestClient esLowClient() {
        RestClient client = RestClient.builder(new HttpHost(host, port, "http"))
                .setFailureListener(new RestClient.FailureListener() {
                    private final Logger logger = LoggerFactory.getLogger(EsConfig.class);

                    @Override
                    public void onFailure(Node node) {
                        logger.warn("搜索数据失败--{}:{}-{}\n查询参数{}", node.getName(), node.getHost(), node.getVersion(), node.getAttributes());
                    }
                })
                //自定义发送消息规则
                .setNodeSelector(NodeSelector.ANY)
                .setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
                    @Override
                    public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder builder) {
                        builder.setAuthenticationEnabled(false);
                        builder.setConnectionRequestTimeout(timeout);
                        builder.setSocketTimeout(timeout);
                        return builder;
                    }
                })
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder builder) {
                        builder.setConnectionManagerShared(true);
                        builder.setMaxConnTotal(connMax);
                        builder.setThreadFactory(new ThreadFactory() {
                            private AtomicLong id = new AtomicLong(1L);

                            @Override
                            public Thread newThread(Runnable r) {
                                if (id.get() == Long.MAX_VALUE) {
                                    id = new AtomicLong(1L);
                                }
                                return new Thread("es" + id.getAndIncrement());
                            }
                        });
                        return builder;
                    }
                })
                .build();
        return client;
    }

}
