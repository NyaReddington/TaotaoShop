package com.taotao.rest.service.impl;

import com.taotao.common.TaotaoResult;
import com.taotao.rest.service.CacheManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CacheManagerServiceImpl implements CacheManagerService {

    @Autowired
    private RedisTemplate redisTemplate;

//    @Value("${taotao.redis.keys.global_prefix}")
    @Value("test_taotao_content_cat_")
    private String globalPrefix;

//    @Value("${taotao.redis.keys.content_prefix}")
    @Value("89")
    private String contentKeyPrefix;


    @Override
    public TaotaoResult clearCache(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.error("缓存清理失败: " + e.getMessage());
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult clearContentCache(Long contentCategoryId) {
//        String key = globalPrefix + contentKeyPrefix + contentCategoryId;
        String key = globalPrefix + contentCategoryId;
        return clearCache(key);
    }
}
