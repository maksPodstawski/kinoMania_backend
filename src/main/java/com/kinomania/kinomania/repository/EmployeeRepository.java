package com.kinomania.kinomania.repository;

import com.kinomania.kinomania.entity.Cinema;
import com.kinomania.kinomania.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("select e.cinema from Employee e where e.user.user_id = :id")
    Cinema getCinemaByUserId(@Param("id") Long id);
}
