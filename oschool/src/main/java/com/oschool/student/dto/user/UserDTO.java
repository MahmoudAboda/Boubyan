/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */


package com.oschool.student.dto.user;


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
 * Data Transfer Object (DTO) for user details.
 * Used to transfer user data between different layers of the application.
 */
@Builder
@Data
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    /**
     * Unique identifier for the user.
     */
    private Long id;

    /**
     * Email of the user, which must be unique and cannot be null.
     */
    @Column(unique = true, nullable = false)
    @NotNull
    private String email;

    /**
     * Role of the user, represented as an enum.
     */
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * First name of the user.
     */
    private String firstName;

    /**
     * Last name of the user.
     */
    private String lastName;
}