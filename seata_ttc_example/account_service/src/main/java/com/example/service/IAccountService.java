package com.example.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

@LocalTCC
public interface IAccountService {


    /**
     * debit
     * Try节点，预扣除金额
     *
     * @param userId
     * @param amount
     */
    @TwoPhaseBusinessAction(name = "deduct", commitMethod = "confirm", rollbackMethod = "cancel")
    boolean tryDeduct(@BusinessActionContextParameter(paramName = "userId") String userId,
                   @BusinessActionContextParameter(paramName = "amount")int amount);


    /**
     * Confirm阶段：确认扣减金额
     */
    boolean confirm(BusinessActionContext actionContext);

    /**
     * Cancel阶段：取消扣减，释放冻结金额
     */
    boolean cancel(BusinessActionContext actionContext);
}