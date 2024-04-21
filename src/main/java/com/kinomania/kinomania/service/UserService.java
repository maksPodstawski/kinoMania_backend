package com.kinomania.kinomania.service;

import com.kinomania.kinomania.entity.User;
import com.kinomania.kinomania.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> findByUsername(String username) {
        var user = userRepository.findByUsername(username);
        if (user.getUsername().equals(username)) {
            return Optional.of(user);
        } else {
            return Optional.empty();
        }

    }
}

