package com.qfedu.customer.controller;

import com.qfedu.common.dto.CartCountDto;
import com.qfedu.common.dto.CartDto;
import com.qfedu.common.vo.R;
import com.qfedu.customer.service.CartService;
import com.qfedu.customer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * projectName: CloudModule
 *
 * @author: 靳帅
 * time: 2020/11/4 10:59
 * description: 订单服务
 */
@RestController
@RequestMapping("/api/cart/")
public class CartController {

    @Autowired
    private CartService service;

    @PostMapping("addMQ.do")
    public R addMQ(@RequestBody CartDto dto){
        return service.addMQ(dto);
    }

    @PostMapping("fortify.do")
    public R fortify(@RequestBody CartCountDto dto){
        return service.fortify(dto);
    }

    @PostMapping("cut.do")
    public R cut(@RequestBody CartCountDto dto){
        return service.cut(dto);
    }



}
