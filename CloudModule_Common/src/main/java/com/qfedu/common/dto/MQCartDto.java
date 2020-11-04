package com.qfedu.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * projectName: CloudLike
 *
 * @author: 靳帅
 * time: 2020/11/3 14:40
 * description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MQCartDto implements Serializable {
    private long id;//唯一
    private int type;//类型
    private Object data;//消息类型

}
