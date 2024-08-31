/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */

package com.oschool.student.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents the response containing a JSON Web Token (JWT).
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

    /**
     * The JSON Web Token (JWT) issued to the user.
     */
    private String token;
}