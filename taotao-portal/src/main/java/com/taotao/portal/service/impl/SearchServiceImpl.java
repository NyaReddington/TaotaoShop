package com.taotao.portal.service.impl;

import com.taotao.common.HttpUtil;
import com.taotao.common.SystemConstants;
import com.taotao.common.TaotaoResult;
import com.taotao.pojo.SearchResult;
import com.taotao.portal.service.SearchService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {

    @Reference
    private com.taotao.dubbo.service.SearchService searchService;

//    @Value("${search.url}")
//    @Value("http://localhost:8083/search/solr/manager")
//    private String searchUrl;



    @Override
    public SearchResult search(String keywords, Integer pageNum, Integer pageSize) {
//        Map<String, String> params = new HashMap<>();
//        params.put("q", keywords);
//        params.put("pageNum", pageNum+"");
//        params.put("pageSize", pageSize+"");
//        String searchResultJson = HttpUtil.doPost(searchUrl + "/search", params,"UTF-8");
//        String searchResultJson = HttpUtil.doPost("http://localhost:8083/search/solr/manager/search", params,"UTF-8");

//        TaotaoResult taotaoResult = TaotaoResult.formatToPojo(searchResultJson, SearchResult.class);

        SearchResult search = searchService.search(keywords, 1, 30);
        TaotaoResult taotaoResult = TaotaoResult.ok(search);

        if(taotaoResult.getStatus().equals(SystemConstants.TAOTAO_RESULT_STATUS_OK)) {
            return (SearchResult) taotaoResult.getData();
        }
        return null;
    }

}
