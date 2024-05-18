package com.kinomania.kinomania.service;

import com.kinomania.kinomania.entity.User;
import com.kinomania.kinomania.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateLastLogin(String username) {
        var user = userRepository.findByUsername(username);
        user.setLast_login_at(LocalDateTime.now());
        userRepository.save(user);
    }

    public void setUpWorkerStatus(User user){
        userRepository.updateUserRoleToWorker(user.getUser_id());
    }
}

