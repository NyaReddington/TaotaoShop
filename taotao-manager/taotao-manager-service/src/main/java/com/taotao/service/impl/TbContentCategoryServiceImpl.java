package com.taotao.service.impl;

import com.taotao.common.EUTreeNode;
import com.taotao.common.SystemConstants;
import com.taotao.common.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.service.TbContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService {

    @Autowired(required = false)
    private TbContentCategoryMapper tbContentCategoryMapper;

    /**
     * 展示内容分类列表树
     * @param parentid
     * @return
     */
    @Override
    public List<EUTreeNode> getTbContentCategoryList(Long parentid) {
        // 根据parentid查询内容分类列表
        TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = tbContentCategoryExample.createCriteria();
        criteria.andParentIdEqualTo(parentid);
        criteria.andStatusEqualTo(1);
        List<TbContentCategory> tbContentCategoryList = tbContentCategoryMapper.selectByExample(tbContentCategoryExample);
        List<EUTreeNode> resultList = new ArrayList<>();
        for (TbContentCategory tbContentCategory : tbContentCategoryList) {
            EUTreeNode euTreeNode = new EUTreeNode();
            euTreeNode.setId(tbContentCategory.getId());
            euTreeNode.setText(tbContentCategory.getName());
            // 判断是否是父节点
            if (tbContentCategory.getIsParent()) {
                euTreeNode.setState("closed");
            } else {
                euTreeNode.setState("open");
            }
            resultList.add(euTreeNode);
        }
        return resultList;
    }


    /**
     * 新增内容分类子节点
     * @param parentId
     * @param name
     * @return
     */
    @Override
    public TaotaoResult addNode(Long parentId, String name) {
        //添加一个新节点
        //创建一个节点对象
        TbContentCategory node = new TbContentCategory();
        node.setName(name);
        node.setParentId(parentId);
        node.setIsParent(false);
        node.setCreated(new Date());
        node.setUpdated(new Date());
        node.setSortOrder(1);
        //状态。可选值:1(正常),2(删除)
        node.setStatus(1);
        //插入新节点。需要返回主键
        tbContentCategoryMapper.insert(node);
        //判断如果父节点的isparent不是true修改为true
        //取父节点的内容
        TbContentCategory parentNode = tbContentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parentNode.getIsParent()) {
            parentNode.setIsParent(true);
            tbContentCategoryMapper.updateByPrimaryKey(parentNode);
        }
        //把新节点返回
        return TaotaoResult.ok(node);

    }


    /**
     * 重命名内容分类节点
     * @param id
     * @param name
     * @return
     */
    @Override
    public TaotaoResult renameNode(Long id, String name) {
        TbContentCategory tbContentCategory = new TbContentCategory();
        tbContentCategory.setId(id);
        tbContentCategory.setName(name);
        tbContentCategory.setUpdated(new Date());
        tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
        return TaotaoResult.build(SystemConstants.TAOTAO_RESULT_STATUS_OK, null);
    }


    /**
     * 删除内容分类节点
     * @param parentId
     * @param id
     * @return
     */
    @Override
    public TaotaoResult deleteNode(Long parentId, Long id) {
        TbContentCategory node = tbContentCategoryMapper.selectByPrimaryKey(id);
        //先删除当前节点
        node.setStatus(2);
        node.setUpdated(new Date());
        tbContentCategoryMapper.updateByPrimaryKey(node);

        // 如果有子节点
        if (node.getIsParent()) {
            TbContentCategoryExample example = new TbContentCategoryExample();
            example.createCriteria().andParentIdEqualTo(id);
            // 获取到所有的子节点
            List<TbContentCategory> sonNodeList = tbContentCategoryMapper.selectByExample(example);
            for (TbContentCategory sonNode : sonNodeList) {
                // 如果子节点还有子节点，递归删除
                if (sonNode.getIsParent()){
                    deleteNode(sonNode.getParentId(), sonNode.getId());
                }
                // 删除子节点
                sonNode.setStatus(2);
                sonNode.setUpdated(new Date());
                tbContentCategoryMapper.updateByPrimaryKey(sonNode);
            }
        }
        // 没有子节点的情况
        // 取父节点信息
        TbContentCategory parentNode = tbContentCategoryMapper.selectByPrimaryKey(node.getParentId());
        //判断父节点下是否还有子节点
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria().andParentIdEqualTo(parentNode.getId());
        List<TbContentCategory> parentNodeSonList = tbContentCategoryMapper.selectByExample(example);
        int count = 0;
        for (TbContentCategory parentNodeSon : parentNodeSonList) {
//          if (!parentNodeSon.getId().equals(id)) {
                if (parentNodeSon.getStatus().equals(1)) {
                    count++;
                }
//          }
        }
        if (count <= 0) {
            parentNode.setIsParent(false);
            tbContentCategoryMapper.updateByPrimaryKey(parentNode);
        }
        return TaotaoResult.ok();
    }
}
