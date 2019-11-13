package com.taotao.web;

import com.taotao.common.EasyUIResult;
import com.taotao.common.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.TbitemService;
import com.taotao.service.impl.TbitemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 处理了商品列表的查询，添加商品，编辑商品，删除商品以及商品上下架的功能
 * 编辑商品时的商品规格信息和商品描述信息在商品规格controller中实现
 */
@Controller
public class ItemController {

    @Autowired
    private TbitemService tbitemService;

    /**
     * 商品列表分页查询
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIResult itemList(@RequestParam("page") Integer page,
                                 @RequestParam("rows") Integer rows){

        List<TbItem> tbItems = tbitemService.itemPageService(page,rows);
        long count = tbitemService.itemCountService();

        EasyUIResult easyUIResult = new EasyUIResult(count,tbItems);
        return  easyUIResult;
    }


    /**
     * 添加商品
     * @param tbItem
     * @param desc
     * @return
     */
    @RequestMapping("/item/save")
    @ResponseBody
    public TaotaoResult saveTbItem(TbItem tbItem, String desc, String itemParams) {
        TaotaoResult result = tbitemService.saveItem(tbItem, desc, itemParams);
        return result;
    }


    /**
     * 编辑商品
     * @param tbItem
     * @param desc
     * @return
     */
    @RequestMapping("/item/update")
    @ResponseBody
    public TaotaoResult editTbItem(TbItem tbItem, String desc, String itemParams) {
//        System.out.println("商品描述：" + desc);
        TaotaoResult result = tbitemService.editItem(tbItem, desc, itemParams);
        return result;
    }


    /**
     * 删除商品
     * @param req
     * @return
     */
    @RequestMapping("/item/delete")
//    @PostMapping("/item/delete")
    @ResponseBody
    public TaotaoResult delTbItem(HttpServletRequest req) {

        TaotaoResult taotaoResult = null;
//        String ids = (String) params.get("ids");
        String ids = req.getParameter("ids");
        System.out.println(ids);
        for (String id : ids.split(",")) {
            String status = "3";
            taotaoResult = tbitemService.deleteTbItem(id, status);
        }
        return taotaoResult;
    }


    /**
     * 商品下架
     * @param req
     * @return
     */
    @RequestMapping("/item/instock")
    @ResponseBody
    public TaotaoResult instockTbItem(HttpServletRequest req) {

        TaotaoResult taotaoResult = null;
//        String ids = (String) params.get("ids");
        String ids = req.getParameter("ids");
        System.out.println(ids);
        for (String id : ids.split(",")) {
            String status = "2";
            taotaoResult = tbitemService.deleteTbItem(id, status);
        }
        return taotaoResult;
    }


    /**
     * 商品上架
     * @param req
     * @return
     */
    @RequestMapping("/item/reshelf")
    @ResponseBody
    public TaotaoResult reshelfTbItem(HttpServletRequest req) {

        TaotaoResult taotaoResult = null;
//        String ids = (String) params.get("ids");
        String ids = req.getParameter("ids");
        System.out.println(ids);
        for (String id : ids.split(",")) {
            String status = "1";
            taotaoResult = tbitemService.deleteTbItem(id, status);
        }
        return taotaoResult;
    }

}
