package com.example.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

@LocalTCC
public interface OrderService {


    /**
     * 创建订单
     *
     * @param userId        用户ID
     * @param commodityCode 商品编号
     * @param orderCount    订购数量
     */
    @TwoPhaseBusinessAction(name = "insert", commitMethod = "commit", rollbackMethod = "cancel")
    void create(String userId,
                @BusinessActionContextParameter(paramName = "commodityCode") String commodityCode,
                @BusinessActionContextParameter(paramName = "orderCount") int orderCount);


    boolean commit(BusinessActionContext businessActionContext);

    boolean cancel(BusinessActionContext businessActionContext);
}