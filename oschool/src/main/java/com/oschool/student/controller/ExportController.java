/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */
package com.oschool.student.controller;

import com.oschool.student.service.IExportService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
@RestController
@RequestMapping(ExportController.ExportPath)
@AllArgsConstructor
public class ExportController {

    private final IExportService exportService;
    public static final String ExportPath = "api/v1/export";


    @ApiOperation(value = "Export student courses report ")
    @GetMapping(value = "/student-courses-report", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> exportStudentCoursesReport(@RequestHeader(name = "Authorization") String token) {
        ByteArrayInputStream pdfStream = exportService.exportStudentCoursesReport( token);

        // Set the headers for the response
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=student_courses_report.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));

    }



}
