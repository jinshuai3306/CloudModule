package com.qfedu.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * projectName: CloudModule
 *
 * @author: 靳帅
 * time: 2020/11/4 20:47
 * description:
 */
@Data
@NoArgsConstructor
public class CartCountDto implements Serializable {
    private Integer uid;
    private Integer skuid;
    private Integer scount; //增或减

}
