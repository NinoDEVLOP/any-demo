package com.fun.learn.modules.controller;

import com.fun.learn.modules.model.CreateIndexDto;
import com.fun.learn.modules.model.IndexInfo;
import com.fun.learn.modules.model.Merchandise;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.AnalyzeRequest;
import org.elasticsearch.client.indices.AnalyzeResponse;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author LittlePart
 * @version 1.0
 * @date 2020/03/2020/3/31 22:47
 */
@Controller
@RequestMapping("esHigh")
public class EsHighApiController {

    @Autowired
    private RestHighLevelClient esHighLevelClient;

//    @RequestMapping("put")
//    @ResponseBody
//    public String put(@RequestBody ElasticSearchDto dto) {
//        IndexRequest request = new IndexRequest();
//        request.create(true)
//                .
//
//
//        esHighLevelClient.index();
//    }

    @RequestMapping("create")
    @ResponseBody
    public Object createIndex(@RequestBody CreateIndexDto createIndexDto) throws IOException {
        IndexResponse indexResponse = (IndexResponse) this.queryIndex(
                IndexInfo.builder()
                        .indexName(createIndexDto.getIndexName())
                        .build()
        );
        CreateIndexRequest request = new CreateIndexRequest(createIndexDto.getIndexName());
        request.source("{}", XContentType.JSON);
        CreateIndexResponse response = esHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        return response;
    }

    @RequestMapping("mappingIndex")
    @ResponseBody
    public String mappingIndex(String index, String jsonMapping) throws IOException {
        PutMappingRequest request = new PutMappingRequest(index);
        request.source(jsonMapping, XContentType.JSON);
        AcknowledgedResponse response = esHighLevelClient.indices().putMapping(request, RequestOptions.DEFAULT);
        return response.isAcknowledged() ? "success" : "failed";
    }

    @RequestMapping("searchAnalyze")
    @ResponseBody
    public Object searchAnalyze(String index, String field, String text) throws IOException {
        AnalyzeRequest request = AnalyzeRequest.withField(index, field, text);
        AnalyzeResponse response = esHighLevelClient.indices().analyze(request, RequestOptions.DEFAULT);
        return response.getTokens();
    }

//    @RequestMapping("addAnalyze")
//    @ResponseBody
//    public String addAnalyze(String indexName,String field, String analyzeName) {
//        AnalyzeRequest request = new AnalyzeRequest();
//        AnalyzeRequest.CustomAnalyzerBuilder customAnalyzerBuilder = AnalyzeRequest.buildCustomNormalizer(indexName);
//        customAnalyzerBuilder.addCharFilter();
//        customAnalyzerBuilder.addTokenFilter();
//
//    }

    @RequestMapping("indexInfo")
    @ResponseBody
    public Object queryIndex(IndexInfo dto) {
        GetIndexRequest request = new GetIndexRequest(dto.getIndexName());
        try {
            //request.addFeatures(GetIndexRequest.Feature.ALIASES, GetIndexRequest.Feature.MAPPINGS, GetIndexRequest.Feature.SETTINGS);
            GetIndexResponse response = esHighLevelClient.indices().get(request, RequestOptions.DEFAULT);
            Map<String, MappingMetaData> mappings = response.getMappings();
            Map<String, Settings> settings = response.getSettings();
            Map<String, List<AliasMetaData>> aliases = response.getAliases();
            String[] indices = response.getIndices();
            return new Gson().toJson(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "query is failed!";
    }

    @RequestMapping("index")
    @ResponseBody
    public Object index(Merchandise merchandise) {
        IndexRequest request = new IndexRequest();
        request.index("test-create-mapping-index");
        request.source(new Gson().toJson(merchandise), XContentType.JSON);
        try {
            IndexResponse response = esHighLevelClient.index(request, RequestOptions.DEFAULT);
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Failed!";
    }

    @RequestMapping("searchAll")
    @ResponseBody
    public Object searchAll(IndexInfo info) {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.indices(info.getIndexName());
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse response = esHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Failed!";
    }

    @RequestMapping("search")
    @ResponseBody
    public Object search(String indexName, String params) {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(params, "name", "shopName", "features", "supplyAddress").slop(1);
        searchSourceBuilder.query(queryBuilder);
        searchRequest.indices(indexName);
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse response = esHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Failed!";
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class TestIndexData {
        private String user;

        private Date postDate;

        private String message;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class TestIndexRespData {
        private String id;
        private String index;
        private String type;
        private String version;
        private String status;
        private String opName;
        private String op;
    }

}
