/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */
package com.oschool.student.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a course in the system.
 * This entity is mapped to the "course" table in the database.
 * It includes details such as the course ID, name, start and end dates,
 * and a list of students enrolled in the course.
 */

@Entity
@Table(name = "course")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    /**
     * Unique identifier for the course, automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_seq")
    @SequenceGenerator(name = "course_seq", sequenceName = "COURSE_SEQ", allocationSize = 1)
    private Long id;

    /**
     * Name of the course.
     */
    private String name;

    /**
     * Start date and time of the course.
     */
    private LocalDateTime startDate;

    /**
     * End date and time of the course.
     */
    private LocalDateTime endDate;

    /**
     * Many-to-Many relationship with the Student entity, representing students enrolled in the course.
     */
    @ManyToMany
    @JoinTable(
            name = "course_student",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students = new HashSet<>();
}