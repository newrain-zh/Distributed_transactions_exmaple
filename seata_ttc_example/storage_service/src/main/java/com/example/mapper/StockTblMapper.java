package com.example.mapper;

import com.example.entity.StockTbl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StockTblMapper {

    // 基础CRUD方法略（参考之前的实现）

    /**
     * TCC Try阶段：冻结库存（检查可用库存并预留）
     * @param commodityCode 商品编码
     * @param reduceCount 需冻结的数量
     * @return 影响行数（0表示库存不足）
     */
    int freezeStock(@Param("commodityCode") String commodityCode, @Param("reduceCount") Integer reduceCount);

    /**
     * TCC Confirm阶段：确认扣减库存（将冻结库存真正扣减）
     * @param commodityCode 商品编码
     * @param reduceCount 需扣减的数量
     * @return 影响行数
     */
    int confirmReduceStock(@Param("commodityCode") String commodityCode, @Param("reduceCount") Integer reduceCount);

    /**
     * TCC Cancel阶段：取消冻结（释放冻结的库存，恢复可用库存）
     * @param commodityCode 商品编码
     * @param reduceCount 需释放的数量
     * @return 影响行数
     */
    int cancelFreezeStock(@Param("commodityCode") String commodityCode,
                          @Param("reduceCount") Integer reduceCount,
                          @Param("count") Integer count
                          );

    /**
     * 根据商品编码查询库存（包含冻结字段）
     */
    StockTbl selectByCommodityCodeWithLock(@Param("commodityCode") String commodityCode);
}