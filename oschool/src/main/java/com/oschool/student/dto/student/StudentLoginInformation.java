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

/**
 * Represents login information for a student.
 * Used to transfer student details during the login process.
 */
@Builder
@Data
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class StudentLoginInformation {

    /**
     * Unique identifier for the student.
     */
    private Long id;

    /**
     * Email address of the student, used for authentication.
     */
    private String email;

    /**
     * Role of the student, represented as an enum.
     */
    private Role role;

    /**
     * First name of the student.
     */
    private String firstName;

    /**
     * Last name of the student.
     */
    private String lastName;
}