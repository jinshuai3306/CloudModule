package com.qfedu.provider.service;


import com.qfedu.common.dto.OrderCartDto;
import com.qfedu.common.vo.R;

/**
 * projectName: CloudModule
 *
 * @author: 靳帅
 * time: 2020/11/5 11:06
 * description:
 */
public interface OrderService {
    //-------------------------------1版本------------------
    //下单 ----------  分为购物车和直接下单
    public R addOrderV1(OrderCartDto dto);

    //-------------------------------2版本------------------
    //下单 ----------  分为购物车和直接下单
    public R addOrderV2(OrderCartDto dto);

}
