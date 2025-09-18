package com.example.mapper;

import com.example.entity.StockFreeze;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 库存冻结记录数据访问接口
 */
public interface StockFreezeMapper {
    /**
     * 根据主键删除记录
     * @param xid 事务ID
     * @return 影响行数
     */
    int deleteByPrimaryKey(String xid);

    /**
     * 插入完整记录（所有字段）
     * @param record 库存冻结记录实体
     * @return 影响行数
     */
    int insert(StockFreeze record);

    /**
     * 插入非空字段记录（字段为null的不插入）
     * @param record 库存冻结记录实体
     * @return 影响行数
     */
    int insertSelective(StockFreeze record);

    /**
     * 根据主键查询记录
     * @param xid 事务ID
     * @return 库存冻结记录实体
     */
    StockFreeze selectByPrimaryKey(String xid);

    /**
     * 根据商品编码查询记录
     * @param commodityCode 商品编码
     * @return 库存冻结记录列表
     */
    List<StockFreeze> selectByCommodityCode(String commodityCode);

    /**
     * 根据状态查询记录
     * @param state 状态值
     * @return 库存冻结记录列表
     */
    List<StockFreeze> selectByState(Integer state);

    /**
     * 根据商品编码和状态查询记录
     * @param commodityCode 商品编码
     * @param state 状态值
     * @return 库存冻结记录列表
     */
    List<StockFreeze> selectByCommodityCodeAndState(
            @Param("commodityCode") String commodityCode,
            @Param("state") Integer state);

    /**
     * 根据主键更新非空字段（字段为null的不更新）
     * @param record 库存冻结记录实体
     * @return 影响行数
     */
    int updateByPrimaryKeySelective(StockFreeze record);

    /**
     * 根据主键更新所有字段
     * @param record 库存冻结记录实体
     * @return 影响行数
     */
    int updateByPrimaryKey(StockFreeze record);
}