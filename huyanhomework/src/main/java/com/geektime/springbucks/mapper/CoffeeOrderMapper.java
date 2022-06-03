package com.geektime.springbucks.mapper;

import com.geektime.springbucks.model.Coffee;
import com.geektime.springbucks.model.CoffeeOrder;
import org.apache.ibatis.annotations.*;

/**
 * @author colin
 */
@Mapper
public interface CoffeeOrderMapper {
    @Insert("insert into t_orders(id, create_time, update_time,customer,state) " +
            "values(#{id}, now(), now(),#{customer},#{state})")
//    @Options(useGeneratedKeys = true)
    int save(CoffeeOrder coffeeOrder);


    @Update("update t_orders set state = #{state}")
    int updateState(CoffeeOrder coffeeOrder);

//    @Select("select * from t_coffees where id = #{id}")
//    @Results({
//            @Result(id=true, column = "id", property = "id"),
//            @Result(column = "create_time", property="createTime"),
//            // map-underscore-to-camel-case = true 可以实现一样的效果
//            // @Result(column = "update_time", property="updateTime")
//    })
//    Coffee findById(@Param("id") Long id);
//
    @Select("select * from t_orders where customer = #{customer}")
    CoffeeOrder findone(@Param("customer") String custromer);

    @Delete("delete t_orders where id=#{id}")
    void delete(CoffeeOrder coffeeOrder);
//
//    @Select("select * from t_coffees")
//    Coffee find();
}
