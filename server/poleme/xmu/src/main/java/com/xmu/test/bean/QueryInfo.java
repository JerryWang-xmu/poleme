package com.xmu.test.bean;

public class QueryInfo {
    private String query;
    private String currentId;//查询信息 username
    private String currentMenuId;
    private int unitnum;
    private int pageNum =1;//当前页
    private int pageSize =5;//每页最大值

    public QueryInfo(String query, String currentId, String currentMenuId, int unitnum, int pageNum, int pageSize) {
        this.query = query;
        this.currentId = currentId;
        this.currentMenuId = currentMenuId;
        this.unitnum = unitnum;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public int getUnitnum() {
        return unitnum;
    }

    public void setUnitnum(int unitnum) {
        this.unitnum = unitnum;
    }

    @Override
    public String toString() {
        return "QueryInfo{" +
                "query='" + query + '\'' +
                ", currentId='" + currentId + '\'' +
                ", currentMenuId='" + currentMenuId + '\'' +
                ", unitnum=" + unitnum +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }

    public String getCurrentMenuId() {
        return currentMenuId;
    }

    public void setCurrentMenuId(String currentMenuId) {
        this.currentMenuId = currentMenuId;
    }

    public QueryInfo() {
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public QueryInfo(String currentId, int pageNum, int pageSize) {
        this.currentId = currentId;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public String getCurrentId() {
        return currentId;
    }

    public void setCurrentId(String currentId) {
        this.currentId = currentId;
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

}
