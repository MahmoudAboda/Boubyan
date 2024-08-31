package com.oschool.student.error.exceptions;

public class AbstractTokenExpiredException extends RuntimeException{
    public AbstractTokenExpiredException(String message) {
        super(message);
    }
}