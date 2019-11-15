package com.taotao.rest.service.impl;

import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.rest.pojo.TaotaoResult;
import com.taotao.rest.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired(required = false)
    private TbContentMapper tbContentMapper;

    /**
     * 根据内容的分类ID查询内容列表，在AdsServiceImpl内调用
     * @param cid
     * @return
     */
    @Override
    public TaotaoResult getContentList(long cid) {
        TbContentExample example = new TbContentExample();
        //添加条件
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        List<TbContent> list = tbContentMapper.selectByExample(example);
        return TaotaoResult.ok(list);
    }
}
