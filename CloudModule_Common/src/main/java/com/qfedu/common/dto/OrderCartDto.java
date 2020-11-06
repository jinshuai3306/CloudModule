package com.qfedu.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * projectName: CloudModule
 *
 * @author: 靳帅
 * time: 2020/11/5 11:09
 * description:
 */
@Data
@NoArgsConstructor
public class OrderCartDto {
    private Integer uid;//用户id
    private Integer aid; //收货地址id
    private Integer type;//支付方式---1支付宝，2微信
    private int mark;//标记位 1.立即购买 2.购物车
    private List<OrderItemDto> list;
    private String skuids;//商品id拼接的字符串


}
