package com.kinomania.kinomania.controller;

import com.kinomania.kinomania.entity.Cinema;
import com.kinomania.kinomania.entity.Employee;
import com.kinomania.kinomania.entity.Movie;
import com.kinomania.kinomania.model.EmployeeDto;
import com.kinomania.kinomania.model.RoomDto;
import com.kinomania.kinomania.model.ScreeningDto;
import com.kinomania.kinomania.repository.CinemaRepository;
import com.kinomania.kinomania.security.UserPrincipal;
import com.kinomania.kinomania.service.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;


@RestController
@AllArgsConstructor
public class PanelController {

    private final MovieService movieService;
    private final CinemaRepository cinemaRepository;
    private final CinemaService cinemaService;
    private final EmployeeService employeeService;
    private final RoomService roomService;
    private final ScreeningService screeningService;

    @PostMapping("/api/v1/panel/addMovie")
    public String addMovie(@AuthenticationPrincipal UserPrincipal principal, @RequestBody Movie movie) {
        movieService.saveMovie(movie);
        return "Movie added successfully by " + principal.getUsername() + " ID: " + principal.getUserId();
    }

    @GetMapping("/api/v1/panel/admin")
    public String admin(@AuthenticationPrincipal UserPrincipal principal) {
        return "admin" + principal.getUsername() + " has been logged in " + principal.getUserId();
    }

    @PostMapping("/api/v1/panel/addCinema")
    public String addCinema(@AuthenticationPrincipal UserPrincipal principal, @RequestBody Cinema cinema) {
        cinemaService.save(cinema);
        return "Cinema added successfully by " + principal.getUsername() + " ID: " + principal.getUserId();
    }

    @PostMapping("/api/v1/panel/addEmployee")
    public String addEmployee(@AuthenticationPrincipal UserPrincipal principal, @RequestBody EmployeeDto employeeDto) {
        employeeService.save(employeeDto);
        return "Employee added successfully by " + principal.getUsername() + " ID: " + principal.getUserId();
    }

    @PostMapping("/api/v1/panel/addRoom")
    public String addRoom(@AuthenticationPrincipal UserPrincipal principal, @RequestBody RoomDto roomDto) {
        roomService.save(roomDto);
        return "Room added successfully by " + principal.getUsername() + " ID: " + principal.getUserId();
    }

    @PostMapping("/api/v1/panel/addScreening")
    public String addScreening(@AuthenticationPrincipal UserPrincipal principal, @RequestBody ScreeningDto screeningDto) throws ParseException {
        screeningService.save(screeningDto);
        return "Screening added successfully by " + principal.getUsername() + " ID: " + principal.getUserId();
    }

}
