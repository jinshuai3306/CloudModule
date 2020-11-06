package com.qfedu.provider.listener;

import com.qfedu.entity.Order;
import com.qfedu.provider.config.OrderFlagCongig;
import com.qfedu.provider.config.RabbitMQ;
import com.qfedu.provider.config.RedisKeyConfig;
import com.qfedu.provider.dao.OrderDao;
import com.qfedu.provider.third.RedissonUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.RabbitUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * projectName: CloudModule
 *
 * @author: 靳帅
 * time: 2020/11/5 23:39
 * description:
 */
@Component
@RabbitListener(queues = RabbitMQ.ordertimeout)
public class OrderTimoutListener {
    @Autowired
    private OrderDao dao;

    @RabbitHandler
    @Transactional
    public void handler(long id){
        Order order=dao.queryById(id);
        if(order!=null){
            Collection<Object> collection = RedissonUtil.getHash(RedisKeyConfig.ORDER_ITEM_INFO + order.getId());
            for (Object o : collection) {

            }
            //订单超时
            //更改订单状态
            dao.updateFlag(id, OrderFlagCongig.取消订单);
            //释放库存
            //释放积分抵扣
            //释放优惠卷
            //释放满减

        }
    }


}
