package com.nora.website.exception;

public class EditAdminAcctFailedException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public EditAdminAcctFailedException() {
    }

    public EditAdminAcctFailedException(String message) {
        super(message);
    }

    public EditAdminAcctFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public EditAdminAcctFailedException(Throwable cause) {
        super(cause);
    }

    public EditAdminAcctFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
