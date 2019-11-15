package com.taotao.portal.service;

import org.springframework.stereotype.Component;

@Component
public interface AdsService {

    // 调用服务层的服务根据内容分类id查询内容管理系统的内容
    String getAdItemList();

}
