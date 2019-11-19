package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.EasyUIResult;
import com.taotao.common.HttpUtil;
import com.taotao.common.TaotaoResult;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.service.TbContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.sun.corba.se.impl.util.RepositoryId.cache;

@Service
public class TbContentServiceImpl implements TbContentService {

    @Autowired(required = false)
    private TbContentMapper tbContentMapper;

    /**
     * 根据分类id获取相应的内容数据并分页显示
     * @param catId
     * @param page
     * @param rows
     * @return
     */
    @Override
    public EasyUIResult getContentList(long catId, Integer page, Integer rows) {
        //根据category_id查询内容列表
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(catId);
        //分页处理
        PageHelper.startPage(page, rows);
        List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
        //取分页信息
        PageInfo<TbContent> pageInfo = new PageInfo<>(list);
        EasyUIResult result = new EasyUIResult(pageInfo.getTotal(), list);
        return result;

    }


    /**
     * 添加内容
     * @param content
     * @return
     */
    @Override
    public TaotaoResult addContent(TbContent content) {
        //把图片信息保存至数据库
        content.setCreated(new Date());
        content.setUpdated(new Date());
        //把内容信息添加到数据库
        tbContentMapper.insert(content);

        try {
            Long contentCatId = content.getCategoryId();
            HttpUtil.doGet("http://localhost:8081/cache/clear/content/cat/" + contentCatId);
            System.out.println("清除了添加图片的缓存");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return TaotaoResult.ok();
    }


    /**
     * 编辑内容
     * @param content
     * @return
     */
    @Override
    public TaotaoResult editContent(TbContent content) {
        TbContent tbContent = tbContentMapper.selectByPrimaryKey(content.getId());
        tbContent.setTitle(content.getTitle());
        tbContent.setSubTitle(content.getSubTitle());
        tbContent.setTitleDesc(content.getTitleDesc());
        tbContent.setUrl(content.getUrl());
        tbContent.setPic(content.getPic());
        tbContent.setPic2(content.getPic2());
        // 内容无法更新
        tbContent.setContent(content.getContent());
        tbContent.setUpdated(new Date());
        tbContentMapper.updateByPrimaryKey(tbContent);

        try {
            Long contentCatId = content.getCategoryId();
            HttpUtil.doGet("http://localhost:8081/cache/clear/content/cat/" + contentCatId);
            System.out.println("清除了编辑图片的缓存");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return TaotaoResult.ok();
    }


    /**
     * 删除内容
     * @param id
     * @return
     */
    @Override
    public TaotaoResult deleteContent(String id) {
        tbContentMapper.deleteByPrimaryKey(Long.parseLong(id));

        try {
            TbContent tbContent = tbContentMapper.selectByPrimaryKey(Long.parseLong(id));
            Long contentCatId = tbContent.getCategoryId();
            HttpUtil.doGet("http://localhost:8081/cache/clear/content/cat/" + contentCatId);
            System.out.println("清除了删除图片的缓存");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return TaotaoResult.ok();
    }

}
