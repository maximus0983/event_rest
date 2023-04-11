package com.epam.service.impl;

import com.epam.dto.confDto;
import com.epam.messaging.MessagingConf;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Import({confDto.class, MessagingConf.class})
@ComponentScan
@EnableJpaRepositories
@AutoConfiguration
public class ServiceImplConf {
}
