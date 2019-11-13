package com.taotao.service;

import com.taotao.common.TaotaoResult;

public interface TbItemParamItemService {

    // 商品编辑时加载 商品规格*信息* 用于回显数据
    TaotaoResult getItemParamItemByItemCatId(Long id);

    // 商品编辑时加载 商品*描述* 用于回显数据
    TaotaoResult getItemDescByItemCatId(Long id);

}
