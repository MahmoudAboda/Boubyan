/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */

package com.oschool.student.dto.course;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for creating a course.
 * Used to transfer course creation data between layers.
 */
@Builder
@Data
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class CourseCreateDTO {

    /**
     * Name of the course.
     */
    @NotEmpty(message = "{course.name.is.required}")
    @NotNull(message = "{course.name.is.required}")
    private String name;

    /**
     * Start date and time of the course.
     * The date and time are formatted as "yyyy-MM-dd HH:mm:ss".
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "{course.start.date.is.required}")
    private LocalDateTime startDate;

    /**
     * End date and time of the course.
     * The date and time are formatted as "yyyy-MM-dd HH:mm:ss".
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "{course.end.date.is.required}")
    private LocalDateTime endDate;
}