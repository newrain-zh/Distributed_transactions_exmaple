package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 账户冻结记录表实体类
 * 对应表account_freeze_record
 */
@TableName("account_freeze_record")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountFreezeRecord{

    /**
     * 主键ID
     */
    @TableId(type = IdType.INPUT)
    private String xid;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 账户余额
     */
    private Integer balance;

    /**
     * 冻结金额
     */
    private Integer freezeAmount;

    /**
     * 状态（非空）
     * 可根据业务定义：例如0-冻结中，1-已解冻，2-已扣减等
     */
    private Integer state;

}