package com.kinomania.kinomania.repository;

import com.kinomania.kinomania.entity.UnloggedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnLoggedUserRepository extends JpaRepository<UnloggedUser, Long> {

}
