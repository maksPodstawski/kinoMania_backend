package com.kinomania.kinomania.repository;

import com.kinomania.kinomania.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select * from users where username=?", nativeQuery = true)
    public User findByUsername(String username);

    /*@Query(value = "select * from users where email=?", nativeQuery = true)
    public User findByEmail(String email);*/

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}

