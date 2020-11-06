package com.qfedu.provider.service.impl;


import com.qfedu.common.dto.OrderCartDto;
import com.qfedu.common.dto.OrderItemDto;
import com.qfedu.common.util.IdGeneratorSinglon;
import com.qfedu.common.vo.R;
import com.qfedu.entity.Order;
import com.qfedu.entity.OrderItem;
import com.qfedu.provider.config.OrderFlagCongig;
import com.qfedu.provider.config.RedisKeyConfig;
import com.qfedu.provider.dao.OrderDao;
import com.qfedu.provider.dao.OrderItemDao;
import com.qfedu.provider.service.OrderService;
import com.qfedu.provider.third.RedissonUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * projectName: CloudModule
 *
 * @author: 靳帅
 * time: 2020/11/5 11:06
 * description:
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao itemDao;
    @Autowired
    private RabbitTemplate template;
    //-----------------------第一版本-----------------------------------
    //下单
    @Override
    @Transactional
    public R addOrderV1(OrderCartDto dto) {
        if (dto == null|| dto.getAid() <= 0 || dto.getType() <= 0  || dto.getUid() <= 0 || dto.getList() == null){
            return R.fail("数据格式错误");
        }
        List<Object> list=new ArrayList<>();

        //订单对象
        Order order = new Order();
        order.setType(dto.getType());
        Random random = new Random();
        order.setId(IdGeneratorSinglon.getInstance().generator.nextId());//通过雪花算法得到唯一id
        double toalmoney = 0;//订单总金额

        try {

            //                    String[] ids=dto.getSkuids().split("-");
//                    for(String i:ids){
//                        if(RedissonUtil.checkKeyPat(RedisKeyConfig.ORDER_LOCK+"*"+i+"*")){
//                            //存在 抢购现象
//
//                        }
//                    }

            //下单接口分布式锁，5秒默认等待
            RedissonUtil.lock(RedisKeyConfig.ORDER_LOCK + dto.getSkuids(),5);

            for (OrderItemDto d : dto.getList()) {
                OrderItem i = new OrderItem();
                i.setOid(order.getId());
                i.setSkuid(d.getSkuid());
                //通过商品skuid开始查询数据库，获取商品的最新价格和库存  ----------->查商品表
                i.setPrice(random.nextInt(100)+1.0); //假数据，没有查商品，随机添加
                Integer kucun = random.nextInt(100)+1; //没有查商品表给的假数据，
                if (d.getScount() > kucun){
                    return R.fail("库存不足");
                }else {
                    i.setScount(d.getScount());
                }
                toalmoney = toalmoney + i.getPrice()*i.getScount();
                list.add(i);
            }
            order.setTotalmoney(toalmoney);//总金额
            double zhekou = 0; //折扣价钱
            //调用优惠卷服务
            //调用满减服务
            //调用积分抵扣 (京东-京豆  淘宝-淘金币)
            //调用红包抵扣
            //调用会员价格
            //经过一系列的优惠服务------的总折扣优惠价钱
            zhekou = random.nextInt(10)+1; //没有调用服务，假的折扣数据
            order.setReducemoney(zhekou);//折扣的价钱
            order.setRealitymoney(toalmoney-zhekou);//实际交的钱
            order.setFlag(OrderFlagCongig.未支付);//订单状态
            //依次把数据添加数据库
            //添加订单信息
            orderDao.insert(order);
            //添加订单详情信息
            itemDao.insert(list);


        }finally {
            //解锁
            RedissonUtil.unlock(RedisKeyConfig.ORDER_LOCK+dto.getSkuids());
        }
        return R.succeed("订单添加成功");
    }
    //-----------------------第二版本----------------------------------------------
    //下单
    @Override
    @Transactional
    public R addOrderV2(OrderCartDto dto) {
        if (dto == null|| dto.getAid() <= 0 || dto.getType() <= 0  || dto.getUid() <= 0 || dto.getList() == null){
            return R.fail("数据格式错误");
        }

        Map<String,Object> map = new LinkedHashMap<>();
        //订单对象
        Order order = new Order();
        order.setType(dto.getType());
        Random random = new Random();
        order.setId(IdGeneratorSinglon.getInstance().generator.nextId());//通过雪花算法得到唯一id
        double toalmoney = 0;//订单总金额

        try {

            //                    String[] ids=dto.getSkuids().split("-");
//                    for(String i:ids){
//                        if(RedissonUtil.checkKeyPat(RedisKeyConfig.ORDER_LOCK+"*"+i+"*")){
//                            //存在 抢购现象
//
//                        }
//                    }

            //下单接口分布式锁，5秒默认等待
            RedissonUtil.lock(RedisKeyConfig.ORDER_LOCK + dto.getSkuids(),5);

            for (OrderItemDto d : dto.getList()) {
                OrderItem i = new OrderItem();
                i.setOid(order.getId());
                i.setSkuid(d.getSkuid());
                //通过商品skuid开始查询数据库，获取商品的最新价格和库存  ----------->查商品表
                i.setPrice(random.nextInt(100)+1.0); //假数据，没有查商品，随机添加
                Integer kucun = random.nextInt(100)+1; //没有查商品表给的假数据，
                if (d.getScount() > kucun){
                    return R.fail("库存不足");
                }else {
                    i.setScount(d.getScount());
                }
                toalmoney = toalmoney + i.getPrice()*i.getScount();
                map.put(d.getSkuid()+"",i);

            }
            order.setTotalmoney(toalmoney);//总金额
            double zhekou = 0; //折扣价钱
            //调用优惠卷服务
            //调用满减服务
            //调用积分抵扣 (京东-京豆  淘宝-淘金币)
            //调用红包抵扣
            //调用会员价格
            //经过一系列的优惠服务------的总折扣优惠价钱
            zhekou = random.nextInt(10)+1; //没有调用服务，假的折扣数据
            order.setReducemoney(zhekou);//折扣的价钱
            order.setRealitymoney(toalmoney-zhekou);//实际交的钱
            order.setFlag(OrderFlagCongig.未支付);//订单状态
            //把数据添加入redis
            //redis添加订单信息
            RedissonUtil.setStr(RedisKeyConfig.ORDER_INFO+order.getId(),order);
            //有效期 2小时
            RedissonUtil.setTime(RedisKeyConfig.ORDER_INFO+order.getId(),RedisKeyConfig.ORDER_TIME, TimeUnit.HOURS);
            //redis添加订单详情信息
            RedissonUtil.setHashAll(RedisKeyConfig.ORDER_ITEM_INFO+order.getId(),map);
            RedissonUtil.setTime(RedisKeyConfig.ORDER_ITEM_INFO+order.getId(),RedisKeyConfig.ORDER_TIME,TimeUnit.HOURS);
            //更改库存
            //调用库存服务实现库存数量的更改
            //生成系统消息
            //发送MQ消息  实现1.Mysql数据同步 2.实现延迟队列 -超时订单
            template.convertAndSend("ex-order","",order.getId());

        }finally {
            //解锁
            RedissonUtil.unlock(RedisKeyConfig.ORDER_LOCK+dto.getSkuids());
        }
        return R.succeed("订单添加成功");
    }



}
