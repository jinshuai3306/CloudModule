package com.qfedu.provider.listener;

import com.qfedu.entity.Order;
import com.qfedu.entity.OrderItem;
import com.qfedu.provider.config.RabbitMQ;
import com.qfedu.provider.config.RedisKeyConfig;
import com.qfedu.provider.dao.OrderDao;
import com.qfedu.provider.dao.OrderItemDao;
import com.qfedu.provider.third.RedissonUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * projectName: CloudModule
 *
 * @author: 靳帅
 * time: 2020/11/5 23:27
 * description:
 */
@Component
@RabbitListener(queues = RabbitMQ.ordersync)
public class OrderSyncListener {
    @Autowired
    private OrderDao dao;
    @Autowired
    private OrderItemDao itemDao;

    @RabbitHandler
    @Transactional
    public void handler(long id){
        String k1= RedisKeyConfig.ORDER_INFO+id;
        String k2=RedisKeyConfig.ORDER_ITEM_INFO+id;
        if(RedissonUtil.checkKey(k1,k2)){
            Order order = (Order) RedissonUtil.getStrObj(k1);
            dao.insert(order);
            Collection<Object> items = RedissonUtil.getHash(k2);
            itemDao.insert(items);
        }
    }

}
