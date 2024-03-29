package com.taotao.service;

import com.taotao.common.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemParam;

import java.util.List;

public interface TbItemParamService {

    // 分页查询所有规格列表
    List<TbItemParam> itemParamPageService(int page, int row);

    // 获取总数据条数
    long itemCountService();

    // 添加规格模板时候查询判断选择的目录是否已经添加过规格模板
    TaotaoResult getItemParamByItemCatId(Long id);

    // 添加规格模板
    TaotaoResult saveItemParam(Long itemCatId, String paramData);

    // 编辑规格模板
    TaotaoResult editItemParam(Long itemCatId, String paramData);

    // 删除规格模板
    TaotaoResult deleteTbItem(String id);

}
