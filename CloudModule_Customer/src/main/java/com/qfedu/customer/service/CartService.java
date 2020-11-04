package com.qfedu.customer.service;

import com.qfedu.common.dto.CartCountDto;
import com.qfedu.common.dto.CartDto;
import com.qfedu.common.vo.R;

/**
 * projectName: CloudModule
 *
 * @author: 靳帅
 * time: 2020/11/4 19:29
 * description:
 */
public interface CartService {

    R addMQ(CartDto dto);

    R fortify(CartCountDto dto);

    R cut(CartCountDto dto);
}
