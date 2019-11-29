package com.taotao.rest.service.impl;

import com.taotao.common.ExceptionUtil;
import com.taotao.common.TaotaoResult;
import com.taotao.dubbo.service.TbItemDescService;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.pojo.TbItemDesc;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class TbItemDescServiceImpl implements TbItemDescService {

    @Autowired(required = false)
    private TbItemDescMapper tbItemDescMapper;

    @Override
    public TaotaoResult getItemDesc(Long id) {
        try {
            TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(id);
            return TaotaoResult.ok(tbItemDesc);
        } catch (Exception e) {
            return TaotaoResult.error("查询商品描述信息失败", ExceptionUtil.getStackTrace(e));
        }
    }
}
