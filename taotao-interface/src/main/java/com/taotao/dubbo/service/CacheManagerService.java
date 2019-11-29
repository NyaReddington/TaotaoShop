package com.taotao.dubbo.service;


import com.taotao.common.TaotaoResult;

public interface CacheManagerService {

    TaotaoResult clearCache(String key);

    TaotaoResult clearContentCache(Long contentCategoryId);
}
