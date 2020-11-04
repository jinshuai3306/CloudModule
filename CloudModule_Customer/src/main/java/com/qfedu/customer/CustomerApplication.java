package com.qfedu.customer;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;

/**
 * projectName: cloud2004
 *
 * @author: 靳帅
 * time: 2020/10/29 15:56
 * description:
 */
@SpringBootApplication //SpringBoot启动标记位
@EnableDiscoveryClient//服务器的注册和发现
@RibbonClients //启用Ribbon 实现服务的调用和负载均衡策略
public class CustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class,args);
    }

}
