package com.yunji.client.service;

import com.yunji.client.dto.Req;
import com.yunji.client.dto.User;
import com.yunji.client.dto.UserRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.ParameterizedType;
import java.net.URI;

@Service
public class RestTemplateService {

    //http://localhost/api/server/hello
    //response
    public User hello(){
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/hello")
                .queryParam("name","1111")
                .queryParam("age", 10)
                .encode()
                .build()
                .toUri();
        System.out.println(uri.toString());

        RestTemplate restTemplate = new RestTemplate();
        //getForObject 로 받을 때
        //String result = restTemplate.getForObject(uri, String.class);

        //getForEntity 로 받을 때
        ResponseEntity<User> result = restTemplate.getForEntity(uri, User.class);

        System.out.println(result.getStatusCode());

        return result.getBody();
    }

    public User post(){
        //전체주소 예제를 위한! http://localhost:9090/api/server/user/{userId}/name/{userName}

        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("api/server/user/{userId}/name/{name}")
                .encode()
                .build()
                .expand(100,"steve")
                .toUri();

        System.out.println(uri);

        // http body -> object -> object mapper -> json -> rest templete -> http body json
        UserRequest req = new UserRequest();
        req.setName("yunji");
        req.setAge(20);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<User> response = restTemplate.postForEntity(uri, req, User.class);

        System.out.println(response.getStatusCode());
        System.out.println(response.getHeaders());
        System.out.println(response.getBody());

        return response.getBody();
    }


    public User exchange(){
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("api/server/user/{userId}/name/{name}")
                .encode()
                .build()
                .expand(100,"steve")
                .toUri();

        System.out.println(uri);

        // http body -> object -> object mapper -> json -> rest templete -> http body json
        UserRequest req = new UserRequest();
        req.setName("yunji");
        req.setAge(20);


        RequestEntity<UserRequest> requestEntity = RequestEntity.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-authorization","abcd")
                .header("custom-header", "fff")
                .body(req);


        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<User> responseEntity = restTemplate.exchange(requestEntity,User.class);

        System.out.println(requestEntity.getBody());
        System.out.println(responseEntity.getStatusCode());

        return responseEntity.getBody();
    }


    public Req<User> genericExchange(){
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("api/server/user/{userId}/name/{name}/req")
                .encode()
                .build()
                .expand(100,"steve")
                .toUri();

        System.out.println(uri);



        UserRequest userRequest = new UserRequest();
        userRequest.setName("yunji");
        userRequest.setAge(20);

        // http body -> object -> object mapper -> json -> rest templete -> http body json
        Req<UserRequest> req = new Req<UserRequest>();
        req.setHeader(new Req.Header());
        req.setBody(userRequest);


        RequestEntity<Req<UserRequest>> requestEntity = RequestEntity.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-authorization","abcd")
                .header("custom-header", "fff")
                .body(req);

        RestTemplate restTemplate =  new RestTemplate();


        ResponseEntity<Req<User>> response = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<Req<User>>(){});


        return response.getBody();
    }

}
