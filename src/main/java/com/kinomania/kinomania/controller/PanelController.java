package com.kinomania.kinomania.controller;

import com.kinomania.kinomania.entity.*;
import com.kinomania.kinomania.model.*;
import com.kinomania.kinomania.repository.CinemaRepository;
import com.kinomania.kinomania.security.UserPrincipal;
import com.kinomania.kinomania.service.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;


@RestController
@AllArgsConstructor
public class PanelController {

    private final MovieService movieService;
    private final CinemaRepository cinemaRepository;
    private final CinemaService cinemaService;
    private final EmployeeService employeeService;
    private final RoomService roomService;
    private final ScreeningService screeningService;
    private final UserService userService;
    private final SeatsService seatsService;

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

    @PostMapping("/api/v1/panel/addRoomWithSeats")
    public String addRoomWithSeats(@AuthenticationPrincipal UserPrincipal principal, @RequestBody SeatsAndRoomDTO seatsAndRoomDTO) {
        RoomDto roomDto = RoomDto.builder()
                                 .cinemaId(seatsAndRoomDTO.getCinemaId())
                                 .roomNumber(seatsAndRoomDTO.getRoomNumber())
                                 .build();

        Room room = roomService.save(roomDto);

        SeatsDto seatsDto = SeatsDto.builder()
                                    .RoomId(room.getRoom_id())
                                    .Rows(seatsAndRoomDTO.getRows())
                                    .Columns(seatsAndRoomDTO.getColumns())
                                    .build();
        seatsService.saveAllSeats(seatsDto);
        return "Room with seats added successfully by " + principal.getUsername() + " ID: " + principal.getUserId();
    }



    @GetMapping("/api/v1/panel/getUsers")
    public List<User> getUsers(@AuthenticationPrincipal UserPrincipal principal) {
        return userService.getAllUsers();
    }

    @GetMapping("/api/v1/panel/getEmployees")
    public List<Employee> getEmployees(@AuthenticationPrincipal UserPrincipal principal) {
        return employeeService.getAllEmployees();
    }

    @PostMapping("/api/v1/panel/addSeatsToRoom")
    public String addRoomToSeats(@AuthenticationPrincipal UserPrincipal principal, @RequestBody SeatsDto seatsDto) {
        seatsService.saveAllSeats(seatsDto);
        return "Seats added successfully by " + principal.getUsername() + " ID: " + principal.getUserId();
    }

    @PutMapping("/api/v1/panel/enableMovie/{movieId}")
    public String enableMovie(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long movieId){
        movieService.enableMovie(movieId);
        return "Movie enabled successfully by "  + principal.getUsername() + " ID: " + principal.getUserId();
    }

    @PutMapping("/api/v1/panel/removeMovie/{movieId}")
    public String removeMovie(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long movieId){
        movieService.disableMovie(movieId);
        return "Move disablet successfully by "  + principal.getUsername() + " ID: " + principal.getUserId();
    }

    @DeleteMapping("/api/v1/panel/removeCinema/{cinemaId}")
    public String removeCinema(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long cinemaId){
        cinemaService.deleteCinema(cinemaId);
        return "Cinema deleted successfully by "  + principal.getUsername() + " ID: " + principal.getUserId();
    }
    @PostMapping("/api/v1/panel/addRoomToCinema")
    public String addRoomToCinema(@AuthenticationPrincipal UserPrincipal principal, @RequestBody RoomDto roomDto) {
        roomService.save(roomDto);
        return "Room added successfully to Cinema by " + principal.getUsername() + " ID: " + principal.getUserId();
    }




}
