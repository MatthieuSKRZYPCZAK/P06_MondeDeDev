package com.openclassrooms.mddapi.exception;

public class InvalidUsernameException extends RuntimeException {
	public InvalidUsernameException(String message) {
		super(message);
	}
}
