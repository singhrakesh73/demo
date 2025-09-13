package com.example.demo.controller;

import com.example.demo.controller.controller.UserController;
import com.example.demo.controller.pojo.Response;
import com.example.demo.controller.pojo.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private final RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
    private final UserController userController = new UserController(restTemplate);

    @Test
    public void testGetUsers() {
        User[] users = new User[]{
                new User(),
                new User()
        };

        when(restTemplate.getForObject("https://jsonplaceholder.typicode.com/users", User[].class)).thenReturn(users);

        ResponseEntity<List<Response>> responseEntity = userController.getUsers();

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(2, responseEntity.getBody().size());
    }
}
