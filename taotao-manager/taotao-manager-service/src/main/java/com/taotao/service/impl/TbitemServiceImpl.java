package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.taotao.common.ExceptionUtil;
import com.taotao.common.IDUtils;
import com.taotao.common.SystemConstants;
import com.taotao.common.TaotaoResult;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.*;
import com.taotao.service.TbitemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class TbitemServiceImpl implements TbitemService {

    @Autowired(required = false)
    private TbItemMapper tbItemMapper;

    @Autowired(required = false)
    private TbItemDescMapper tbItemDescMapper;

    @Autowired(required = false)
    private TbItemParamItemMapper tbItemParamItemMapper;

    @Transactional(
        isolation = Isolation.READ_COMMITTED,
        propagation = Propagation.REQUIRED
    )

    /**
     * 分页查询商品列表
     */
    @Override
    public List<TbItem> itemPageService(int page, int row) {
        PageHelper.startPage(page, row);
        TbItemExample tbItemExample = new TbItemExample();
        TbItemExample.Criteria criteria = tbItemExample.createCriteria();
        criteria.andStatusBetween(Byte.parseByte("1"),Byte.parseByte("2"));
        List<TbItem> tbItems = tbItemMapper.selectByExample(tbItemExample);
//        System.out.println(tbItems.size());
        return tbItems;
    }


    /**
     * 获取商品总数量
     * @return
     */
    @Override
    public long itemCountService() {
        long count = tbItemMapper.countByExample(null);
        return count;
    }


    /**
     * 保存添加商品
     * @param tbItem
     * @param desc
     * @return
     */
    @Override
    public TaotaoResult saveItem(TbItem tbItem, String desc, String itemParams) {
        try {
            System.out.println(tbItem.toString());
            long itemId = IDUtils.genItemId();
            System.out.println("itemId：" + itemId);
            tbItem.setId(itemId);
            tbItem.setStatus(SystemConstants.TAOTAO_ITEM_STATUS_NORMAL);
            tbItem.setCreated(new Date());
            tbItem.setUpdated(new Date());
            System.out.println(tbItem.toString());
            tbItemMapper.insertSelective(tbItem);

            TbItemDesc tbItemDesc = new TbItemDesc();
            tbItemDesc.setItemDesc(desc);
            tbItemDesc.setCreated(new Date());
            tbItemDesc.setUpdated(new Date());
            tbItemDesc.setItemId(itemId);
            tbItemDescMapper.insert(tbItemDesc);

            TbItemParamItem itemParamItem = new TbItemParamItem();
            itemParamItem.setItemId(itemId);
            itemParamItem.setParamData(itemParams);
            itemParamItem.setCreated(new Date());
            itemParamItem.setUpdated(new Date());
            tbItemParamItemMapper.insert(itemParamItem);

            System.out.println("itemId2：" + itemParamItem.getItemId());

            return TaotaoResult.ok();
        } catch (Exception e) {
            log.error("保存商品失败", e);
            return TaotaoResult.build(SystemConstants.TAOTAO_RESULT_STATUS_ERROR, ExceptionUtil.getStackTrace(e));
        }

    }


    /**
     * 编辑商品
     * @param tbItem
     * @param desc
     * @param itemParams
     * @return
     */
    @Override
    public TaotaoResult editItem(TbItem tbItem, String desc, String itemParams) {
        try {
            long itemId = tbItem.getId();
            tbItem.setId(itemId);
            tbItem.setStatus(SystemConstants.TAOTAO_ITEM_STATUS_NORMAL);
            tbItem.setCreated(new Date());
            tbItem.setUpdated(new Date());
            tbItemMapper.updateByPrimaryKeySelective(tbItem);

            TbItemDescExample tbItemDescExample = new TbItemDescExample();
            tbItemDescExample.createCriteria().andItemIdEqualTo(itemId);
            List<TbItemDesc> tbItemDescs = tbItemDescMapper.selectByExampleWithBLOBs(tbItemDescExample);
            for (TbItemDesc tbItemDesc : tbItemDescs) {
                tbItemDesc.setItemId(itemId);
                tbItemDesc.setItemDesc(desc);
                tbItemDesc.setUpdated(new Date());
                tbItemDescMapper.updateByPrimaryKeyWithBLOBs(tbItemDesc);
            }

            TbItemParamItemExample tbItemParamItemExample = new TbItemParamItemExample();
            TbItemParamItemExample.Criteria criteria = tbItemParamItemExample.createCriteria();
            criteria.andItemIdEqualTo(itemId);
            List<TbItemParamItem> tbItemParamItems = tbItemParamItemMapper.selectByExample(tbItemParamItemExample);
            for (TbItemParamItem itemParamItem : tbItemParamItems) {
                itemParamItem.setItemId(itemId);
                itemParamItem.setParamData(itemParams);
//                System.out.println(itemParams);
                itemParamItem.setCreated(new Date());
                itemParamItem.setUpdated(new Date());
//                System.out.println(itemParamItem);
                tbItemParamItemMapper.updateByPrimaryKeyWithBLOBs(itemParamItem);
            }
            return TaotaoResult.ok();
        } catch (Exception e) {
            log.error("编辑商品失败", e);
            return TaotaoResult.build(SystemConstants.TAOTAO_RESULT_STATUS_ERROR, ExceptionUtil.getStackTrace(e));
        }
    }


    /**
     * 逻辑删除商品
     * @param id
     * @return
     */
    public TaotaoResult deleteTbItem(String id, String status) {
        try {
            /*TbItem tbItem = tbItemMapper.selectByPrimaryKey(Long.parseLong(id));
            TbItemExample tbItemExample = new TbItemExample();
            TbItemExample.Criteria criteria = tbItemExample.createCriteria();
            criteria.andStatusEqualTo(Byte.parseByte("3"));
            tbItemMapper.updateByExample(tbItem, tbItemExample);*/

            TbItem tbItem = new TbItem();
            tbItem.setId(Long.parseLong(id));
            tbItem.setStatus(Byte.parseByte(status));
            tbItemMapper.updateByPrimaryKeySelective(tbItem);

            return TaotaoResult.build(SystemConstants.TAOTAO_RESULT_STATUS_OK, null);
        } catch (Exception e) {
            log.error("更新商品状态失败", e);
            return TaotaoResult.build(SystemConstants.TAOTAO_RESULT_STATUS_ERROR, ExceptionUtil.getStackTrace(e));
        }
    }

}
