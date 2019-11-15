package com.taotao.service;

import com.taotao.common.EUTreeNode;
import com.taotao.common.TaotaoResult;

import java.util.List;

public interface TbContentCategoryService {

    // 展示内容分类列表树
    List<EUTreeNode> getTbContentCategoryList(Long parentid);

    // 新增内容分类子节点
    TaotaoResult addNode(Long parentId, String name);

    // 重命名内容分类节点
    TaotaoResult renameNode(Long id, String name);

    // 删除内容分类节点
    TaotaoResult deleteNode(Long parentId, Long id);
}
