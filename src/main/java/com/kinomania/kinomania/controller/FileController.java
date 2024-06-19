package com.kinomania.kinomania.controller;

import com.kinomania.kinomania.model.*;
import com.kinomania.kinomania.repository.ReservatedSeatsRepository;
import com.kinomania.kinomania.security.UserPrincipal;
import com.kinomania.kinomania.service.ReportService;
import com.kinomania.kinomania.service.TimeService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final ReportService reportService;


    @GetMapping("/api/v1/downloadReport")
    public ResponseEntity<byte[]> downloadReport(@AuthenticationPrincipal UserPrincipal principal, @RequestBody TimeSpanDTO timeSpanDTO) throws IOException {
        Workbook workbook = new XSSFWorkbook();

        List<CinemaTicketsDTO> ticketsPerCinema = reportService.getTicketsPerCinema(TimeService.parseDate(timeSpanDTO.getStartDate()), TimeService.parseDate(timeSpanDTO.getEndDate()));
        List<Object[]> ticketsPerCinemaData = ticketsPerCinema.stream()
                .map(dto -> new Object[]{dto.getCinemaId(), dto.getTicketsCount()})
                .collect(Collectors.toList());
        createExcelSheet(workbook, "Tickets Per Cinema", "Cinema Id", "Tickets Amount", ticketsPerCinemaData);

        List<CinemaIncomeDTO> incomePerCinema = reportService.getTotalTicketPricePerCinema(TimeService.parseDate(timeSpanDTO.getStartDate()), TimeService.parseDate(timeSpanDTO.getEndDate()));
        List<Object[]> incomePerCinemaData = incomePerCinema.stream()
                .map(dto -> new Object[]{dto.getCinemaId(), dto.getIncome().doubleValue()})
                .collect(Collectors.toList());
        createExcelSheet(workbook, "Total Ticket Price Per Cinema", "Cinema Id", "Income", incomePerCinemaData);

        List<MovieTicketsDTO> seatsPerMovie = reportService.getReservedSeatsCountPerMovie(TimeService.parseDate(timeSpanDTO.getStartDate()), TimeService.parseDate(timeSpanDTO.getEndDate()));
        List<Object[]> seatsPerMovieData = seatsPerMovie.stream()
                .map(dto -> new Object[]{dto.getMovieId(), dto.getTicketsCount()})
                .collect(Collectors.toList());
        createExcelSheet(workbook, "Reserved Seats Count Per Movie", "Movie Id", "Tickets Amount", seatsPerMovieData);


        List<UserTicketsDTO> usersTicketAmount = reportService.getUsersTicketsAmount(TimeService.parseDate(timeSpanDTO.getStartDate()), TimeService.parseDate(timeSpanDTO.getEndDate()));
        List<Object[]> usersTicketAmountData = usersTicketAmount.stream()
                .map(dto -> new Object[]{dto.getUserId(), dto.getTicketsCount()})
                .collect(Collectors.toList());
        createExcelSheet(workbook, "Users Tickets Amount", "User Id", "Tickets Amount", usersTicketAmountData);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment", "data.xlsx");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");


        return ResponseEntity.ok()
                .headers(headers)
                .body(outputStream.toByteArray());

    }

    private void createExcelSheet(Workbook workbook, String sheetName,String firstColumn, String secondColumn, List<Object[]> data) {
        Sheet sheet = workbook.createSheet(sheetName);
        int rowNum = 0;
        Row headerRow = sheet.createRow(rowNum++);
        headerRow.createCell(0).setCellValue(firstColumn);
        headerRow.createCell(1).setCellValue(secondColumn);
        for (Object[] rowData : data) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Object field : rowData) {
                Cell cell = row.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                } else if (field instanceof Long) {
                    cell.setCellValue((Long) field);
                } else if (field instanceof Double) {
                    cell.setCellValue((Double) field);
                }
            }
        }
    }
}
