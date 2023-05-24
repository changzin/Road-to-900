package com.backend.roadto900.exception;

import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException{
    private int code;
    public GeneralException() {
    }
    public GeneralException(String message, int code) {
        super(message);
        this.code = code;
    }
}
