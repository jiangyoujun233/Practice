package com.nora.website.exception;

/**
 * 保存或更新Admin时如果检测到登录账号重复抛出这个异常
 */
public class LoginFailedException extends RuntimeException{


    private static final long serialVersionUID = 1L;

    public LoginFailedException() {
    }

    public LoginFailedException(String message) {
        super(message);
    }

    public LoginFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginFailedException(Throwable cause) {
        super(cause);
    }

    public LoginFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
