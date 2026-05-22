package com.example.demo.dto;

import com.github.pagehelper.PageInfo;
import java.util.List;

public class PageResponse<T> {
    private long total;          // 总记录数
    private int pageNum;        // 当前页码
    private int pageSize;       // 每页条数
    private int pages;          // 总页数
    private List<T> list;       // 当前页数据

    public PageResponse() {
    }

    public PageResponse(long total, int pageNum, int pageSize, int pages, List<T> list) {
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pages = pages;
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}