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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Data Transfer Object (DTO) for creating a user.
 * Used to transfer user creation data between layers.
 */
@Builder
@Data
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {

    /**
     * Email of the user, which must be unique and cannot be null.
     */
    @Column(unique = true, nullable = false)
    @Email
    @NotEmpty(message = "{email.is.required}")
    @NotNull(message = "{email.is.required}")
    private String email;

    /**
     * Role of the user, represented as an enum.
     */
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{role.is.required}")
    private Role role;

    /**
     * First name of the user.
     */
    @NotEmpty(message = "{first.name.is.required}")
    @NotNull(message = "{first.name.is.required}")
    private String firstName;

    /**
     * Last name of the user.
     */
    @NotEmpty(message = "{last.name.is.required}")
    @NotNull(message = "{last.name.is.required}")
    private String lastName;

    /**
     * Password of the user.
     */
    @NotEmpty(message = "{password.is.required}")
    @NotNull(message = "{password.is.required}")
    private String password;
}