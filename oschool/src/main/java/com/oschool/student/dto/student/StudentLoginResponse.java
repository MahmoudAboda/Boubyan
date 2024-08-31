/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */

package com.oschool.student.dto.student;

import lombok.Builder;
import lombok.Data;

/**
 * Represents the response returned after a successful student login.
 * Includes a JWT token and student login details.
 */
@Builder
@Data
public class StudentLoginResponse {

    /**
     * JSON Web Token (JWT) issued upon successful login.
     * Used for authentication and authorization in subsequent requests.
     */
    private String token;

    /**
     * Contains detailed login information about the student.
     * Includes fields such as student ID, email, role, first name, and last name.
     */
    private StudentLoginInformation studentLoginInformation;
}