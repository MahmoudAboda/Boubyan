package com.oschool.student.error.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AbstractStudentException extends RuntimeException{
    public AbstractStudentException(String message) {
        super(message);
    }
}