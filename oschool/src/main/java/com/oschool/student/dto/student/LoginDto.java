/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */
package com.oschool.student.dto.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


/**
 * Data Transfer Object (DTO) for user login.
 * Used to transfer login credentials between layers.
 */
@Builder
@Data
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    /**
     * Email address of the user, used for authentication.
     */
    @Email
    @NotEmpty(message = "{email.is.required}")
    @NotNull(message = "{email.is.required}")
    private String email;

    /**
     * Password of the user, used for authentication.
     */
    @NotEmpty(message = "{password.is.required}")
    @NotNull(message = "{password.is.required}")
    private String password;
}