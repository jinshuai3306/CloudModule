package com.qfedu.provider.listener;

import com.qfedu.common.config.RabbitMQTypeConfig;
import com.qfedu.common.dto.CartDto;
import com.qfedu.common.dto.MQCartDto;
import com.qfedu.entity.Cart;
import com.qfedu.provider.config.RabbitMQ;
import com.qfedu.provider.dao.CartDao;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * projectName: CloudModule
 *
 * @author: 靳帅
 * time: 2020/11/4 19:04
 * description:监听
 */
@Component
@RabbitListener(queues = RabbitMQ.JS_Cart)
public class CartMQListener {
    @Autowired
    private CartDao dao;

    @RabbitHandler
    public void hanlder(MQCartDto dto){
        System.out.println(dto);
        if (dto.getType() == RabbitMQTypeConfig.MQ_CART_ADD || dto.getType() == RabbitMQTypeConfig.MQ_CART_UPDATE){
            //验证消息类型
            switch (dto.getType()){
                case RabbitMQTypeConfig.MQ_CART_ADD:
                    //添加
                    dao.addCartMQ((CartDto)dto.getData());
                    break;
                case RabbitMQTypeConfig.MQ_CART_UPDATE:
                    //修改
                    dao.updateMQ((CartDto)dto.getData());
                    break;
            }
        }
    }
}
