package com.qfedu.customer.config;

import com.netflix.loadbalancer.BestAvailableRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * projectName: CloudLike
 *
 * @author: 靳帅
 * time: 2020/10/30 18:31
 * description:
 */
@Configuration
public class RibbonConfig {
    //创建请求对象
    @Bean
    @LoadBalanced//启动负载均衡
    public RestTemplate createRT(){
        return new RestTemplate();
    }
    //负载均衡的分发策略
    @Bean
    public IRule createRule(){
        //最小并发发配
        return new BestAvailableRule();
    }

}
