package com.qfedu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * projectName: CloudModule
 *
 * @author: 靳帅
 * time: 2020/11/4 15:28
 * description:
 */
@Data
@NoArgsConstructor
public class Cart implements Serializable {
    private Integer id;
    private Integer uid;//用户id
    private Integer skuid;//sku商品属性id
    private Integer scount;//数量
    private Integer jprice;//价格
    private Integer ctime;//时间

    public Cart(Integer uid, Integer skuid, Integer scount, Integer jprice) {
        this.uid = uid;
        this.skuid = skuid;
        this.scount = scount;
        this.jprice = jprice;
    }
}
