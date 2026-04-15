package com.servicehub.user.service;

import com.servicehub.user.dto.UserRequestDTO;
import com.servicehub.user.dto.UserResponseDTO;

import java.util.concurrent.CompletableFuture;

public interface UserService {

    UserResponseDTO registerUser(UserRequestDTO request);

    CompletableFuture<UserResponseDTO> getUserById(Long id);

    void deleteUser(Long id);
}