package com.qfedu.provider.dao;

import com.qfedu.common.dto.CartDto;
import com.qfedu.common.dto.MQCartDto;
import com.qfedu.entity.Cart;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * projectName: CloudModule
 *
 * @author: 靳帅
 * time: 2020/11/4 13:08
 * description:
 */
@Repository
public interface CartDao {
    @Select("select * from t_cart where uid = #{uid}")
    @ResultType(Cart.class)
    List<Cart> allUid(Integer uid);
    @Insert("insert into t_cart(uid,skuid,scount,jprice,ctime) values(#{uid},#{skuid},#{scount},#{jprice},now())")
    int addCartMQ(CartDto dto);

    @Update("update t_cart set scount = #{scount} where uid = #{uid} and skuid = #{skuid}")
    int updateMQ(CartDto data);
}
