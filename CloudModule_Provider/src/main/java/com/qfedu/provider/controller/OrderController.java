package com.qfedu.provider.controller;


import com.qfedu.common.dto.OrderItemDto;
import com.qfedu.common.vo.R;
import com.qfedu.provider.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * projectName: CloudModule
 *
 * @author: 靳帅
 * time: 2020/11/5 11:08
 * description: 订单
 */
@RestController
@RequestMapping("/order/")
public class OrderController {
    @Autowired
    private OrderService service;
    //

}
