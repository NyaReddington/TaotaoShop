package com.taotao.rest.web;

import com.taotao.rest.pojo.TaotaoResult;
import com.taotao.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 查询类目树
 */
@RestController
@RequestMapping("/rest/itemcat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    /**
     * 查询出类目树返回给前端
     * @return
     */
    @CrossOrigin
    @RequestMapping("/all")
    public Object getItemCat() {
        TaotaoResult taotaoResult = itemCatService.getItemCat();
        return taotaoResult;
    }
}
