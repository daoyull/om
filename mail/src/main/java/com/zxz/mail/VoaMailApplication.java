package com.zxz.mail;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;



/**
 * @version 1.0
 * @Author zhen
 * @Date 2021/10/3 10:58
 * @注释
 */

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class VoaMailApplication {
    public static void main(String[] args) {
        SpringApplication.run(VoaMailApplication.class, args);
    }
    @Bean
    public Queue queue(){
        return new Queue("mail.welcome");
    }
}
