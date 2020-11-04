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

}
