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

import com.example.feign.OrderFeignClient;
import com.example.feign.StockFeignClient;
import com.example.service.BusinessService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
@Slf4j
public class BusinessServiceImpl implements BusinessService {


    @Resource
    private StockFeignClient stockFeignClient;
    @Resource
    private OrderFeignClient orderFeignClient;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "seata-tcc")
    // 开启全局事务
    public void purchase(String userId, String commodityCode, int orderCount, boolean forceRollback) {
        log.info("订购商品库存: " + RootContext.getXID());
        stockFeignClient.reduce(commodityCode, orderCount); // 扣减商品库存
        orderFeignClient.create(userId, commodityCode, orderCount); // 创建订单
        if (forceRollback) {
            throw new RuntimeException("force rollback!");
        }
    }


}