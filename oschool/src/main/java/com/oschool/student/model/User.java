/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */

package com.oschool.student.model;

import com.oschool.student.enumeration.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Represents a user in the system.
 * This entity is mapped to the "app_user" table in the database.
 * It includes details such as the user ID, email, role, password,
 * and associated student, as well as various account status flags.
 */
@Entity
@Table(name = "app_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    /**
     * Unique identifier for the user, automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "USER_SEQ", allocationSize = 1)
    private Long id;

    /**
     * Email of the user, which must be unique and cannot be null.
     */
    @NotNull
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    /**
     * Role of the user, represented as an enum.
     */
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * Password of the user.
     */
    @Column(name = "password")
    private String password;

    /**
     * One-to-One relationship with the Student entity.
     * Represents the student details associated with the user.
     */
    @OneToOne(mappedBy = "user")
    private Student student;

    /**
     * Flag indicating if the user's account is non-expired.
     */
    private boolean isAccountNonExpired;

    /**
     * Flag indicating if the user's account is non-locked.
     */
    private boolean isAccountNonLocked;

    /**
     * Flag indicating if the user's credentials are non-expired.
     */
    private boolean isCredentialsNonExpired;

    /**
     * Flag indicating if the user's account is enabled.
     */
    private boolean isEnabled;

    /**
     * Returns the authorities granted to the user based on their role.
     */
    @Override
    public Set<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority(role.toString()));
    }

    /**
     * Returns the username of the user, which is the email.
     */
    @Override
    public String getUsername() {
        return email;
    }
}