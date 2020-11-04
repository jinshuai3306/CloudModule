package com.qfedu.provider.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



/**
 * projectName: CloudModule
 *
 * @author: 靳帅
 * time: 2020/11/4 18:32
 * description:
 */
@Configuration
public class RabbitMQ {
    /**
     *购物车队列名称
     */
    public static final String JS_Cart = "js_cart";
    //创建队列
    @Bean
    public Queue createQ1(){
        return new Queue(JS_Cart);
    }
}
