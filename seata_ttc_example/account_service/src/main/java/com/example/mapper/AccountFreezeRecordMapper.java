package com.example.mapper;

import com.example.entity.AccountFreezeRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 账户冻结记录Mapper接口
 */
@Mapper
public interface AccountFreezeRecordMapper {
    /**
     * 根据主键删除记录
     * @param xid 主键ID
     * @return 影响行数
     */
    int deleteByPrimaryKey(Integer xid);

    /**
     * 插入完整记录（所有字段）
     * @param record 账户冻结记录实体
     * @return 影响行数
     */
    int insert(AccountFreezeRecord record);

    /**
     * 插入非空字段记录（为null的字段不插入）
     * @param record 账户冻结记录实体
     * @return 影响行数
     */
    int insertSelective(AccountFreezeRecord record);

    /**
     * 根据主键查询记录
     * @param xid 主键ID
     * @return 账户冻结记录实体
     */
    AccountFreezeRecord selectByPrimaryKey(String xid);

    /**
     * 根据用户ID查询记录
     * @param userId 用户ID
     * @return 账户冻结记录列表
     */
    List<AccountFreezeRecord> selectByUserId(@Param("userId") String userId);

    /**
     * 根据状态查询记录
     * @param state 状态
     * @return 账户冻结记录列表
     */
    List<AccountFreezeRecord> selectByState(@Param("state") Integer state);

    /**
     * 根据主键更新非空字段
     * @param record 账户冻结记录实体
     * @return 影响行数
     */
    int updateByPrimaryKeySelective(AccountFreezeRecord record);

    /**
     * 根据主键更新所有字段
     * @param record 账户冻结记录实体
     * @return 影响行数
     */
    int updateByPrimaryKey(AccountFreezeRecord record);
}