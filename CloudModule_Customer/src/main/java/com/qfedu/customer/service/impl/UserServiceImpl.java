package com.qfedu.customer.service.impl;

import com.qfedu.common.vo.R;
import com.qfedu.customer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * projectName: CloudModule
 *
 * @author: 靳帅
 * time: 2020/11/4 11:02
 * description:
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RestTemplate restTemplate;

    public R test1(){
        return restTemplate.getForObject("http://ModuleProvider/user/test1.do",R.class);

    }

}
