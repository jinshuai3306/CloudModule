package com.qfedu.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * projectName: CloudModule
 *
 * @author: 靳帅
 * time: 2020/11/5 11:09
 * description:订单表
 */
@Data
@NoArgsConstructor
public class Order  implements Serializable {
    private long id;
    private Integer uid;//用户id
    private Integer aid; //收货地址id
    private Double totalmoney;//总金额
    private Double reducemoney;//优惠金额
    private Double realitymoney;//实际付款金额
    private Date ctime;//订单成时间
    private Date utime;//订单更新时间
    private Integer type; //支付方式：1支付宝，2微信
    /**
     * 1.下单
     * 2.我的订单
     * 3.订单状态变化
     *
     * 1.未付款-待支付
     * 2.已付款-待发货
     * 3.已发货-待签收
     * 4.已签收-待确认
     * 5.已确认-待评价
     * 6.已评价
     * 7.取消订单（未支付）
     * 8.订单超时
     */
    private Integer flag;//订单的状态

    public Order(Integer uid, Integer aid, Double totalmoney, Double reducemoney, Double realitymoney,Integer type) {
        this.uid = uid;
        this.aid = aid;
        this.totalmoney = totalmoney;
        this.reducemoney = reducemoney;
        this.realitymoney = realitymoney;
        this.type =  type;
    }
}
