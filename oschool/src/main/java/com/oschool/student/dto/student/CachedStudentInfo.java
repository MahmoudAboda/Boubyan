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

import java.io.Serializable;

/**
 * Represents cached student information.
 * Implements Serializable for object serialization.
 */
@Builder
@Data
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class CachedStudentInfo implements Serializable {

    /**
     * First name of the student.
     */
    private String firstName;

    /**
     * Last name of the student.
     */
    private String lastName;

    /**
     * Unique identifier for the student.
     */
    private Long id;

    /**
     * Email of the student.
     */
    private String email;

    /**
     * Role of the student, represented as an enum.
     */
    private Role role;

    // Implement Serializable requires a serialVersionUID field (optional but recommended)
    private static final long serialVersionUID = 1L;
}