package com.evidyaloka.gateway.services;

import com.evidyaloka.gateway.models.request.Status;
import com.evidyaloka.gateway.models.request.StatusFilter;
import com.evidyaloka.gateway.models.request.UserRequest;
import com.evidyaloka.gateway.models.request.UsersSearchPage;
import com.evidyaloka.gateway.models.response.RcUserResponse;
import com.evidyaloka.gateway.models.response.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class RcService {

    @Autowired
    WebClient rcClient;

    public User getUserById(String userId) {
        return rcClient.get()
                .uri((uriBuilder -> uriBuilder
                        .path("/Users/{id}")
                        .build(userId)
                ))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(User.class)
                .block();
    }

    public List<User> getUsers() {
        ResponseEntity<List<User>> responseEntity = listUsers("Active");
        return responseEntity.getBody();
    }

    /*public ResponseEntity<List<User>> getUserByEmail(String email) {
        return rcClient.post()
                .uri("/Users/search")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(EmailSearchPage.builder()
                        .offset(0)
                        .limit(10)
                        .filters(ContactDetailsSearch.builder()
                                .contactDetails(EmailFilters.builder()
                                        .email(Status.builder()
                                                .eq(email)
                                                .build())
                                        .build())
                                .build())
                        .build()
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntityList(User.class)
                .block();
    }*/

    public ResponseEntity<RcUserResponse> createUser(UserRequest userRequest) {
        return rcClient.post()
                .uri("/Users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userRequest)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(RcUserResponse.class)
                .block();
    }

    public ResponseEntity<RcUserResponse> updateUser(UserRequest userRequest, String userId) {
        return rcClient.put()
                .uri((uriBuilder -> uriBuilder
                        .path("/Users/{id}")
                        .build(userId)
                ))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userRequest)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(RcUserResponse.class)
                .block();
    }

    public ResponseEntity<List<User>> listUsers (String status) {
        return rcClient.post()
                .uri("/Users/search")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(UsersSearchPage.builder()
                        .offset(0)
                        .limit(500)
                        .filters(StatusFilter.builder()
                                .status(Status.builder()
                                        .eq(status)
                                        .build())
                                .build())
                        .build()
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntityList(User.class)
                .block();
    }

    public ResponseEntity<List<User>> getAllusers(List<User> allUsers) {
        return rcClient.post()
                .uri("/Users/search")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(UsersSearchPage.builder()
                        .offset(0)
                        .limit(10)
                        .filters(StatusFilter.builder()
                                .status(Status.builder()
                                        .build())
                                .build())
                        .build()
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntityList(User.class)
                .block();

    }
}
