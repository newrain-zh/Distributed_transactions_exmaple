package com.example.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("stock-service")
public interface StockClient {

    @GetMapping("/stock/minus")
    String minus(@RequestParam("gid") String gid, @RequestParam("count") Integer count);
}