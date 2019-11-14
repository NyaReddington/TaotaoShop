package com.taotao.rest.service.impl;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.rest.pojo.ItemCat;
import com.taotao.rest.pojo.TaotaoResult;
import com.taotao.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired(required = false)
    private TbItemCatMapper tbItemCatMapper;

    /**
     * 基于数据库查询递归 类目树
     * @return
     */
    @Override
    public TaotaoResult getItemCat() {
        List<Object> itemCatList = loadItemCat(0L);
        return TaotaoResult.ok(itemCatList);
    }

    private List<Object> loadItemCat(Long parentId) {
        List<Object> result = new ArrayList<>();

        TbItemCatExample condition = new TbItemCatExample();
        condition.createCriteria()
                .andStatusNotEqualTo(2)
                .andParentIdEqualTo(parentId);

        List<TbItemCat> tbItemCatList = tbItemCatMapper.selectByExample(condition);
//        System.out.println(tbItemCatList);
        int count = 0;
        for(TbItemCat tbItemCat : tbItemCatList) {
//            System.out.println(tbItemCat.getParentId());
            if(tbItemCat.getIsParent()) {
                ItemCat itemCat = new ItemCat();
                itemCat.setUrl("/products/"+tbItemCat.getId()+".html");
                if(parentId.equals(0)) {
                    itemCat.setName("<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
                }else {
                    itemCat.setName(tbItemCat.getName());
                }
                itemCat.setItems(loadItemCat(tbItemCat.getId()));
                result.add(itemCat);
                count++;
                if(count >= 14) {
                    break;
                }
            } else {
                String itemCat = "/products/"+tbItemCat.getId()+".html|"+tbItemCat.getName();
                result.add(itemCat);
            }
        }
        return result;
    }



}
