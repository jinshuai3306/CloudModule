package com.qfedu.provider.config;

import org.springframework.context.annotation.Configuration;

/**
 * projectName: CloudModule
 *
 * @author: 靳帅
 * time: 2020/11/4 16:12
 * description:
 */
@Configuration
public class RedisKeyConfig {
    /**
     * 购物车存入redis，key的前缀
     */
    public static final String RK_CART_UID = "rk_cart_uid:";

    /**
     * 购物车的redis的时间 小时
     */
    public static final Integer RK_CART_TIME = 24;

    /**
     *下单接口分布式锁前缀：后跟skuid商品id
     */
    public static final String  ORDER_LOCK="order:create:";//skuid


    /**
     * 存储订单信息 String类型 前缀+订单id
     */
    public static final String ORDER_INFO="order_info:";
    /**
     * 存储订单详情-Hash类型 key前缀+订单id,  field:skuid  value:订单详情对象
     */
    public static final String ORDER_ITEM_INFO="order_item_info:";
    /**
     * 订单数据 有效期2小时
     */
    public static final int ORDER_TIME=2;

}
