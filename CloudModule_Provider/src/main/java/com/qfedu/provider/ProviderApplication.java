package com.qfedu.provider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * projectName: cloud2004
 *
 * @author: 靳帅
 * time: 2020/10/29 15:56
 * description:
 */
@SpringBootApplication //SpringBoot启动标记位
@EnableDiscoveryClient//服务器的注册和发现
@MapperScan("com.qfedu.provider.dao")
public class ProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class,args);
    }

}
