package com.example.entity;

import java.io.Serializable;

/**
 * 库存冻结记录表实体类
 * 对应数据库表：stock_freeze
 */
public class StockFreeze implements Serializable {
    /**
     * 事务ID（主键）
     */
    private String xid;

    /**
     * 商品编码
     */
    private String commodityCode;

    /**
     * 商品总数量
     */
    private Integer count;

    /**
     * 冻结数量
     */
    private Integer freezeCount;

    /**
     * 状态（默认0）
     * 可根据业务定义：例如0-冻结中，1-已解冻，2-已使用
     */
    private Integer state;

    // 序列化版本号
    private static final long serialVersionUID = 1L;

    // getter和setter方法
    public String getXid() {
        return xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getFreezeCount() {
        return freezeCount;
    }

    public void setFreezeCount(Integer freezeCount) {
        this.freezeCount = freezeCount;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "StockFreeze{" +
                "xid='" + xid + '\'' +
                ", commodityCode='" + commodityCode + '\'' +
                ", count=" + count +
                ", freezeCount=" + freezeCount +
                ", state=" + state +
                '}';
    }
}