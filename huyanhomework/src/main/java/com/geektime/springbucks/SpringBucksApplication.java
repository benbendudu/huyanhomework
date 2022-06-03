package com.geektime.springbucks;

import com.geektime.springbucks.mapper.CoffeeMapper;
import com.geektime.springbucks.model.Coffee;
import com.geektime.springbucks.model.CoffeeOrder;
import com.geektime.springbucks.model.OrderState;
import com.geektime.springbucks.service.CoffeeOrderService;
import com.geektime.springbucks.service.CoffeeService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author colin
 */
@Slf4j
@EnableTransactionManagement
@SpringBootApplication
@MapperScan("com.geektime.springbucks.mapper")
public class SpringBucksApplication implements ApplicationRunner {

    @Autowired
    private CoffeeMapper coffeeMapper;

    @Autowired
    private CoffeeOrderService orderService;

    @Autowired
    private CoffeeService coffeeService;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;


    public static void main(String[] args) {
        SpringApplication.run(SpringBucksApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("All Coffee: {}", coffeeMapper.find().toString());

        final Optional<Coffee> latte = coffeeService.findOneCoffee("latte");
        System.out.println("latte = " + latte);

        if (latte.isPresent()) {
            final CoffeeOrder order = orderService.createOrder("colin", latte.get());
            log.info("Update INIT TO PAID: {}", orderService.updateState(order, OrderState.PAID));
            log.info("Update PAID TO INIT: {}", orderService.updateState(order, OrderState.INIT));
        }

//        分页测试
        pageTest();

//        Redis测试
        redisTest();

    }

    public void pageTest(){

        coffeeMapper.findAllWithRowBounds(new RowBounds(0, 3))
                .forEach(c -> log.info("Page(1) Coffee {}", c));
        coffeeMapper.findAllWithRowBounds(new RowBounds(3, 3))
                .forEach(c -> log.info("Page(2) Coffee {}", c));

        log.info("===================");

        coffeeMapper.findAllWithRowBounds(new RowBounds(1, 0))
                .forEach(c -> log.info("Page(1) Coffee {}", c));

        log.info("===================");

//    这种方法不好用了
//        coffeeMapper.findAllWithParam(1, 3)
//                .forEach(c -> log.info("Page(1) Coffee {}", c));
//        List<Coffee> list = coffeeMapper.findAllWithParam(2, 3);
//        PageInfo page = new PageInfo(list);
//        log.info("PageInfo: {}", page);
    }

    public void redisTest(){
        JedisPoolConfig  jedisPoolConfig= new JedisPoolConfig();
        log.info("PoolConfig - : {}" , jedisPoolConfig.toString());


        JedisPool jedisPool = new JedisPool(host,port);

        try (Jedis jedis = jedisPool.getResource()) {
            coffeeMapper.find().forEach(c -> {
                jedis.hset("springbucks-menu",
                        c.getName(),
                        Double.toString(c.getPrice()));
            });

            Map<String, String> menu = jedis.hgetAll("springbucks-menu");
            log.info("Menu: {}", menu);

            String price = jedis.hget("springbucks-menu", "espresso");
            log.info("espresso - {}",
                    Double.parseDouble(price));
        }

    }

}
