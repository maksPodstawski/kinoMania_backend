package com.kinomania.kinomania.controller;

import com.kinomania.kinomania.entity.*;
import com.kinomania.kinomania.model.*;
import com.kinomania.kinomania.repository.CinemaRepository;
import com.kinomania.kinomania.security.UserPrincipal;
import com.kinomania.kinomania.service.*;
import lombok.AllArgsConstructor;
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
    private final PositionService positionService;

    @PostMapping("/api/v1/panel/addMovie")
    public ResponseDTO addMovie(@AuthenticationPrincipal UserPrincipal principal, @RequestBody Movie movie) {
        if (movie.getDescription().trim().isEmpty() || movie.getDirector().trim().isEmpty() ||
                movie.getGenre().trim().isEmpty() || movie.getTitle().trim().isEmpty() || movie.getDuration() <= 0 ||
                movie.getImg_url().trim().isEmpty()) {
            return new ResponseDTO(0L, "All fields must be filled");
        }
        movieService.saveMovie(movie);
        return new ResponseDTO(1L, "Movie added successfully");
    }

    @GetMapping("/api/v1/panel/admin")
    public String admin(@AuthenticationPrincipal UserPrincipal principal) {
        return "admin" + principal.getUsername() + " has been logged in " + principal.getUserId();
    }

    @PostMapping("/api/v1/panel/addCinema")
    public ResponseDTO addCinema(@AuthenticationPrincipal UserPrincipal principal, @RequestBody Cinema cinema) {
        if (cinema.getAddress().trim().isEmpty() || cinema.getCity().trim().isEmpty() ||
                cinema.getImage_url().trim().isEmpty()) {
            return new ResponseDTO(0L, "All fields must be filled");
        }
        cinemaService.save(cinema);
        return new ResponseDTO(1L, "Cinema added successfully");
    }

    @PostMapping("/api/v1/panel/addEmployee")
    public ResponseDTO addEmployee(@AuthenticationPrincipal UserPrincipal principal, @RequestBody EmployeeDto employeeDto) {
        if (employeeDto.getSurname().trim().isEmpty() || employeeDto.getName().trim().isEmpty() ||
                employeeDto.getUserId() == null || employeeDto.getPositionId() == null || employeeDto.getCinemaId() == null) {
            return new ResponseDTO(0L, "All fields must be filled");
        }
        employeeService.save(employeeDto);
        return new ResponseDTO(1L, "Employee added successfully");
    }

    @PostMapping("/api/v1/panel/addRoom")
    public ResponseDTO addRoom(@AuthenticationPrincipal UserPrincipal principal, @RequestBody RoomDto roomDto) {
        if(roomDto.getRoomNumber() <= 0 || roomDto.getCinemaId() == null) {
            return new ResponseDTO(0L, "All fields must be filled");
        }
        roomService.save(roomDto);
        return new ResponseDTO(1L, "Room added successfully");
    }

    @PostMapping("/api/v1/panel/addScreening")
    public ResponseDTO addScreening(@AuthenticationPrincipal UserPrincipal principal, @RequestBody ScreeningDto screeningDto) throws ParseException {
        if(screeningDto.getMovieId() == null || screeningDto.getRoomId() == null || screeningDto.getScreeningDate() == null) {
            return new ResponseDTO(0L, "All fields must be filled");
        }
        screeningService.save(screeningDto);
        return new ResponseDTO(1L, "Screening added successfully");
    }

    @PostMapping("/api/v1/panel/addRoomWithSeats")
    public ResponseDTO addRoomWithSeats(@AuthenticationPrincipal UserPrincipal principal, @RequestBody SeatsAndRoomDTO seatsAndRoomDTO) {
        if(seatsAndRoomDTO.getColumns() <= 0 || seatsAndRoomDTO.getRows() <= 0 || seatsAndRoomDTO.getRoomNumber() <= 0 || seatsAndRoomDTO.getCinemaId() == null) {
            return new ResponseDTO(0L, "All fields must be filled");
        }
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
        return new ResponseDTO(1L, "Room with seats added successfully");
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
    public ResponseDTO addRoomToSeats(@AuthenticationPrincipal UserPrincipal principal, @RequestBody SeatsDto seatsDto) {
        if(seatsDto.getRoomId() == null || seatsDto.getRows() <= 0 || seatsDto.getColumns() <= 0) {
            return new ResponseDTO(0L, "All fields must be filled");
        }
        seatsService.saveAllSeats(seatsDto);
        return new ResponseDTO(1L, "Seats added successfully");
    }

    @PutMapping("/api/v1/panel/enableMovie/{movieId}")
    public ResponseDTO enableMovie(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long movieId) {
        movieService.enableMovie(movieId);
        return new ResponseDTO(1L, "Movie enabled successfully");
    }

    @PutMapping("/api/v1/panel/removeMovie/{movieId}")
    public ResponseDTO removeMovie(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long movieId) {
        movieService.disableMovie(movieId);
        return new ResponseDTO(1L, "Move disabled successfully");
    }

    @DeleteMapping("/api/v1/panel/removeCinema/{cinemaId}")
    public ResponseDTO removeCinema(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long cinemaId) {
        cinemaService.deleteCinema(cinemaId);
        return new ResponseDTO(1L, "Cinema deleted successfully");
    }

    @PostMapping("/api/v1/panel/addRoomToCinema")
    public ResponseDTO addRoomToCinema(@AuthenticationPrincipal UserPrincipal principal, @RequestBody RoomDto roomDto) {
        roomService.save(roomDto);
        return new ResponseDTO(1L, "Room added successfully to Cinema");
    }

    @GetMapping("/api/v1/getRoomsByWorker")
    public List<Room> getRoomsByWorker(@AuthenticationPrincipal UserPrincipal principal) {
        return roomService.getRoomsByCinemaId(employeeService.getCinemaByUserId(principal.getUserId()).getCinema_id());
    }

    @GetMapping("/api/v1/panel/getUserByEmail/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/api/v1/panel/getPositions")
    public List<Position> getPositions() {
        return positionService.getAllPositions();
    }
}
