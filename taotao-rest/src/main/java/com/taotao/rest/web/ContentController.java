package com.taotao.rest.web;

import com.taotao.rest.pojo.TaotaoResult;
import com.taotao.rest.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    /**
     * 根据内容的分类ID查询内容列表，在AdsServiceImpl内调用
     * @param cid
     * @return
     */
    @RequestMapping("/category/{cid}")
    @ResponseBody
    public TaotaoResult getContentList(@PathVariable Long cid) {
        TaotaoResult result = null;
        try {
            result = contentService.getContentList(cid);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, e.getMessage());
        }
        return result;
    }

}
