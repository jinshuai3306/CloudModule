package com.qfedu.provider.controller;

import com.qfedu.common.dto.CartCountDto;
import com.qfedu.common.dto.CartDto;
import com.qfedu.common.vo.R;
import com.qfedu.entity.Cart;
import com.qfedu.provider.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

/**
 * projectName: CloudModule
 *
 * @author: 靳帅
 * time: 2020/11/4 13:07
 * description:
 */
@RestController
@RequestMapping("/cart/")
public class CartController {
    @Autowired
    private CartService cartService;
    //添加购物车
    @PostMapping("addCart.do")
    public R addCart(@RequestBody CartDto dto){

        return cartService.addCart(dto);
    }
    //购物车点击添加按钮
    @PostMapping("fortify.do")
    public R fortify(@RequestBody CartCountDto dto){
        return cartService.fortify(dto);
    }
    //购物车点击添加按钮
    @PostMapping("cut.do")
    public R cut(@RequestBody CartCountDto dto){
        return cartService.cut(dto);
    }
}
