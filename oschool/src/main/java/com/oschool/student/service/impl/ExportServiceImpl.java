/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */

package com.oschool.student.service.impl;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.oschool.student.dao.CourseRepository;
import com.oschool.student.dto.StudentCourseDTO;
import com.oschool.student.error.exceptions.AbstractStudentException;
import com.oschool.student.error.exceptions.AbstractTokenExpiredException;
import com.oschool.student.service.IExportService;
import com.oschool.student.util.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.stream.IntStream;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ExportServiceImpl implements IExportService {
    private final CourseRepository courseRepository;
    private final JwtUtils jwtUtils;


    /**
     * Exports a report of courses for a specific student as a PDF document.
     * Retrieves the student's courses, creates a PDF with a table of course details, and returns the PDF as a ByteArrayInputStream.
     *
     * @param token     The token used for authorization (currently not used in the method).
     * @return A ByteArrayInputStream containing the PDF report.
     * @throws RuntimeException if the token is expired or any other runtime exception occurs during the report generation.
     */
    public ByteArrayInputStream exportStudentCoursesReport( String token) {
        String[] headerColumns = {"#", "Student name", "Student email", "Course name", "Course start date", "Course end date"};

        try {
            // Retrieve student courses from the repository
            List<StudentCourseDTO> studentCourses = courseRepository.findStudentCoursesByStudentId(Long.parseLong(jwtUtils.getStudentIdFromToken(token)));

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Add table header
            Table table = new Table(headerColumns.length);
            for (String header : headerColumns) {
                Cell cell = new Cell();
                cell.add(new Paragraph(header)
                        .setFontSize(12)
                        .setBold()
                        .setFontColor(ColorConstants.WHITE));
                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                table.addHeaderCell(cell);
            }

            // Use Stream API to add rows to the table
            IntStream.range(0, studentCourses.size())
                    .forEach(index -> {
                        StudentCourseDTO studentCourse = studentCourses.get(index);
                        table.addCell(String.valueOf(index + 1));
                        table.addCell(studentCourse.getStudentName());
                        table.addCell(studentCourse.getStudentEmail());
                        table.addCell(studentCourse.getCourseName());
                        table.addCell(studentCourse.getCourseStartDate().toString());
                        table.addCell(studentCourse.getCourseEndDate().toString());
                    });

            document.add(table);
            document.close();

            return new ByteArrayInputStream(out.toByteArray());
        } catch (ExpiredJwtException e) {
            // Handle case where the token is expired by throwing a runtime exception
            throw new AbstractTokenExpiredException("Token is expired");
        }  catch (Exception e) {
            // Handle other exceptions and wrap them in a runtime exception
            throw new AbstractStudentException(e.getMessage());
        }
    }

}
