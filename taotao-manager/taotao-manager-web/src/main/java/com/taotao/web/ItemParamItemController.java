package com.taotao.web;

import com.taotao.common.TaotaoResult;
import com.taotao.service.TbItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 编辑商品 时的 商品规格信息 和 商品描述信息的加载，用于编辑商品时数据回显
 */
@Controller
@RequestMapping("/item/param/item")
public class ItemParamItemController {

    @Autowired
    private TbItemParamItemService tbItemParamItemService;

    /**
     * 商品编辑时加载 商品规格*信息* 用于回显数据
     * @param id
     * @return
     */
    @RequestMapping("/query/{id}")
    @ResponseBody
    public TaotaoResult queryItemParamItemByItemCatId(@PathVariable Long id) {
        TaotaoResult result = tbItemParamItemService.getItemParamItemByItemCatId(id);
        return result;
    }


    /**
     * 商品编辑时加载 商品*描述* 用于回显数据
     * @param id
     * @return
     */
    @RequestMapping("/desc/{id}")
    @ResponseBody
    public TaotaoResult queryItemDescByItemCatId(@PathVariable Long id) {
        TaotaoResult result = tbItemParamItemService.getItemDescByItemCatId(id);
        return result;
    }
}
