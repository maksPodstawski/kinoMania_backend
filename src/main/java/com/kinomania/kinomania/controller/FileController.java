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
import org.springframework.web.bind.annotation.PostMapping;
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


    @PostMapping("/api/v1/downloadReport")
    public ResponseEntity<byte[]> downloadReport(@AuthenticationPrincipal UserPrincipal principal, @RequestBody TimeSpanDTO timeSpanDTO) throws IOException {
        Workbook workbook = new XSSFWorkbook();

        List<CinemaTicketsDTO> ticketsPerCinema = reportService.getTicketsPerCinema(TimeService.parseDate(timeSpanDTO.getStartDate()), TimeService.parseDate(timeSpanDTO.getEndDate()));
        List<Object[]> ticketsPerCinemaData = ticketsPerCinema.stream()
                .map(dto -> new Object[]{dto.getCinemaId(), dto.getCinemaAddress(),dto.getTicketsCount()})
                .collect(Collectors.toList());
        List<String> columns = List.of("Cinema Id", "Cinema Address", "Tickets Amount");
        createExcelSheet(workbook, "Tickets Per Cinema", columns, ticketsPerCinemaData);

        List<CinemaIncomeDTO> incomePerCinema = reportService.getTotalTicketPricePerCinema(TimeService.parseDate(timeSpanDTO.getStartDate()), TimeService.parseDate(timeSpanDTO.getEndDate()));
        List<Object[]> incomePerCinemaData = incomePerCinema.stream()
                .map(dto -> new Object[]{dto.getCinemaId(), dto.getCinemaAddress(),dto.getIncome().doubleValue()})
                .collect(Collectors.toList());
        List<String> columns2 = List.of("Cinema Id", "Cinema Address", "Income");
        createExcelSheet(workbook, "Total Ticket Price Per Cinema", columns2, incomePerCinemaData);

        List<MovieTicketsDTO> seatsPerMovie = reportService.getReservedSeatsCountPerMovie(TimeService.parseDate(timeSpanDTO.getStartDate()), TimeService.parseDate(timeSpanDTO.getEndDate()));
        List<Object[]> seatsPerMovieData = seatsPerMovie.stream()
                .map(dto -> new Object[]{dto.getMovieId(), dto.getMovieTitle(),dto.getTicketsCount()})
                .collect(Collectors.toList());
        List<String> columns3 = List.of("Movie Id", "Movie Title", "Tickets Amount");
        createExcelSheet(workbook, "Reserved Seats Count Per Movie", columns3, seatsPerMovieData);


        List<UserTicketsDTO> usersTicketAmount = reportService.getUsersTicketsAmount(TimeService.parseDate(timeSpanDTO.getStartDate()), TimeService.parseDate(timeSpanDTO.getEndDate()));
        List<Object[]> usersTicketAmountData = usersTicketAmount.stream()
                .map(dto -> new Object[]{dto.getUserId(), dto.getUserName(),dto.getTicketsCount()})
                .collect(Collectors.toList());
        List<String> columns4 = List.of("User Id", "User Name", "Tickets Amount");
        createExcelSheet(workbook, "Users Tickets Amount", columns4, usersTicketAmountData);

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

    private static void createExcelSheet(Workbook workbook, String sheetName, List<String> columns, List<Object[]> data) {
        Sheet sheet = workbook.createSheet(sheetName);

        // Create header row
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns.get(i));
        }

        // Populate data rows
        for (int rowIndex = 0; rowIndex < data.size(); rowIndex++) {
            Object[] rowData = data.get(rowIndex);
            Row dataRow = sheet.createRow(rowIndex + 1);
            for (int columnIndex = 0; columnIndex < rowData.length; columnIndex++) {
                Cell cell = dataRow.createCell(columnIndex);
                if (rowData[columnIndex] instanceof String) {
                    cell.setCellValue((String) rowData[columnIndex]);
                } else if (rowData[columnIndex] instanceof Double) {
                    cell.setCellValue((Double) rowData[columnIndex]);
                }
                else if (rowData[columnIndex] instanceof Long) {
                    cell.setCellValue((Long) rowData[columnIndex]);
                }// Add more data types as needed
            }
        }
    }
}