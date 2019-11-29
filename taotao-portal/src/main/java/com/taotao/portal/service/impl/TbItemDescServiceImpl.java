package com.taotao.portal.service.impl;

import com.taotao.common.HttpUtil;
import com.taotao.common.SystemConstants;
import com.taotao.common.TaotaoResult;
import com.taotao.pojo.TbItemDesc;
import com.taotao.portal.service.TbItemDescService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TbItemDescServiceImpl implements TbItemDescService {

//    @Value("${rest.url}")
    /*@Value("http://localhost:8081")
    private String restUrl;*/

    @Reference
    private com.taotao.dubbo.service.TbItemDescService tbItemDescDubboService;

    @Override
    public String getTbItemDesc(Long id) {
//        String jsonResult = HttpUtil.doGet(restUrl + "/item/desc/"+id);
//        TaotaoResult taotaoResult = TaotaoResult.formatToPojo(jsonResult, TbItemDesc.class);

        TaotaoResult taotaoResult = tbItemDescDubboService.getItemDesc(id);

        if(taotaoResult.getStatus().equals(SystemConstants.TAOTAO_RESULT_STATUS_OK)) {
            TbItemDesc itemDesc = (TbItemDesc) taotaoResult.getData();
            if (!(itemDesc == null)) {
                log.info("查询到商品详情");
                return itemDesc.getItemDesc();
            } else {
                log.error("商品详情为空");
            }
        } else {
            log.error("请求商品描述信息出错: " + taotaoResult.getMsg()+"\n异常：" + taotaoResult.getData());
            return "<span>暂无描述</span>";
        }
        return null;
    }
}
