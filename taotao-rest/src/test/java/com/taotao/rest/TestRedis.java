package com.taotao.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestApplication.class)
public class TestRedis {

    @Autowired(required = false)
    private TbContentMapper tbContentMapper;

    @Autowired
//    private RedisTemplate redisTemplate;
    private StringRedisTemplate redisTemplate;

    @Test
    public void testPustRedis() {
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(89L);
        List<TbContent> tbContentList = tbContentMapper.selectByExample(example);

        ValueOperations<String, String> ops2 = redisTemplate.opsForValue();
//        ops2.set("test", JSON.toJSONString(tbContentList));

//        System.out.println(ops2.get("test", 0, -1));

        List<TbContent> list = JSON.parseArray(ops2.get("test", 0, -1), TbContent.class);
        for (TbContent tbContent : list) {
            System.out.println(tbContent);
        }
    }

}
