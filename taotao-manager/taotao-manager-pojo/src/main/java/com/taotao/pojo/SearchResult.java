package com.taotao.pojo;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable {

    private long pageNum;
    private long totalPages;
    private List<SearchItem> itemList;

    public long getPageNum() {
        return pageNum;
    }

    public void setPageNum(long pageNum) {
        this.pageNum = pageNum;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public List<SearchItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SearchItem> itemList) {
        this.itemList = itemList;
    }
}
