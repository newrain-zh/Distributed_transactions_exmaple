package com.example.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * 库存TCC服务接口
 */
@LocalTCC
public interface StockTccService {

    /**
     * Try阶段：冻结库存（资源检查与预留）
     * name：TCC事务名称，需唯一
     * commitMethod：Confirm阶段方法名
     * rollbackMethod：Cancel阶段方法名
     */
    @TwoPhaseBusinessAction(name = "stockReduceTcc", commitMethod = "confirm", rollbackMethod = "cancel")
    boolean tryReduce(
            BusinessActionContext context,
            @BusinessActionContextParameter(paramName = "commodityCode")    String commodityCode,
            @BusinessActionContextParameter(paramName = "count")      Integer count);

    /**
     * Confirm阶段：确认扣减库存（Try成功后执行）
     */
    boolean confirm(BusinessActionContext context);

    /**
     * Cancel阶段：取消冻结（Try失败后执行）
     */
    boolean cancel(BusinessActionContext context);
}