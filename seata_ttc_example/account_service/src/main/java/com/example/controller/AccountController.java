package com.example.controller;


import com.example.service.IAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AccountController {

    @Resource
    private IAccountService accountService;

    @GetMapping("/debit")
    public void debit(@RequestParam("userId") String userId,
                      @RequestParam("amount") int amount) {
        accountService.tryDeduct(userId, amount);
    }
}