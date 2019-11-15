package com.taotao.rest.service;

import com.taotao.rest.pojo.TaotaoResult;

public interface ContentService {

    // 根据内容的分类ID查询内容列表，在AdsServiceImpl内调用
    TaotaoResult getContentList(long cid);

}
