package com.qfedu.provider.dao;

import com.qfedu.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * projectName: CloudModule
 *
 * @author: 靳帅
 * time: 2020/11/5 11:06
 * description:
 */
@Repository
public interface OrderDao {
    @Insert("insert into t_order(id,uid,aid,totalmoney,reducemoney,realitymoney,ctime,utime,flag,type) " +
            "values(#{#{id},#{uid},#{aid},#{totalmoney},#{reducemoney},#{realitymoney},now(),now(),#{flag},#{type})")
    int insert(Order order);
    @Select("select * from t_order where id = #{is}")
    @ResultType(Order.class)
    Order queryById(long id);
    @Update("update t_order set flag=#{flag} where id = #{id}")
    int updateFlag(long id, Integer flag);
}
