/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */


package com.oschool.student.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) representing the association between a student and a course.
 * Used to transfer combined data of a student and a course in API responses.
 */
@Data
@Builder
@NoArgsConstructor
public class StudentCourseDTO {

    /**
     * Unique identifier for the course.
     */
    private Long courseId;

    /**
     * Name of the course.
     */
    private String courseName;

    /**
     * Start date and time of the course.
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime courseStartDate;

    /**
     * End date and time of the course.
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime courseEndDate;

    /**
     * Unique identifier for the student.
     */
    private Long studentId;

    /**
     * Name of the student.
     */
    private String studentName;

    /**
     * Email of the student.
     */
    private String studentEmail;

    /**
     * Constructor for direct JPA mapping to populate all fields.
     *
     * @param courseId        Unique identifier for the course.
     * @param courseName      Name of the course.
     * @param courseStartDate Start date and time of the course.
     * @param courseEndDate   End date and time of the course.
     * @param studentId       Unique identifier for the student.
     * @param studentName     Name of the student.
     * @param studentEmail    Email of the student.
     */
    public StudentCourseDTO(Long courseId, String courseName, LocalDateTime courseStartDate, LocalDateTime courseEndDate, Long studentId, String studentName, String studentEmail) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
    }
}