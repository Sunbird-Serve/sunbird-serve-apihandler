package com.evidyaloka.gateway.controllers;

import com.evidyaloka.gateway.api.UserApi;
import com.evidyaloka.gateway.models.request.UserRequest;
import com.evidyaloka.gateway.models.response.RcUserResponse;
import com.evidyaloka.gateway.models.response.User;
import com.evidyaloka.gateway.services.RcService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@CrossOrigin(origins = "*")
public class UserController implements UserApi {

    private final RcService rcService;

    @Override
    public ResponseEntity<User> getUserById(String userId, Map<String, String> headers) {
        return ResponseEntity.ok(rcService.getUserById(userId));
    }

    @Override
    public ResponseEntity<User> getUserByEmail(String email, Map<String, String> headers) {
        return rcService.getUserByEmail(email);
    }

    @Override
    public ResponseEntity<RcUserResponse> createUser(UserRequest userRequest, Map<String, String> headers) {
        return rcService.createUser(userRequest);
    }

    @Override
    public ResponseEntity<RcUserResponse> updateUser(String userId, UserRequest userRequest, Map<String, String> headers) {
        return rcService.updateUser(userRequest, userId);
    }
}
