package com.depromeet.um.api.exception;

public class AccountAlreadyExistException extends RuntimeException {

    public AccountAlreadyExistException() {
        super("AccountAlreadyExistException");
    }

    public AccountAlreadyExistException(String message) {
        super(message);
    }
}
