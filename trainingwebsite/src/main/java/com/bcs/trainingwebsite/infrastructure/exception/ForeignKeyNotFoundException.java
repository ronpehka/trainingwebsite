package com.bcs.trainingwebsite.infrastructure.exception;

import lombok.Getter;

@Getter
public class ForeignKeyNotFoundException extends RuntimeException {
    private final String message;
    private final Integer errorCode;

    public ForeignKeyNotFoundException(String fieldName, Integer fieldValue) {
        super("Ei leidnud foreign keyd '" + fieldName + "' väärtusega: " + fieldValue);
        this.message = "Ei leidnud foreign keyd '" + fieldName + "' väärtusega: " + fieldValue;
        this.errorCode = 666;
    }
}
