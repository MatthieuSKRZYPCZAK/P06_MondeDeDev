package com.openclassrooms.mddapi.common;

public class ResponseMessages {

	private ResponseMessages() {}

	// Authentication & Authorization
	public static final String INVALID_IDENTIFIER = "Invalid username or password";

	// Registration
	public static final String REGISTER_FAILED = "Registration failed";

	// User-related errors
	public static final String EMAIL_ALREADY_EXISTS = "Email already exists.";
	public static final String USERNAME_ALREADY_EXISTS = "Username already exists.";
	public static final String USER_NOT_FOUND = "User not found.";

	// Password-related messages
	public static final String INVALID_PASSWORD = "Password must contain at least 8 characters, one uppercase letter, one lowercase letter, one digit, and one special character.";

	// Generic Errors
	public static final String INTERNAL_SERVER_ERROR = "Internal server error.";
	public static final String ACCESS_DENIED = "Access denied.";
}