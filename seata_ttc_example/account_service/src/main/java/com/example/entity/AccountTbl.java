package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * 账户表实体类（对应 account_tbl 表）
 */
@Data
@AllArgsConstructor
@TableName("account_tbl")
public class AccountTbl {

    @TableId(type = IdType.INPUT)
    private String userId;          // 用户ID
    private Integer balance;        // 账户余额

    // 无参构造
    public AccountTbl() {
    }

    // toString 方法
    @Override
    public String toString() {
        return "AccountTbl{" +
                ", userId='" + userId + '\'' +
                ", balance=" + balance + '}';
    }
}