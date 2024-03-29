package com.taotao.portal.service.impl;

import com.taotao.common.HttpUtil;
import com.taotao.common.JsonUtils;
import com.taotao.common.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.portal.service.AdsService;
import com.taotao.rest.pojo.ADItem;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdsServiceImpl implements AdsService {

    @Value("http://localhost:8081")
    private String REST_BASE_URL;
    @Value("/content/category/89")
    private String INDEX_AD1_URL;

    /**
     * 调用服务层的服务根据内容分类id查询内容管理系统的内容
     * 需要一个描述数据结构的pojo类
     * 使用Httpclient调用服务层的查询方法
     * @return
     */
    @Override
    public String getAdItemList() {
        //调用服务层的服务查询打广告位的数据
        String result = HttpUtil.doGet(REST_BASE_URL + INDEX_AD1_URL);
        //把json数据转换成对象
        TaotaoResult taotaoResult = TaotaoResult.formatToList(result, TbContent.class);
        List<ADItem> itemList = new ArrayList<>();
        if (taotaoResult.getStatus() == 200 ) {
            List<TbContent> contentList = (List<TbContent>) taotaoResult.getData();
            for (TbContent tbContent : contentList) {
                ADItem item = new ADItem();
                item.setHeight(240);
                item.setWidth(670);
                item.setSrc(tbContent.getPic());
                item.setHeightB(240);
                item.setWidthB(550);
                item.setSrcB(tbContent.getPic2());
                item.setAlt(tbContent.getTitleDesc());
                item.setHref(tbContent.getUrl());
                itemList.add(item);
            }
        }
        return JsonUtils.objectToJson(itemList);
    }
}
