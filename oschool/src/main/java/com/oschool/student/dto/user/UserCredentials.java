/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */


package com.oschool.student.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Represents user credentials consisting of an email and password.
 * Used for authentication purposes.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentials {

    /**
     * Email of the user.
     */
    @Email
    @NotEmpty(message = "{email.is.required}")
    @NotNull(message = "{email.is.required}")
    private String email;

    /**
     * Password of the user.
     */
    @NotEmpty(message = "{password.is.required}")
    @NotNull(message = "{password.is.required}")
    private String password;

    /**
     * Sets the email after trimming any leading or trailing whitespace.
     * Ensures that the email value is always clean.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        if (email != null) {
            this.email = email.trim();
        }
    }
}