package com.jg.bookstore.domain.exception;

import lombok.Getter;

public class BaseException extends RuntimeException {

    @Getter
    private ErrorCode errorCode;

    public BaseException(final ErrorCode errorCode) {
        super(errorCode.toString());
        this.errorCode = errorCode;
    }
}
