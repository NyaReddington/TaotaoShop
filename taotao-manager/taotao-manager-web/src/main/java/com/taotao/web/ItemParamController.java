package com.taotao.web;

import com.taotao.common.EasyUIResult;
import com.taotao.common.TaotaoResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.TbItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 处理了商品规格模板的查询展示，添加，编辑，删除功能
 * 以及 编辑商品 时的 商品规格信息 和 商品描述信息的加载，用于编辑商品时数据回显
 */
@Controller
@RequestMapping("/item/param")
public class ItemParamController {

    @Autowired
    private TbItemParamService tbItemParamService;

    /**
     * 分页查询规格*模板*
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public EasyUIResult tbItemParamList(@RequestParam("page") Integer page,
                                 @RequestParam("rows") Integer rows){

        List<TbItemParam> tbItemParams = tbItemParamService.itemParamPageService(page, rows);
        for (TbItemParam tbItemParam : tbItemParams) {
            System.out.println(tbItemParam.getParamData() + " " + tbItemParam.getItemCatId());
        }
        long count = tbItemParamService.itemCountService();
        EasyUIResult easyUIResult = new EasyUIResult(count,tbItemParams);
        return easyUIResult;
    }


    /**
     * 添加规格*模板*时候查询判断选择的目录是否已经添加过规格模板
     * @param id
     * @return
     */
    @RequestMapping("/query/itemcatid/{id}")
    @ResponseBody
    public TaotaoResult queryItemParamByItemCatId(@PathVariable Long id) {
        TaotaoResult result = tbItemParamService.getItemParamByItemCatId(id);
        return result;
    }


    /**
     * 添加规格*模板*
     * @param itemCatId
     * @param paramData
     * @return
     */
    @RequestMapping("/save/{itemCatId}")
    @ResponseBody
    public TaotaoResult saveItemParam(@PathVariable Long itemCatId, String paramData) {
        TaotaoResult result = tbItemParamService.saveItemParam(itemCatId, paramData);
        return result;
    }


    /**
     * 编辑规格*模板*
     * @param itemCatId
     * @param paramData
     * @return
     */
    @RequestMapping("/update/{itemCatId}")
    @ResponseBody
    public TaotaoResult editItemParam(@PathVariable Long itemCatId, String paramData) {
        TaotaoResult result = tbItemParamService.editItemParam(itemCatId, paramData);
        return result;
    }


    /**
     * 删除规格*模板*
     * @param req
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult deleteItemParam(HttpServletRequest req) {
        TaotaoResult taotaoResult = null;
        String ids = req.getParameter("ids");
        System.out.println(ids);
        for (String id : ids.split(",")) {
            taotaoResult = tbItemParamService.deleteTbItem(id);
        }
        return taotaoResult;

    }


    /**
     * 商品编辑时加载 商品规格*信息* 用于回显数据
     * @param id
     * @return
     */
    @RequestMapping("/item/query/{id}")
    @ResponseBody
    public TaotaoResult queryItemParamItemByItemCatId(@PathVariable Long id) {
        TaotaoResult result = tbItemParamService.getItemParamItemByItemCatId(id);
        return result;
    }


    /**
     * 商品编辑时加载 商品*描述* 用于回显数据
     * @param id
     * @return
     */
    @RequestMapping("/item/desc/{id}")
    @ResponseBody
    public TaotaoResult queryItemDescByItemCatId(@PathVariable Long id) {
        TaotaoResult result = tbItemParamService.getItemDescByItemCatId(id);
        return result;
    }

}
