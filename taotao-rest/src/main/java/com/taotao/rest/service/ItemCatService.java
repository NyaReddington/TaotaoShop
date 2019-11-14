package com.taotao.rest.service;

import com.taotao.rest.pojo.TaotaoResult;

public interface ItemCatService {

    // 基于数据库查询递归 类目树
    TaotaoResult getItemCat();

}
