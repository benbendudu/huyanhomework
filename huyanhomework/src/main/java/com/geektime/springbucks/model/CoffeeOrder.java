package com.geektime.springbucks.model;

import lombok.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.boot.autoconfigure.batch.BatchProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.JDBCType;
import java.util.List;

/**
 * @author colin
 */
@Entity
@Table(name = "t_orders")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class CoffeeOrder extends BaseEntity implements Serializable {
    @Id
    private Long id;

    private String customer;

    @ManyToMany
    @JoinTable(name="t_order_coffees")
    @OrderBy("id")
    private List<Coffee> coffees;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private OrderState state;
}
