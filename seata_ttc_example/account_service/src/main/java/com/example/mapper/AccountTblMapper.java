package com.example.mapper;

import com.example.entity.AccountTbl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 账户表 Mapper 接口
 */
@Mapper
public interface AccountTblMapper {

    /**
     * 根据 ID 查询账户
     * @param id 账户ID
     * @return 账户信息
     */
    AccountTbl selectById(@Param("id") Integer id);

    /**
     * 根据用户 ID 查询账户
     * @param userId 用户ID
     * @return 账户信息（通常一个用户对应一个账户）
     */
    AccountTbl selectByUserId(@Param("userId") String userId);

    /**
     * 查询所有账户
     * @return 账户列表
     */
    List<AccountTbl> selectAll();

    /**
     * 新增账户
     * @param account 账户信息
     * @return 影响行数
     */
    int insert(AccountTbl account);

    /**
     * 全字段更新账户信息
     * @param account 账户信息（需包含 ID）
     * @return 影响行数
     */
    int update(AccountTbl account);

    /**
     * 根据 ID 删除账户
     * @param id 账户ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Integer id);

    /**
     * 扣减账户余额（如消费、转账出）
     * @param userId 用户ID
     * @param amount 扣减金额（正数）
     * @return 影响行数
     */
    int deductBalance(@Param("userId") String userId, @Param("amount") Integer amount);

    /**
     * 增加账户余额（如充值、转账入）
     * @param userId 用户ID
     * @param amount 增加金额（正数）
     * @return 影响行数
     */
    int addBalance(@Param("userId") String userId, @Param("amount") Integer amount);

    /**
     * 冻结金额（如 TCC 事务的 Try 阶段）
     * @param userId 用户ID
     * @param amount 冻结金额（正数）
     * @return 影响行数
     */
    int freezeAmount(@Param("userId") String userId, @Param("amount") Integer amount);

    /**
     * 解冻金额（如 TCC 事务的 Cancel 阶段）
     * @param userId 用户ID
     * @param amount 解冻金额（正数）
     * @return 影响行数
     */
    int unfreezeAmount(@Param("userId") String userId, @Param("amount") Integer amount);

    /**
     * 扣减冻结金额（如 TCC 事务的 Confirm 阶段，确认扣减）
     * @param userId 用户ID
     * @param amount 扣减的冻结金额（正数）
     * @return 影响行数
     */
    int deductFreezeAmount(@Param("userId") String userId, @Param("amount") Integer amount);
}