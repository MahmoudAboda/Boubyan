/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */

package com.oschool.student.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import redis.clients.jedis.Jedis;

@Configuration
public class RedissonConfig {

    @Value("${redis.db.host}")
    private String redisHost;

    @Value("${redis.db.port}")
    private String redisPort;

    @Bean
    public Config config(){
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + redisHost + ":" + redisPort);
        return config;
    }
    @Bean
    @Scope("prototype")
    public Jedis jedis() {
        Jedis jedis = new Jedis(redisHost, Integer.parseInt(redisPort));
       // jedis.auth(redisPassword);
        return jedis;
    }

    @Bean
    public RedissonClient redissonClient(){
        return Redisson.create(config());
    }
}
