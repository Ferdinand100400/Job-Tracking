package ru.vk.education.job.web.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFindUserByName.class)
    public ResponseEntity<String> handle(NotFindUserByName exception) {
        return ResponseEntity.status(404)
                .body("не найден пользователь с именем: " + exception.getName());
    }
}
