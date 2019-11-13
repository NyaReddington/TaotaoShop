package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.taotao.common.SystemConstants;
import com.taotao.common.TaotaoResult;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.*;
import com.taotao.service.TbItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class TbItemParamServiceImpl implements TbItemParamService {

    @Autowired(required = false)
    private TbItemParamMapper tbItemParamMapper;    // 注入规格模板mapper

    @Autowired(required = false)
    private TbItemCatMapper tbItemCatMapper;    // 注入商品类别mapper

    @Autowired(required = false)
    private TbItemParamItemMapper tbItemParamItemMapper;    // 注入规格信息mapper

    @Autowired(required = false)
    private TbItemDescMapper tbItemDescMapper;  // 注入商品描述mapper

    @Override
    public List<TbItemParam> itemParamAllService() {
        List<TbItemParam> tbItemParams = tbItemParamMapper.selectByExample(null);
        return tbItemParams;
    }

    /**
     * 分页查询所有规格列表
     * @param page
     * @param row
     * @return
     */
    @Override
    public List<TbItemParam> itemParamPageService(int page, int row) {
        PageHelper.startPage(page,row);
        List<TbItemParam> tbItemParams = tbItemParamMapper.selectByExampleWithBLOBs(null);
        for (TbItemParam tbItemParam : tbItemParams) {
            Long itemCatId = tbItemParam.getItemCatId();
            TbItemCat tbItemCat = tbItemCatMapper.selectByPrimaryKey(itemCatId);    // 查询出商品类别，设置到商品规格中
            tbItemParam.setItemCatName(tbItemCat.getName());
        }
        return tbItemParams;
    }

    /**
     * 获取总数据条数
     * @return
     */
    @Override
    public long itemCountService() {
        long count = tbItemParamMapper.countByExample(null);
        return count;
    }


    /**
     * 添加规格模板时候查询判断选择的目录是否已经添加过规格模板
     * @param id
     * @return
     */
    @Override
    public TaotaoResult getItemParamByItemCatId(Long id) {
        TbItemParamExample condition = new TbItemParamExample();
        condition.createCriteria().andItemCatIdEqualTo(id);
        List<TbItemParam> tbItemParamList = tbItemParamMapper.selectByExampleWithBLOBs(condition);
        if(tbItemParamList.size() > 0) {
            return TaotaoResult.ok(tbItemParamList.get(0));
        }
        return TaotaoResult.build(SystemConstants.TAOTAO_RESULT_STATUS_ERROR, "未查询到数据");

    }


    /**
     * 添加规格模板
     * @param itemCatId
     * @param paramData
     * @return
     */
    @Override
    public TaotaoResult saveItemParam(Long itemCatId, String paramData) {
        TbItemParam tbItemParam = new TbItemParam();
        tbItemParam.setItemCatId(itemCatId);
        tbItemParam.setParamData(paramData);
        tbItemParam.setCreated(new Date());
        tbItemParam.setUpdated(new Date());
        tbItemParamMapper.insert(tbItemParam);
        return TaotaoResult.ok();
    }


    /**
     * 编辑规格模板
     * @param itemCatId
     * @param paramData
     * @return
     */
    @Override
    public TaotaoResult editItemParam(Long itemCatId, String paramData) {
        TbItemParamExample tbItemParamExample = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = tbItemParamExample.createCriteria();
        criteria.andItemCatIdEqualTo(itemCatId);
        List<TbItemParam> tbItemParams = tbItemParamMapper.selectByExampleWithBLOBs(tbItemParamExample);
        for (TbItemParam tbItemParam : tbItemParams) {
            tbItemParam.setParamData(paramData);
            tbItemParam.setUpdated(new Date());
            tbItemParamMapper.updateByPrimaryKeyWithBLOBs(tbItemParam);
        }
        return TaotaoResult.ok();
    }


    /**
     * 删除规格模板
     * @param id
     * @return
     */
    @Override
    public TaotaoResult deleteTbItem(String id) {
        tbItemParamMapper.deleteByPrimaryKey(Long.parseLong(id));
        return TaotaoResult.build(SystemConstants.TAOTAO_RESULT_STATUS_OK, null);
    }


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
