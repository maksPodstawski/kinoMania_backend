package com.kinomania.kinomania.service;

import com.kinomania.kinomania.entity.Cinema;
import com.kinomania.kinomania.entity.Employee;
import com.kinomania.kinomania.entity.Position;
import com.kinomania.kinomania.entity.User;
import com.kinomania.kinomania.model.EmployeeDto;
import com.kinomania.kinomania.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private  final  CinemaService cinemaService;
    private  final  PositionService positionService;
    private  final  UserService userService;


    public void save(EmployeeDto employeeDto) {
        var employee = new Employee();
        employee.setName(employeeDto.getName());
        employee.setSurname(employeeDto.getSurname());
        employee.setCinema(cinemaService.getCinemaById(employeeDto.getCinemaId()));
        employee.setPosition(positionService.getPositionById(employeeDto.getPositionId()));
        employee.setUser(userService.getUserById(employeeDto.getUserId()));
        employeeRepository.save(employee);
    }
}
