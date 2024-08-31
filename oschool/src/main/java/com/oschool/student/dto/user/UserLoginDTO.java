/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */


package com.oschool.student.dto.user;

import com.oschool.student.enumeration.Role;
import com.oschool.student.model.Student;
import com.oschool.student.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Data Transfer Object (DTO) for user login details.
 * Used to transfer user data during the login process.
 */
@Builder
@Data
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {

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
     * Password of the user.
     */
    private String password;

    /**
     * Associated Student entity linked to this user.
     */
    @OneToOne(mappedBy = "user")
    private Student student;

    /**
     * First name of the user.
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * Last name of the user.
     */
    @Column(name = "last_name")
    private String lastName;
}