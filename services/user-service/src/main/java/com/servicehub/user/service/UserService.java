package com.servicehub.user.service;

import com.servicehub.user.dto.UserRequestDTO;
import com.servicehub.user.dto.UserResponseDTO;

public interface UserService {

    UserResponseDTO registerUser(UserRequestDTO request);

    UserResponseDTO getUserById(Long id);

    void deleteUser(Long id);
}