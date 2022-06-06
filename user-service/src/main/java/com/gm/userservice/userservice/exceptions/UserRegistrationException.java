package com.gm.userservice.userservice.exceptions;

import javax.validation.constraints.Email;

public class UserRegistrationException extends Exception {
    public UserRegistrationException(String message) {
        super(message);
    }
}
