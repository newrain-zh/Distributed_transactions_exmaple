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
package com.example.controller;

import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.example.TestData;
import com.example.service.BusinessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
public class BusinessController {

    @Resource
    private BusinessService businessService;

    @RequestMapping(value = "/purchase", method = RequestMethod.GET, produces = "application/json")
    public String purchase(Boolean rollback) {
        businessService.purchase(TestData.USER_ID, TestData.COMMODITY_CODE, 2, rollback);
        return "success";
    }


    @GetMapping(value = "/init")
    public void init(){
        SqlRunner.db().delete("delete from order_tbl");
        SqlRunner.db().update("ALTER TABLE order_tbl AUTO_INCREMENT = 1");

        SqlRunner.db().delete("delete from account_freeze_record");
        SqlRunner.db().update("ALTER TABLE account_freeze_record AUTO_INCREMENT = 1");

        SqlRunner.db().delete("delete from stock_freeze ");
        SqlRunner.db().update("ALTER TABLE stock_freeze AUTO_INCREMENT = 1");

        SqlRunner.db().update("update stock_tbl set count = 100 , freeze_count=0 where id = 1 ");
        SqlRunner.db().update("update account_tbl set balance = 10000 where user_id='U10000'");
    }
}