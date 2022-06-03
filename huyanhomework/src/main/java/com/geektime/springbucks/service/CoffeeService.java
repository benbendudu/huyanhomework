package com.geektime.springbucks.service;

import com.geektime.springbucks.mapper.CoffeeMapper;
import com.geektime.springbucks.model.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * @author colin
 */
@Slf4j
@Service
public class CoffeeService {
    @Autowired
    private CoffeeMapper coffeeMapper;

    public Optional<Coffee> findOneCoffee(String name) {

        final Optional<Coffee> coffee = Optional.ofNullable(coffeeMapper.findByName(name));

        log.info("Coffee Found: {}", coffee);
        return coffee;
    }
}
