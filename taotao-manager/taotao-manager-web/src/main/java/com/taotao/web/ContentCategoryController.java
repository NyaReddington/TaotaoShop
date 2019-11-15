package com.taotao.web;

import com.taotao.common.EUTreeNode;
import com.taotao.common.TaotaoResult;
import com.taotao.service.TbContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

    @Autowired
    private TbContentCategoryService tbContentCategoryService;

    /**
     * 展示内容分类列表树
     * @param parentid
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<EUTreeNode> getContentCategoryList(@RequestParam(value="id", defaultValue="0") Long parentid) {
        List<EUTreeNode> list = tbContentCategoryService.getTbContentCategoryList(parentid);
        return list;
    }

    /**
     * 新增内容分类子节点
     * @param parentId
     * @param name
     * @return
     * @throws Exception
     */
    @RequestMapping("/create")
    @ResponseBody
    public TaotaoResult addNode(Long parentId, String name) throws Exception {
        TaotaoResult result = tbContentCategoryService.addNode(parentId, name);
        return result;
    }

    /**
     * 重命名内容分类节点
     * @param id
     * @param name
     * @return
     * @throws Exception
     */
    @RequestMapping("/update")
    @ResponseBody
    public TaotaoResult renameNode(Long id, String name) throws Exception {
        TaotaoResult result = tbContentCategoryService.renameNode(id, name);
        return result;
    }

    /**
     * 删除内容分类节点
     * @param parentId
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult deleteNode(Long parentId, Long id) throws Exception {
        TaotaoResult result = tbContentCategoryService.deleteNode(parentId, id);
        return result;
    }


}
