package com.geektime.springbucks.service;

import com.geektime.springbucks.mapper.CoffeeOrderMapper;
import com.geektime.springbucks.model.Coffee;
import com.geektime.springbucks.model.CoffeeOrder;
import com.geektime.springbucks.model.OrderState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author colin
 */
@Slf4j
@Service
@Transactional
public class CoffeeOrderService {

    @Autowired
    private CoffeeOrderMapper coffeeOrderMapper;


    public CoffeeOrder createOrder(String customer, Coffee... coffees) {
        final CoffeeOrder order = CoffeeOrder.builder().customer(customer)
                .coffees(new ArrayList<>(Arrays.asList(coffees)))
                .state(OrderState.INIT)
                .build();
        coffeeOrderMapper.save(order);
        final CoffeeOrder saved = coffeeOrderMapper.findone(customer);

        log.info("New Order: {}", saved);

        return saved;
    }

    public boolean updateState(CoffeeOrder order, OrderState state) {
        if (state.compareTo(order.getState()) <= 0) {
            log.warn("Wrong State order: {}, {}", state, order.getState());
            return false;
        }

        order.setState(state);

        coffeeOrderMapper.updateState(order);
        log.info("Updated Order: {}", order);

        return true;
    }
}
