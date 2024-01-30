package com.encore.board.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class CommonException {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String,Object>> entityNotFoundExceptionHandler(EntityNotFoundException e) {
        log.error("Handler entity not found "+e.getMessage());
        return this.errorResponseMessage(HttpStatus.NOT_FOUND, e.getMessage());
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public void illegalArgumentExceptionHandler(IllegalArgumentException e) {
        log.error("IllegalArgumentException "+e.getMessage());
    }

    public static ResponseEntity<Map<String, Object>> errorResponseMessage(HttpStatus status, String error) {
        Map<String, Object> body = new HashMap<>();
        body.put("status",Integer.toString(status.value()));
        body.put("error",error);
        return new ResponseEntity<>(body, status);
    }
}
