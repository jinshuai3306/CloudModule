package com.qfedu.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * projectName: CloudModule
 *
 * @author: 靳帅
 * time: 2020/11/5 11:09
 * description: 订单详情表
 */
@Data
@NoArgsConstructor
public class OrderItem {
    private Integer id;
    private long oid;//订单id
    private Integer skuid; //商品属性id
    private Integer scount;//商品数量
    private Double price;//商品价格
    private Date ctime;//订单成时间

    public OrderItem(Integer oid, Integer skuid, Integer scount, Double price) {
        this.oid = oid;
        this.skuid = skuid;
        this.scount = scount;
        this.price = price;
    }
}
