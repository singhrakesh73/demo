package com.example.demo.controller.controller;

import com.example.demo.controller.pojo.Response;
import com.example.demo.controller.pojo.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private final RestTemplate restTemplate;

    public UserController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/users")
    public ResponseEntity<List<Response>> getUsers() {
        String url = "https://jsonplaceholder.typicode.com/users";
        User[] users = restTemplate.getForObject(url, User[].class);
        List<Response> responseList = new ArrayList<>();
        List<Response> list = Arrays.stream(users).map(u -> {
            Response response = new Response();
            response.setId(u.getId());
            if (u.getAddress() != null && u.getAddress().getGeo() != null) {
                response.setLatitude(u.getAddress().getGeo().getLat());
                response.setLongitude(u.getAddress().getGeo().getLng());
            }
            if (u.getCompany() != null) {
                response.setCompanyName(u.getCompany().getName());
            }
            return response;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<String> handleRestClientException(RestClientException ex) {
        return new ResponseEntity<>("Error occurred while making REST call", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
