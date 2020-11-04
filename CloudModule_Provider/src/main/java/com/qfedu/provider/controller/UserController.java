package com.qfedu.provider.controller;

import com.qfedu.common.vo.R;
import com.qfedu.provider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * projectName: CloudModule
 *
 * @author: 靳帅
 * time: 2020/11/4 11:20
 * description:
 */
@RestController
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("test1.do")
    public R test1(){
        return R.succeed("你好世界");
    }

}
