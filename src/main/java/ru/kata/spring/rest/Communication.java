package ru.kata.spring.rest;

import org.springframework.http.*;
import ru.kata.spring.rest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Objects;


@Component
public class Communication {

    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;
    private final String URl = "http://94.198.50.185:7081/api/users";
    private String sessionId;

    @Autowired
    public Communication(RestTemplate restTemplate, HttpHeaders httpHeaders) {
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
    }

    // вывод всех пользователей
    public ResponseEntity<String> getUsers() {
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URl, HttpMethod.GET, entity, String.class);
        sessionId = String.join(";", Objects.requireNonNull(responseEntity.getHeaders().get("Set-Cookie")));
        return responseEntity;
    }

    //добавление пользователя
    public String saveUser(User user) {
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.set("Cookie", sessionId);
        HttpEntity<User> entity = new HttpEntity<>(user, httpHeaders);
        return restTemplate.exchange(URl, HttpMethod.POST, entity, String.class).getBody();
    }

    // изменение пользователя
    public String updateUser(User user) {
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.set("Cookie", sessionId);
        HttpEntity<User> entity = new HttpEntity<>(user, httpHeaders);
        return restTemplate.exchange(URl, HttpMethod.PUT, entity, String.class).getBody();
    }

    // Удаление пользователя
    public String deleteUser(Long id) {
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.set("Cookie", sessionId);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(URl + "/" + id, HttpMethod.DELETE, entity, String.class).getBody();
    }
}
