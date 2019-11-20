package com.taotao.portal.pojo;

import com.taotao.pojo.TbItem;

/**
 * 由于商品图片可能有多张， 为了满足前端页面展示需要
 * ，我们给TbItem扩展一个方法来处理多张图片的情况
 */
public class TbItemExt extends TbItem {

   /**
    * 将数据库中image字段存储的,分隔格式的多张图片，转化成一个String数组
    *
    * @return
    */
   public String[] getImages() {
      if (this.getImage() != null && this.getImage() != "") {
         String[] strings = this.getImage().split(",");
         return strings;
      }
      return null;
   }
}
