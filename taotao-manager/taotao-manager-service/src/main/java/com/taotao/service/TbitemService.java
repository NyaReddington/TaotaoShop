package com.taotao.service;

import com.taotao.common.TaotaoResult;
import com.taotao.pojo.TbItem;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TbitemService {
    // 分页查询商品列表
    List<TbItem> itemPageService(int page, int row);

    // 获取商品总数量
    long itemCountService();

    // 保存添加商品
    TaotaoResult saveItem(TbItem tbItem, String desc, String itemParams);

    // 编辑商品
    TaotaoResult editItem(TbItem tbItem, String desc, String itemParams);

    // 逻辑删除商品
    TaotaoResult deleteTbItem(String id, String status);

}
