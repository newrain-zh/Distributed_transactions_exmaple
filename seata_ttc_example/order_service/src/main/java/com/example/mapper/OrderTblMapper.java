package com.example.mapper;

import com.example.entity.OrderTbl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单表 Mapper 接口
 */
@Mapper
public interface OrderTblMapper {

    /**
     * 根据订单ID查询订单
     * @param id 订单ID
     * @return 订单信息
     */
    OrderTbl selectById(@Param("id") Integer id);

    /**
     * 根据用户ID查询订单列表
     * @param userId 用户ID
     * @return 该用户的所有订单
     */
    List<OrderTbl> selectByUserId(@Param("userId") String userId);

    /**
     * 查询所有订单
     * @return 订单列表
     */
    List<OrderTbl> selectAll();

    /**
     * 新增订单（自增ID会自动回填到实体类）
     * @param order 订单信息
     * @return 影响行数
     */
    int insert(OrderTbl order);

    /**
     * 更新订单信息（动态更新非空字段）
     * @param order 订单信息（需包含ID）
     * @return 影响行数
     */
    int update(OrderTbl order);

    /**
     * 根据订单ID删除订单
     * @param id 订单ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Integer id);

    /**
     * 根据商品编码查询订单列表
     * @param commodityCode 商品编码
     * @return 该商品的所有订单
     */
    List<OrderTbl> selectByCommodityCode(@Param("commodityCode") String commodityCode);
}