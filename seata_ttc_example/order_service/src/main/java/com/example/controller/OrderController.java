package com.example.controller;


import com.example.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/create", method = RequestMethod.GET, produces = "application/json")
    public String create(@RequestParam("userId") String userId,
                         @RequestParam("commodityCode") String commodityCode,
                         @RequestParam("orderCount") int orderCount) {
        try {
            orderService.create(userId, commodityCode, orderCount);
        } catch (Exception ex) {
            throw ex;
        }
        return "SUCCESS";
    }
}