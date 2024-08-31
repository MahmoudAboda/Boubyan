/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */


package com.oschool.student.dto.course;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oschool.student.enumeration.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


/**
 * Data Transfer Object (DTO) for responding with course data.
 * Used to transfer course details in responses.
 */
@Builder
@Data
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponseDTO {

    /**
     * Unique identifier for the course.
     */
    private Long id;

    /**
     * Name of the course.
     */
    private String name;

    /**
     * Start date and time of the course.
     * The date and time are formatted as "yyyy-MM-dd HH:mm:ss".
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    /**
     * End date and time of the course.
     * The date and time are formatted as "yyyy-MM-dd HH:mm:ss".
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;
}