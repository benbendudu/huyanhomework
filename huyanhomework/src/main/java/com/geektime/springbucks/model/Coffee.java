package com.geektime.springbucks.model;

import lombok.*;
import org.hibernate.annotations.Type;
import org.joda.money.Money;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author colin
 */
@Entity
@Table(name = "t_coffees")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Coffee extends BaseEntity implements Serializable {
    //咖啡名称
    private String name;

    @Column
//    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyMinorAmount",
//            parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode", value = "CNY")})
    //咖啡价格
    private Double price;

}
