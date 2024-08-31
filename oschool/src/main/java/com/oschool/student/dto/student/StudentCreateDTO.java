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

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Data Transfer Object (DTO) for creating a student.
 * Used to transfer student creation data between layers.
 */
@Builder
@Data
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class StudentCreateDTO {

    /**
     * Email of the student, which must be unique and cannot be null.
     */
    @Column(unique = true, nullable = false)
    @NotNull
    @Email
    @NotEmpty(message = "{email.is.required}")
    @NotNull(message = "{email.is.required}")
    private String email;

    /**
     * Role of the student, represented as an enum.
     */
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{role.is.required}")
    private Role role;

    /**
     * User ID associated with the student, used for linking to a user.
     */
    private Long userId;

    /**
     * First name of the student.
     */
    @NotEmpty(message = "{first.name.is.required}")
    @NotNull(message = "{first.name.is.required}")
    private String firstName;

    /**
     * Last name of the student.
     */
    @NotEmpty(message = "{last.name.required}")
    @NotNull(message = "{last.name.required}")
    private String lastName;
}