/*
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.example.service.impl;

import com.example.XState;
import com.example.entity.AccountFreezeRecord;
import com.example.entity.AccountTbl;
import com.example.mapper.AccountFreezeRecordMapper;
import com.example.mapper.AccountTblMapper;
import com.example.service.IAccountService;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Slf4j
public class AccountServiceImpl implements IAccountService {

    @Resource
    private AccountTblMapper accountMapper;

    @Resource
    private AccountFreezeRecordMapper freezeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean tryDeduct(String userId,
                             int amount) {

        log.info("账户扣减...开始 xid:{} userId={} amount={}", RootContext.getXID(), userId, amount);
        AccountTbl accountTbl = accountMapper.selectByUserId(userId);
        if (accountTbl == null) {
            throw new RuntimeException("未找到用户");
        }
        int rows = accountMapper.deductBalance(userId, amount); // 扣钱
        if (rows == 0) {
            return false;
        }
        log.info("账户扣减...成功 xid:{} userId={} amount={}", RootContext.getXID(), userId, amount);
        AccountFreezeRecord accountFreezeRecord = new AccountFreezeRecord();
        accountFreezeRecord.setXid(RootContext.getXID());
        accountFreezeRecord.setUserId(userId);
        accountFreezeRecord.setBalance(accountTbl.getBalance());
        accountFreezeRecord.setFreezeAmount(amount);
        accountFreezeRecord.setState(XState.TRY.getState());
        int insert = freezeMapper.insert(accountFreezeRecord); // 冻结
        return insert > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean confirm(BusinessActionContext context) {
        String xid = context.getXid();
        log.info("账户服务...confirm进入 xid={}", xid);
        AccountFreezeRecord accountFreezeRecord = freezeMapper.selectByPrimaryKey(xid);
        if (accountFreezeRecord == null) {
            return true;
        }
        if (accountFreezeRecord.getState() == XState.COMMIT.getState()) {
            return true;
        }
        accountFreezeRecord.setState(XState.COMMIT.getState());
        freezeMapper.updateByPrimaryKey(accountFreezeRecord);
        log.info("账户服务...confirm完成 xid={}", xid);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancel(BusinessActionContext actionContext) {
        String xid = actionContext.getXid();
        log.info("账户服务...cancel进入 xid={}", xid);
        AccountFreezeRecord freezeRecord = freezeMapper.selectByPrimaryKey(xid);
        if (freezeRecord == null) {
            return true;
        }
        if (freezeRecord.getState() == XState.CANCEL.getState()) {
            return true;
        }
        // 恢复金额
        accountMapper.unfreezeAmount(freezeRecord.getUserId(), freezeRecord.getFreezeAmount());
        // 冻结金额清零
        freezeRecord.setState(XState.CANCEL.getState());
        freezeRecord.setFreezeAmount(0);
        freezeMapper.updateByPrimaryKey(freezeRecord);
        log.info("账户服务...cancel完成 xid={}", xid);
        return true;
    }

}