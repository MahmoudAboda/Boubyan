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
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a student in the system.
 * This entity is mapped to the "student" table in the database.
 * It includes details such as the student ID, email, role, user ID,
 * and associated user and courses.
 */
@Entity
@Table(name = "student")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    /**
     * Unique identifier for the student, automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq")
    @SequenceGenerator(name = "student_seq", sequenceName = "STUDENT_SEQ", allocationSize = 1)
    private Long id;

    /**
     * Email of the student, which must be unique and cannot be null.
     */
    @Column(unique = true, nullable = false)
    @NotNull
    private String email;

    /**
     * Role of the student, represented as an enum.
     */
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * User ID associated with the student, used for linking to a user.
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * One-to-One relationship with the User entity.
     * Represents the user details associated with the student.
     */
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    /**
     * First name of the student.
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * Last name of the student.
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * Many-to-Many relationship with the Course entity.
     * Represents the set of courses the student is enrolled in.
     */
    @ManyToMany(mappedBy = "students")
    private Set<Course> courses = new HashSet<>();
}
