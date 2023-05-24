package com.backend.roadto900.exhandler;

import com.backend.roadto900.exception.GeneralException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {GeneralException.class})
    protected ResponseEntity handleGeneralException(GeneralException e){
        Map<String,String> result = new HashMap<>();
        result.put("ErrorMessage",e.getMessage());
        return ResponseEntity.status(e.getCode()).body(result);
    }
}
