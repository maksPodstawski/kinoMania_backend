package com.kinomania.kinomania.repository;

import com.kinomania.kinomania.entity.User;
import com.kinomania.kinomania.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select * from users where username=?", nativeQuery = true)
    public User findByUsername(String username);

    public User getUserByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users SET role = 'ROLE_WORKER' WHERE user_id = :id", nativeQuery = true)
    void updateUserRoleToWorker(@Param("id") Long id);

}

