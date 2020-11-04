package com.qfedu.provider.service.impl;

import com.qfedu.provider.dao.UserDao;
import com.qfedu.provider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * projectName: CloudModule
 *
 * @author: 靳帅
 * time: 2020/11/4 11:19
 * description:
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

}
