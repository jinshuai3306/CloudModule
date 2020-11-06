package com.qfedu.provider.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * projectName: CloudModule
 *
 * @author: 靳帅
 * time: 2020/11/4 18:32
 * description:
 */
@Configuration
@RefreshScope
public class RabbitMQ {
    /**
     *购物车队列名称
     */
    public static final String JS_Cart = "js_cart";

    //实现下单数据的同步 Redis->Mysql
    public static final String ordersync="js_order-sync";
    //实现延迟队列-订单超时  24小时
    public static final String ordertime="js_order-time";

    public static final String ordertimeout="js-order-timeout";

//    @Value("${order.timeout}")
//    private long otimeout;

    //创建队列
    @Bean
    public Queue createQ1(){
        return new Queue(JS_Cart);
    }

    @Bean
    public Queue createQ2(){
        return new Queue(ordersync);
    }
    //延时
    @Bean
    public Queue createQ3(){
        Map<String,Object> params=new LinkedHashMap<>();
        //延时时间
        params.put("x-messgae-ttl",10);
        //死信交换器 对应
        params.put("x-dead-letter-exchange","ex-order-ttl");
        //
        params.put("x-dead-letter-routing-key","order-timeout");
        return QueueBuilder.durable(ordertime).withArguments(params).build();
    }
    //死信
    @Bean
    public Queue createQ4(){
        return new Queue(ordertimeout);
    }
    //交换器
    @Bean
    public FanoutExchange createFex(){
        return new FanoutExchange("ex-order");
    }
    @Bean
    public DirectExchange createDex(){
        return new DirectExchange("ex-order-ttl");
    }
    //绑定

    @Bean
    public Binding createBdMysql(FanoutExchange fe){
        return BindingBuilder.bind(createQ2()).to(fe);
    }
    @Bean
    public Binding createBdTtl(FanoutExchange fe){
        return BindingBuilder.bind(createQ3()).to(fe);
    }
    @Bean
    public Binding createBdDlx(DirectExchange de){
        return BindingBuilder.bind(createQ4()).to(de).with("order-timeout");
    }


}
