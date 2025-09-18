/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.service.impl;

import com.example.entity.OrderTbl;
import com.example.feign.AccountClient;
import com.example.mapper.OrderTblMapper;
import com.example.service.OrderService;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * The type Order service.
 *
 * @author jimin.jm @alibaba-inc.com
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {


    @Resource
    private OrderTblMapper orderTblMapper;

    @Resource
    private AccountClient accountClient;

    @Override
    public void create(String userId, String commodityCode, int orderCount) {
        log.info("创建订单 ... xid: " + RootContext.getXID());
        // 计算订单金额
        int orderMoney = calculate(commodityCode, orderCount);
        // 从账户余额扣款
        OrderTbl orderTbl = new OrderTbl();
        orderTbl.setUserId(userId);
        orderTbl.setCommodityCode(commodityCode);
        orderTbl.setCount(orderCount);
        orderTbl.setMoney(orderMoney);
        orderTblMapper.insert(orderTbl);
        accountClient.debit(userId, orderMoney);


    }

    @Override
    public boolean commit(BusinessActionContext businessActionContext) {
        log.info("创建订单 ... 进入commit xid: " + businessActionContext.getXid());
        return true;
    }

    @Override
    public boolean cancel(BusinessActionContext businessActionContext) {
        log.info("创建订单 ... 进入cancel xid: " + businessActionContext.getXid());
        return true;
    }

    private int calculate(String commodityId, int orderCount) {
        return 100 * orderCount;
    }

}