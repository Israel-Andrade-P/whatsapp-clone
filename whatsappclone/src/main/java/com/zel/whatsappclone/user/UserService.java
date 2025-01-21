package com.zel.whatsappclone.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    public List<UserResponse> getAllUsersExceptSelf(Authentication loggedUser){
        return userRepository.findAllUsersExceptSelf(loggedUser.getName()).stream().map(mapper::fromUser).toList();
    }
}
