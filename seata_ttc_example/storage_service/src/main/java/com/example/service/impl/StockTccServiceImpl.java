package com.example.service.impl;

import com.example.XState;
import com.example.entity.StockFreeze;
import com.example.mapper.StockFreezeMapper;
import com.example.mapper.StockTblMapper;
import com.example.service.StockTccService;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.LocalTCC;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
@LocalTCC  // 标识为本地TCC服务
public class StockTccServiceImpl implements StockTccService {

    @Autowired
    private StockTblMapper stockMapper;

    @Resource
    private StockFreezeMapper stockFreezeMapper;


    /**
     * Try阶段：检查库存并冻结
     */
    @Override
//    @GlobalTransactional
    public boolean tryReduce(BusinessActionContext context, String commodityCode, Integer count) {
        int rows = stockMapper.freezeStock(commodityCode, count);
        if (rows <= 0) {
            // 库存不足，抛出异常触发全局回滚
            throw new RuntimeException("库存不足，商品编码：" + commodityCode + "，请求数量：" + count);
        }
        StockFreeze stockFreeze = new StockFreeze();
        stockFreeze.setXid(context.getXid());
        stockFreeze.setCommodityCode(commodityCode);
        stockFreeze.setCount(count);
        stockFreeze.setFreezeCount(count);
        stockFreeze.setState(XState.TRY.getState());
        stockFreezeMapper.insert(stockFreeze);
        log.info("Stock TCC Try：冻结库存，商品编码：" + commodityCode + "，数量：" + count);
        return true;
    }

    /**
     * Confirm阶段：确认扣减（冻结库存转实际扣减）
     */
    @Override
    public boolean confirm(BusinessActionContext context) {
        String xid = context.getXid();
        log.info("库存服务...confirm进入 xid={}", xid);
        StockFreeze freeze = stockFreezeMapper.selectByPrimaryKey(xid);
        if (freeze == null) {
            return true;
        }
        if (freeze.getState() == XState.COMMIT.getState()) {
            return true;
        }
        freeze.setState(XState.COMMIT.getState());
        stockFreezeMapper.updateByPrimaryKey(freeze);
        log.info("库存服务...confirm完成 xid={}", xid);
        return true;
    }

    /**
     * Cancel阶段：取消冻结（释放库存）
     */
    @Override
    @Transactional
    public boolean cancel(BusinessActionContext context) {
        String xid = context.getXid();
        log.info("库存服务... cancel进入 xid={}", xid);
        StockFreeze record = stockFreezeMapper.selectByPrimaryKey(xid);
        if (record == null) {
            return true;
        }
        record.setState(XState.CANCEL.getState());
        record.setFreezeCount(0);

        List<StockFreeze> stockFreezes = stockFreezeMapper.selectByCommodityCode(record.getCommodityCode());
        if (CollectionUtils.isEmpty(stockFreezes)) {
            return true;
        }
        StockFreeze stockFreeze = stockFreezes.get(0);
        stockMapper.cancelFreezeStock(stockFreeze.getCommodityCode(), record.getCount(), record.getCount());// 恢复冻结的库存
        stockFreezeMapper.updateByPrimaryKey(record);  // 冻结的库存清零
        log.info("库存服务... cancel完成 xid={}", xid);
        return true;
    }
}