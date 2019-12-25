package com.evans.mobileappws.exceptions;

public class UserServiceException extends RuntimeException {
    private static final long serialVersionUID = 3701772239574502020L;

    public UserServiceException(String message) {
        super(message);
    }
}
