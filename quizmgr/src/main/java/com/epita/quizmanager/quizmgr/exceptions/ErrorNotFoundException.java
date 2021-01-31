package com.epita.quizmanager.quizmgr.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ErrorNotFoundException extends RuntimeException {

	public ErrorNotFoundException(String message) {
        super(message);
    }
}
