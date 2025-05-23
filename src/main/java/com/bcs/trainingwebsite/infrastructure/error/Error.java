package com.bcs.trainingwebsite.infrastructure.error;

import lombok.Getter;

@Getter
public enum Error {
    INCORRECT_CREDENTIALS("Vale kasutajanimi või parool", 111),
    INCORRECT_TRAINING_TIME("Treener ei saa mitmes kohas samal ajal trenni anda", 999),
    EMAIL_UNAVAILABLE("Sellise emailiga konto on juba süsteemis olemas", 333),
    INVALID_REQUEST("Sellist treeningut ei eksisteeri", 404);

    private final String message;
    private final int errorCode;

    Error(String message, int errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

}
