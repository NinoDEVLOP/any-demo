package com.fun.learn.modules.controller;

import com.fun.learn.modules.model.ElasticSearchDto;
import com.fun.learn.util.ThreadUtil;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author LittlePart
 * @version 1.0
 * @date 2020/03/2020/3/31 20:28
 */
@Controller
@RequestMapping("/es")
public class EsLowApiController {

    @Autowired
    public RestClient esClient;

    @RequestMapping("/put")
    @ResponseBody
    public String putData(@RequestBody ElasticSearchDto dto) {
        Request request = new Request("PUT", "/");
        request.addParameter("pretty", "true");
        request.setEntity(new NStringEntity("", ContentType.APPLICATION_JSON));
        //最终执行请求
        Future<Response> responseFuture = ThreadUtil.getScheduledPoolInst().submit(() -> {
            try {
                return esClient.performRequest(request);
            } catch (IOException e) {
                return null;
            }
        });
        try {
            Response response = responseFuture.get();
            byte[] dataByte = new byte[Long.valueOf(response.getEntity().getContentLength()).intValue()];
            response.getEntity().getContent().read(dataByte);
            String data = new String(dataByte);
            return data;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/get")
    @ResponseBody
    public String queryData(@RequestBody ElasticSearchDto dto) {
        Request request = new Request("GET", "/");
        request.addParameter("pretty", "true");
        request.setEntity(new NStringEntity("", ContentType.APPLICATION_JSON));
        //最终执行请求
        Future<Response> responseFuture = ThreadUtil.getScheduledPoolInst().submit(() -> {
            try {
                return esClient.performRequest(request);
            } catch (IOException e) {
                return null;
            }
        });
        try {
            Response response = responseFuture.get();
            byte[] dataByte = new byte[Long.valueOf(response.getEntity().getContentLength()).intValue()];
            response.getEntity().getContent().read(dataByte);
            String data = new String(dataByte);
            return data;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
