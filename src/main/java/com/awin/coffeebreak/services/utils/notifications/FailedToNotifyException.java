package com.awin.coffeebreak.services.utils.notifications;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_GATEWAY)
public class FailedToNotifyException extends RuntimeException {
    public FailedToNotifyException(String message, Throwable cause) {
        super(message,cause);
    }

    public FailedToNotifyException(String message) {
        super(message);
    }
}
