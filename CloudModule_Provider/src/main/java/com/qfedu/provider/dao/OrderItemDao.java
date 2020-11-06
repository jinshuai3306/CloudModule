package com.qfedu.provider.dao;

import com.qfedu.entity.OrderItem;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * projectName: CloudModule
 *
 * @author: 靳帅
 * time: 2020/11/5 19:02
 * description:
 */
@Repository
public interface OrderItemDao {


    int[] insert(Collection<Object> list);
}
