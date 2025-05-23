package com.openclassrooms.mddapi.common;

/**
 * Classe utilitaire contenant l’ensemble des messages de réponse utilisés dans l’application MDD.
 */
public class ResponseMessages {

	private ResponseMessages() {}

	// Authentication & Authorization
	public static final String INVALID_IDENTIFIER = "Invalid username or password";
	public static final String IDENTIFIER_NOT_BLANK = "The identifier cannot be empty.";
	public static final String PASSWORD_NOT_BLANK = "The password is required.";
	public static final String AUTHENTICATION_FAILED = "Authentication failed. User could not be authenticated.";
	public static final String EMAIL_NOT_BLANK = "The email cannot be empty.";
	public static final String USERNAME_NOT_BLANK = "The username cannot be empty.";
	public static final String INVALID_EMAIL = "Invalid email format";
	public static final String INVALID_JWT = "Invalid or missing JWT token";
	public static final String EXPIRED_JWT = "Your session has expired. Please log in again.";
	public static final String REFRESH_TOKEN_NOT_FOUND = "No refresh token found.";

	// User-related errors
	public static final String EMAIL_ALREADY_EXISTS = "Email already exists.";
	public static final String USERNAME_ALREADY_EXISTS = "Username already exists.";
	public static final String USER_NOT_FOUND = "User not found.";
	public static final String INVALID_USERNAME = "Username must be between 3 and 20 characters, contain at least 3 letters, and must not have more than 5 consecutive digits. Only letters and numbers are allowed. Spaces and special characters are not permitted.";

	// Password-related messages
	public static final String INVALID_PASSWORD = "Password must contain at least 8 characters, one uppercase letter, one lowercase letter, one digit, and one special character.";
	public static final String INVALID_OLD_PASSWORD = "Old password is incorrect.";
	public static final String OLD_PASSWORD_NOT_BLANK = "Old password is required.";
	public static final String NEW_PASSWORD_NOT_BLANK = "New password is required.";
	public static final String NEW_PASSWORD_MUST_BE_DIFFERENT = "New password must be different from old password.";
	public static final String PASSWORD_UPDATE_SUCCESS = "Password updated successfully.";

	// Topic-related errors
	public static final String TOPIC_NOT_FOUND = "Topic not found: %s";

	// Post-related errors
	public static final String POST_NOT_FOUND = "Post not found.";

	// Validation messages
	public static final String TITLE_NOT_BLANK = "Title cannot be blank.";
	public static final String CONTENT_NOT_BLANK = "Content cannot be blank.";
	public static final String TOPIC_NOT_BLANK = "Topic is required.";

	// Database errors
	public static final String DATABASE_ERROR = "Database error. Please try again later.";
	public static final String DATA_INTEGRITY_VIOLATION = "A database integrity constraint was violated.";
	public static final String INVALID_REQUEST = "Invalid request data.";

	// Generic parameter errors
	public static final String INVALID_PARAMETER = "Invalid parameter.";

	// Generic Errors
	public static final String INTERNAL_SERVER_ERROR = "Internal server error.";

}