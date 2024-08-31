/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */

package com.oschool.student.dto.student;

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

/**
 * Data Transfer Object (DTO) for responding with student data.
 * Used to transfer student details in API responses.
 */
@Builder
@Data
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponseDTO {

    /**
     * Unique identifier for the student.
     */
    private Long id;

    /**
     * Email of the student, which must be unique and cannot be null.
     */
    @Column(unique = true, nullable = false)
    @NotNull
    private String email;

    /**
     * Role of the student, represented as an enum.
     */
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * User ID associated with the student, used for linking to a user.
     */
    private Long userId;

    /**
     * First name of the student.
     */
    private String firstName;

    /**
     * Last name of the student.
     */
    private String lastName;
}