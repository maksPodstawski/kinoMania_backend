package com.kinomania.kinomania.repository;

import com.kinomania.kinomania.entity.PasswordRecovery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PasswordRecoveryRepository extends JpaRepository<PasswordRecovery, Long> {

    @Query("SELECT pr FROM PasswordRecovery pr WHERE pr.recovery_code = :recoveryCode")
    PasswordRecovery findByRecoveryCode(@Param("recoveryCode")String recoveryCode);
}
