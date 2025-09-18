package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 订单表实体类（对应 order_tbl 表）
 */

@TableName("order_tbl")
public class OrderTbl {

    @TableId(type = IdType.AUTO)
    private Integer id;             // 订单ID（自增主键）
    private String  userId;          // 用户ID
    private String  commodityCode;   // 商品编码
    private Integer count;          // 购买数量
    private Integer money;          // 订单金额（单位：分，避免浮点数精度问题）

    // 无参构造
    public OrderTbl() {
    }

    // 全参构造（不含id，适用于新增订单）
    public OrderTbl(String userId, String commodityCode, Integer count, Integer money) {
        this.userId        = userId;
        this.commodityCode = commodityCode;
        this.count         = count;
        this.money         = money;
    }

    // getter 和 setter 方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    // toString 方法
    @Override
    public String toString() {
        return "OrderTbl{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", commodityCode='" + commodityCode + '\'' +
                ", count=" + count +
                ", money=" + money +
                '}';
    }
}