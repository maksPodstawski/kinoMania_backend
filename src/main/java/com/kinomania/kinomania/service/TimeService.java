package com.kinomania.kinomania.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TimeService {
    public static LocalDateTime parseDate(String date) {
        System.out.println("Parsing date: " + LocalDate.parse(date));
        return LocalDate.parse(date).atStartOfDay();
    }
}
