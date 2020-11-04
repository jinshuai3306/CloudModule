package com.qfedu.customer.controller;

import com.qfedu.common.vo.R;
import com.qfedu.customer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * projectName: CloudModule
 *
 * @author: 靳帅
 * time: 2020/11/4 10:59
 * description:
 */
@RestController
@RequestMapping("/api/")
public class UserController {
    @Autowired
    private UserService  userService;

    @GetMapping("test1.do")
    public R test1(){
        return userService.test1();
    }


}
