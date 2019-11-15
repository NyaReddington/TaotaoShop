package com.taotao.web;

import com.taotao.common.EasyUIResult;
import com.taotao.common.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.TbContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private TbContentService tbContentService;

    /**
     * 根据分类id获取相应的内容数据并分页显示
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/query/list")
    @ResponseBody
    public EasyUIResult getContentList(Long categoryId, Integer page, Integer rows) {
        EasyUIResult result = tbContentService.getContentList(categoryId, page, rows);
        return result;
    }

    /**
     * 添加内容
     * @param content
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public TaotaoResult addContent(TbContent content) {
        TaotaoResult result = tbContentService.addContent(content);
        return result;
    }

    /**
     * 编辑内容
     * @param content
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public TaotaoResult editContent(TbContent content) {
        TaotaoResult result = tbContentService.editContent(content);
        return result;
    }

    /**
     * 删除内容
     * @param req
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult deleteContent(HttpServletRequest req) {
        TaotaoResult taotaoResult = null;
        String ids = req.getParameter("ids");
        for (String id : ids.split(",")) {
            taotaoResult = tbContentService.deleteContent(id);
        }
        return taotaoResult;
    }

}
