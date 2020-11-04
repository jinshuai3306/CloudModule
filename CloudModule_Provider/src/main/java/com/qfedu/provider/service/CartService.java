package com.qfedu.provider.service;

import com.qfedu.common.dto.CartCountDto;
import com.qfedu.common.dto.CartDto;
import com.qfedu.common.vo.R;

/**
 * projectName: CloudModule
 *
 * @author: 靳帅
 * time: 2020/11/4 13:09
 * description:
 */
public interface CartService {

    R addCart(CartDto dto);

    R fortify(CartCountDto dto);

    R cut(CartCountDto dto);

}
