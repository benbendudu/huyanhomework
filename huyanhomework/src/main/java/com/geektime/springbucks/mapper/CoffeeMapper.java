package com.geektime.springbucks.mapper;

import com.geektime.springbucks.model.Coffee;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.List;

/**
 * @author colin
 */
@Mapper
public interface CoffeeMapper {
    @Insert("insert into t_coffees(name, price, create_time, update_time) " +
            "values(#{name}, #{price}, now(), now())")
    @Options(useGeneratedKeys = true)
    int save(Coffee coffee);

//    @Select("select * from t_coffees where id = #{id}")
//    @Results({
//            @Result(id=true, column = "id", property = "id"),
//            @Result(column = "create_time", property="createTime"),
//            // map-underscore-to-camel-case = true 可以实现一样的效果
//            // @Result(column = "update_time", property="updateTime")
//    })
//    Coffee findById(@Param("id") Long id);

    @Select("select * from t_coffees where name = #{name}")
    Coffee findByName(@Param("name") String name);

    @Select("select * from t_coffees")
    ArrayList<Coffee> find();

    @Select("select * from t_coffees order by id")
    List<Coffee> findAllWithRowBounds(RowBounds rowBounds);

    @Select("select * from t_coffees order by id")
    List<Coffee> findAllWithParam(@Param("pageNum") int pageNum,
                                  @Param("pageSize") int pageSize);
}
