package com.qfedu.customer.service.impl;

import com.qfedu.common.dto.CartCountDto;
import com.qfedu.common.dto.CartDto;
import com.qfedu.common.vo.R;
import com.qfedu.customer.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * projectName: CloudModule
 *
 * @author: 靳帅
 * time: 2020/11/4 19:29
 * description:
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private RestTemplate template;

    @Override
    public R addMQ(CartDto dto) {
        //转换格式
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType("application/json;UTF-8"));
        HttpEntity<CartDto> entity=new HttpEntity<>(dto,httpHeaders);

        return template.postForObject("http://ModuleProvider/cart/addCart.do",entity,R.class);
    }

    @Override
    public R fortify(CartCountDto dto) {
        //转换格式
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType("application/json;UTF-8"));
        HttpEntity<CartCountDto> entity=new HttpEntity<>(dto,httpHeaders);

        return template.postForObject("http://ModuleProvider/cart/fortify.do",entity,R.class);
    }

    @Override
    public R cut(CartCountDto dto) {
        //转换格式
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType("application/json;UTF-8"));
        HttpEntity<CartCountDto> entity=new HttpEntity<>(dto,httpHeaders);

        return template.postForObject("http://ModuleProvider/cart/cut.do",entity,R.class);
    }
}
