package com.qfedu.common.config;

/**
 * projectName: CloudModule
 *
 * @author: 靳帅
 * time: 2020/11/4 17:28
 * description:
 */
public class RabbitMQTypeConfig {
    /**
     * 取消点赞
     */
    public static final int LIKE_MQ_DEL = 2;
    /**
     * 点赞
     */
    public static final int LIKE_MQ_ADD = 1;

    /**
     * 购物车添加
     */
    public static final int MQ_CART_ADD = 3;
    /**
     * 购物车修改
     */
    public static final int MQ_CART_UPDATE = 4;


}
