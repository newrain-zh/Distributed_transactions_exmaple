package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 库存表实体类（支持TCC事务）
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("stock_tbl")
public class StockTbl {
    private Integer id;             // 库存记录ID
    private String  commodityCode;   // 商品编码
    private Integer count;          // 可用库存数量
    private Integer freezeCount;    // 冻结库存数量（TCC Try阶段预留）

}