package com.taotao.search.service;

import com.taotao.pojo.SearchItem;
import com.taotao.pojo.SearchResult;
import org.apache.solr.common.SolrDocumentList;

import java.util.List;
import java.util.Map;

public interface SearchService {

    SearchResult search(String keywords, Integer pageNum, Integer pageSize);



}
