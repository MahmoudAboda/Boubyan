/*
 * Copyright (c)  Acuanix Company
 * @Author : Mohamed Taha
 * @Date: 11/1/2023
 */

package com.oschool.student.error;



import com.oschool.student.dto.common.ResponsePayload;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Map;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;



@ControllerAdvice
@Slf4j
public class controllerAdvice{
    public ResponseEntity<ResponsePayload> error(Integer code, HttpStatus status, Exception e, String msg) {
        log.error("error message :"+e.getMessage());
        return  ResponseEntity.status(status!=null ? status.value() : code).body(
                ResponsePayload.builder()
                        .date(LocalDateTime.now())
                        .error(Map.of("errorMessage",msg))
                        .build()
        );
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<ResponsePayload> handleException(Exception e, Locale locale) {
        e.printStackTrace();
        return error(INTERNAL_SERVER_ERROR.value(),INTERNAL_SERVER_ERROR, e,
                e.getMessage());
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ResponsePayload> handleRunTimeException(RuntimeException e) {
        return error(INTERNAL_SERVER_ERROR.value(),INTERNAL_SERVER_ERROR, e,
                e.getMessage());
    }



    @ExceptionHandler({javax.validation.ConstraintViolationException.class})
    public ResponseEntity<ResponsePayload> handleConstraintViolationException(javax.validation.ConstraintViolationException e) {
        if(e.getMessage().contains("Radius limit must")){
            return error(INTERNAL_SERVER_ERROR.value(),INTERNAL_SERVER_ERROR, e, "Radius limit must be greater than 0");
        }
        return error(INTERNAL_SERVER_ERROR.value(),INTERNAL_SERVER_ERROR, e, "Invalid data");
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ResponsePayload> handSQLInjectionBindingException(MethodArgumentNotValidException e) {
        String messageTxt = null;
        try {
            messageTxt = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        }catch (Exception efd){
            efd.printStackTrace();
        }
        return error(INTERNAL_SERVER_ERROR.value(), INTERNAL_SERVER_ERROR,new Exception("Malicious data!!!"),
                messageTxt);

    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseEntity<ResponsePayload> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return error(INTERNAL_SERVER_ERROR.value(),INTERNAL_SERVER_ERROR, e,
                "some parameters is not presented!");
    }

    @ExceptionHandler({DuplicateKeyException.class})
    public ResponseEntity<ResponsePayload> handleDuplicateKeyException(DuplicateKeyException e) {
        return error(INTERNAL_SERVER_ERROR.value(),INTERNAL_SERVER_ERROR, e,
                e.getMessage());
    }

    @ExceptionHandler({ExpiredJwtException.class})
    public ResponseEntity<ResponsePayload> handleExpiredTokenException(ExpiredJwtException e) {
        return error(UNAUTHORIZED.value(),UNAUTHORIZED, e,
                e.getMessage());
    }



}
