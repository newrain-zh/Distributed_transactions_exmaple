package com.example.controller;

import com.example.service.StockTccService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {

    @Autowired
    private StockTccService stockTccService;

    /**
     * 扣减库存接口（全局事务入口）
     * @GlobalTransactional：标记为Seata全局事务
     */
    @PostMapping("/reduce")
    public String reduceStock(
            @RequestParam("commodityCode") String commodityCode,
            @RequestParam("count") Integer count) {

        // 调用TCC的Try阶段
        boolean tryResult = stockTccService.tryReduce(null, commodityCode, count);
        if (!tryResult) {
            return "库存扣减失败";
        }
        // 此处可调用其他微服务（如订单服务、支付服务）
        // 如果所有服务Try成功，Seata会自动触发Confirm；任何服务失败，触发Cancel
        return "库存扣减成功（全局事务已提交）";
    }
}