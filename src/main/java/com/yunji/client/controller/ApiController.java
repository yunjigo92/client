package com.yunji.client.controller;

import com.yunji.client.dto.Req;
import com.yunji.client.dto.User;
import com.yunji.client.service.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client")
public class ApiController {


    private final RestTemplateService restTemplateService;

    public ApiController(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }


    @GetMapping("/hello")
    public User getHello(){
        return restTemplateService.hello();
    }


    @GetMapping("/post")
    public User post(){
        return restTemplateService.post();
    }

    @GetMapping("/exchange")
    public User exchange(){
        return restTemplateService.exchange();
    }

    @GetMapping("/generic")
    public Req<User> genericExchange(){
        return restTemplateService.genericExchange();
    }


}
