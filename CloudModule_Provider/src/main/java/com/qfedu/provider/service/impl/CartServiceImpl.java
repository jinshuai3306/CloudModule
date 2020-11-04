package com.qfedu.provider.service.impl;

import com.qfedu.common.config.RabbitMQTypeConfig;
import com.qfedu.common.dto.CartCountDto;
import com.qfedu.common.dto.CartDto;
import com.qfedu.common.dto.MQCartDto;
import com.qfedu.common.util.IdGeneratorSinglon;
import com.qfedu.common.vo.R;
import com.qfedu.entity.Cart;
import com.qfedu.provider.config.RabbitMQ;
import com.qfedu.provider.config.RedisKeyConfig;
import com.qfedu.provider.dao.CartDao;
import com.qfedu.provider.service.CartService;
import com.qfedu.provider.third.RedissonUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * projectName: CloudModule
 *
 * @author: 靳帅
 * time: 2020/11/4 13:10
 * description:
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartDao cartDao;
    @Autowired
    private RabbitTemplate template;
    //添加购物车
    @Override
    public R addCart(CartDto dto) {
        if (dto==null || dto.getUid()<=0 || dto.getJprice() <= 0 || dto.getScount() <= 0 || dto.getSkuid() <= 0){
            return R.fail("非法数据格式");
        }

        MQCartDto mqCartDto = new MQCartDto();
        String key = RedisKeyConfig.RK_CART_UID+dto.getUid();
        //判断redis是否有缓存：true：有缓冲，false：没有缓存，没有添加过购物车或者redis过期
        if (RedissonUtil.checkKey(key)){
            //有缓存
            //判断商品是否加入过购物车
            if (RedissonUtil.checkField(key,dto.getSkuid()+"")){
                //该商品加入过购物车----》增加购物车该商品的数量
                Cart cart = (Cart) RedissonUtil.getHash(key, dto.getSkuid() + "");
                cart.setScount(cart.getScount()+dto.getScount());
                //更新redis中的数据
                RedissonUtil.setHash(key,dto.getSkuid()+"",cart);
                //通知MQ队列----------同步数据库---------------
                mqCartDto.setType(RabbitMQTypeConfig.MQ_CART_UPDATE);
                dto.setScount(cart.getScount());
            }else {
                //该商品没有加入过购物车--->添加新的
                Cart cart = new Cart(dto.getUid(),dto.getSkuid(),dto.getScount(),dto.getJprice());
                //存入redis
                RedissonUtil.setHash(key,dto.getSkuid()+"",cart);
                //通知队列--------------同步数据库---------------
                mqCartDto.setType(RabbitMQTypeConfig.MQ_CART_ADD);

            }
        }else {
            List<Cart> list = cartDao.allUid(dto.getUid());

            System.out.println(list.size());
            //判断没有添加过购物车或者redis过期
            if (list == null || list.size() <= 0){

                //该用户的=购物车是空的，第一次添加购物车
                Cart cart = new Cart(dto.getUid(),dto.getSkuid(),dto.getScount(),dto.getJprice());
                //存入redis
                System.out.println("cart = " + cart);
                RedissonUtil.setHash(key,dto.getSkuid()+"",cart);

                //通知队列--------------同步数据库---------------
                mqCartDto.setType(RabbitMQTypeConfig.MQ_CART_ADD);

            }else {

                //redis过期
                Map<String,Object> map = new LinkedHashMap<>();
                boolean r = true;
                for (Cart cart : list) {
                    if (cart.getSkuid() == dto.getSkuid()){
                        r = false; //表示该商品存入过购物车了---修改数量
                        cart.setScount(cart.getScount()+dto.getScount());//
                        mqCartDto.setType(RabbitMQTypeConfig.MQ_CART_UPDATE);
                        dto.setScount(cart.getScount());
                    }
                    map.put(cart.getSkuid()+"",cart);
                }

                if (r){
                    //该商品没有加入过购物车--->添加新的
                    Cart cart = new Cart(dto.getUid(),dto.getSkuid(),dto.getScount(),dto.getJprice());
                    map.put(cart.getSkuid()+"",cart);
                    //通知队列--------------同步数据库---------------
                    mqCartDto.setType(RabbitMQTypeConfig.MQ_CART_ADD);
                }
                //更新
                RedissonUtil.setHashAll(key,map);

            }
            //设置有效期
            RedissonUtil.setTime(key,RedisKeyConfig.RK_CART_TIME, TimeUnit.HOURS);
        }
        mqCartDto.setId(IdGeneratorSinglon.getInstance().generator.nextId());
        mqCartDto.setData(dto);

        template.convertAndSend("", RabbitMQ.JS_Cart,mqCartDto);
        return R.succeed();
    }
    //增加按钮
    @Override
    public R fortify(CartCountDto dto) {
        if (dto == null || dto.getScount() <= 0 || dto.getSkuid()<= 0 || dto.getUid() <= 0){
            return R.fail("非法数据格式");
        }
        MQCartDto mqCartDto = new MQCartDto();
        String key = RedisKeyConfig.RK_CART_UID+dto.getUid();

        if (RedissonUtil.checkKey(key)){
             if (RedissonUtil.checkField(key,dto.getSkuid()+"")){
                 Cart cart = (Cart) RedissonUtil.getHash(key, dto.getSkuid() + "");
                 cart.setScount(cart.getScount()+dto.getScount());
                 RedissonUtil.setHash(key,dto.getScount()+"",cart);
                 mqCartDto.setType(RabbitMQTypeConfig.MQ_CART_UPDATE);
                 mqCartDto.setId(IdGeneratorSinglon.getInstance().generator.nextId());
                 CartDto cartDto = new CartDto(cart.getUid(),cart.getSkuid(),cart.getScount(),cart.getJprice());
                 mqCartDto.setData(cartDto);
                 template.convertAndSend("",RabbitMQ.JS_Cart,mqCartDto);
                 return R.succeed();
             }
        }
        return R.fail();
    }

    @Override
    public R cut(CartCountDto dto) {
        if (dto == null || dto.getScount() <= 0 || dto.getSkuid()<= 0 || dto.getUid() <= 0){
            return R.fail("非法数据格式");
        }
        MQCartDto mqCartDto = new MQCartDto();
        String key = RedisKeyConfig.RK_CART_UID+dto.getUid();

        if (RedissonUtil.checkKey(key)){
            if (RedissonUtil.checkField(key,dto.getSkuid()+"")){
                Cart cart = (Cart) RedissonUtil.getHash(key, dto.getSkuid() + "");
                if (cart.getScount()==0 || cart.getScount() < dto.getScount()){
                    return R.fail();
                }
                cart.setScount(cart.getScount()-dto.getScount());
                RedissonUtil.setHash(key,dto.getScount()+"",cart);
                mqCartDto.setType(RabbitMQTypeConfig.MQ_CART_UPDATE);
                mqCartDto.setId(IdGeneratorSinglon.getInstance().generator.nextId());
                CartDto cartDto = new CartDto(cart.getUid(),cart.getSkuid(),cart.getScount(),cart.getJprice());
                mqCartDto.setData(cartDto);
                template.convertAndSend("",RabbitMQ.JS_Cart,mqCartDto);
                return R.succeed();
            }
        }
        return R.fail();
    }
}
