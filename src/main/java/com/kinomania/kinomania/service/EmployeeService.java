package com.kinomania.kinomania.service;

import com.kinomania.kinomania.entity.Cinema;
import com.kinomania.kinomania.entity.Employee;
import com.kinomania.kinomania.entity.Position;
import com.kinomania.kinomania.entity.User;
import com.kinomania.kinomania.model.EmployeeDto;
import com.kinomania.kinomania.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private  final  CinemaService cinemaService;
    private  final  PositionService positionService;
    private  final  UserService userService;


    public void save(EmployeeDto employeeDto) {
        var employee = Employee.builder()
                .name(employeeDto.getName())
                .surname(employeeDto.getSurname())
                .cinema(cinemaService.getCinemaById(employeeDto.getCinemaId()))
                .position(positionService.getPositionById(employeeDto.getPositionId()))
                .user(userService.getUserById(employeeDto.getUserId()))
                .build();

        employeeRepository.save(employee);
        userService.setUpWorkerStatus(employee.getUser());

    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Cinema getCinemaByUserId(Long id) {
        return employeeRepository.getCinemaByUserId(id);
    }
}
