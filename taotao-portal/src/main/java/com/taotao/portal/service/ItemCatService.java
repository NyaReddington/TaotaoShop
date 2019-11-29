package com.taotao.portal.service;


import com.taotao.common.TaotaoResult;

public interface ItemCatService {

    // 基于数据库查询递归 类目树
    TaotaoResult getItemCat();

}
