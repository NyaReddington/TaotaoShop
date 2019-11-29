package com.taotao.rest.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.taotao.common.TaotaoResult;
import com.taotao.dubbo.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;

import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired(required = false)
    private TbContentMapper tbContentMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 根据内容的分类ID查询内容列表，在AdsServiceImpl内调用
     * @param cid
     * @return
     */
    @Override
    public TaotaoResult getContentList(long cid) {

        // 添加缓存要求： 如果访问缓存异常，应该正常查询数据库，而不是直接报错
        // 缓存逻辑： 请求进来，先去Redis缓存中查询，如果有，直接返回。
        // 否则，查询数据库。将结果放入Redis缓存中，然后返回。

        // 从Redis缓存中尝试获取需要的数据
        List<TbContent> tbContentList = null;
        try {
            tbContentList = getTbContentFromCache(cid);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Redis缓存中没有数据
        if (tbContentList == null) {
            // 从数据库中查询出指定分类下的内容数据
            TbContentExample example = new TbContentExample();
            // 设置查询条件
            TbContentExample.Criteria criteria = example.createCriteria();
            criteria.andCategoryIdEqualTo(cid);
            // 开始查询
//            List<TbContent> list = tbContentMapper.selectByExample(example);
            tbContentList = tbContentMapper.selectByExample(example);
            // 放入Redis缓存中， 下次就不用再查询数据库了
            try {
                pushTbContent2Cache(cid, tbContentList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return TaotaoResult.ok(tbContentList);
    }

    private List<TbContent> getTbContentFromCache(long cid) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String jsonValue = ops.get("test_taotao_content_cat_" + cid, 0, -1);
        List<TbContent> list = JSON.parseArray(jsonValue, TbContent.class);
        return list;
    }

    private void pushTbContent2Cache(Long cid, List<TbContent> list) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set("test_taotao_content_cat_" + cid, JSON.toJSONString(list));
    }

}
