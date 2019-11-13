package com.taotao.web;

import com.taotao.common.EUTreeNode;
import com.taotao.service.TbitemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 实现商品类目树的查询
 */
@RestController
public class ItemCatController {

    @Autowired
    private TbitemCatService tbitemCatService;

    @RequestMapping("/item/cat/list")   // 该请求在common.js内
    public List<EUTreeNode> gatAllItemCat(@RequestParam(value = "id", defaultValue = "0") long parentId){
        List<EUTreeNode> itemCatTree = tbitemCatService.getItemCatTree(parentId);
        return itemCatTree;
    }
}
