package com.example.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "account-service",url = "http://localhost:8085")
public interface AccountClient {

    @GetMapping("/debit")
    String debit(@RequestParam("userId") String uid, @RequestParam("amount") Integer money);
}