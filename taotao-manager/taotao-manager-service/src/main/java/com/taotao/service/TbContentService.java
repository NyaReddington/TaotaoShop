package com.taotao.service;

import com.taotao.common.EasyUIResult;
import com.taotao.common.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface TbContentService {

    // 根据分类id获取相应的内容数据并分页显示
    EasyUIResult getContentList(long catId, Integer page, Integer rows);

    // 添加内容
    TaotaoResult addContent(TbContent content);

    // 编辑内容
    TaotaoResult editContent(TbContent content);

    // 删除内容
    TaotaoResult deleteContent(String id);

}
