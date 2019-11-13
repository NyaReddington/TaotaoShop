package com.taotao.service.impl;

import com.taotao.common.SystemConstants;
import com.taotao.common.TaotaoResult;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemDescExample;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.service.TbItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TbItemParamItemServiceImpl implements TbItemParamItemService {

    @Autowired(required = false)
    private TbItemParamItemMapper tbItemParamItemMapper;    // 注入规格信息mapper

    @Autowired(required = false)
    private TbItemDescMapper tbItemDescMapper;  // 注入商品描述mapper

    /**
     * 商品编辑时加载 商品规格*信息* 用于回显数据
     * @param id
     * @return
     */
    @Override
    public TaotaoResult getItemParamItemByItemCatId(Long id) {
        TbItemParamItemExample condition = new TbItemParamItemExample();
        condition.createCriteria().andItemIdEqualTo(id);
        List<TbItemParamItem> tbItemParamItems = tbItemParamItemMapper.selectByExampleWithBLOBs(condition);
        if(tbItemParamItems.size() > 0) {
            return TaotaoResult.ok(tbItemParamItems.get(0));
        }
        return TaotaoResult.build(SystemConstants.TAOTAO_RESULT_STATUS_ERROR, "未查询到数据");
    }


    /**
     * 商品编辑时加载 商品*描述* 用于回显数据
     * @param id
     * @return
     */
    @Override
    public TaotaoResult getItemDescByItemCatId(Long id) {
        TbItemDescExample condition = new TbItemDescExample();
        condition.createCriteria().andItemIdEqualTo(id);
        List<TbItemDesc> tbItemDescs = tbItemDescMapper.selectByExampleWithBLOBs(condition);
        if (tbItemDescs.size() > 0) {
            return TaotaoResult.ok(tbItemDescs.get(0));
        }
        return TaotaoResult.build(SystemConstants.TAOTAO_RESULT_STATUS_ERROR, "未查询到数据");
    }
}
