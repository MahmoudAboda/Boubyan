/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */

package com.oschool.student.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.time.Clock;
import java.time.ZoneId;

@Configuration
@PropertySources({
        @PropertySource("classpath:ValidationMessages.properties"),
        @PropertySource("classpath:ValidationMessages_ar.properties")
})
public class ApplicationPropertySourcesConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public Clock clock(){
        return Clock.system(ZoneId.systemDefault());
    }
}
