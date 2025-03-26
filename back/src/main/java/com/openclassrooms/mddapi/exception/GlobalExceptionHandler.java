package com.openclassrooms.mddapi.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Optional;

import static com.openclassrooms.mddapi.common.ResponseMessages.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * Gestion des exceptions générales (non prévues)
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGlobalException(Exception e) {
		logger.error(INTERNAL_SERVER_ERROR, e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(INTERNAL_SERVER_ERROR));
	}

	/**
	 * Gestion des erreurs d'unicité (email déjà existant)
	 */
	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {
		logger.info(e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
	}

	/**
	 * Gestion des erreurs d'unicité (username déjà existant)
	 */
	@ExceptionHandler(UsernameAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException e) {
		logger.info(e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
	}

	/**
	 * Gestion des erreurs de mot de passe invalide
	 */
	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<ErrorResponse> handleInvalidPasswordException(InvalidPasswordException e) {
		logger.info(e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
	}

	/**
	 * Gestion des erreurs de nom utilisateur
	 */
	@ExceptionHandler(InvalidUsernameException.class)
	public ResponseEntity<ErrorResponse> handleInvalidUsernameException(InvalidUsernameException e) {
		logger.info(e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
	}

	/**
	 * Gestion des erreurs d'utilisateur non trouvé
	 */
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
		logger.info(e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
	}

	/**
	 * Gestion des erreurs d'authentification
	 */
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException e) {
		logger.info(e.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(e.getMessage()));
	}

	/**
	 * Gestion des erreurs d'authentification
	 */
	@ExceptionHandler(AuthenticationFailedException.class)
	public ResponseEntity<ErrorResponse> handleAuthenticationFailedException(AuthenticationFailedException e) {
		logger.info(e.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(e.getMessage()));
	}

	/**
	 * Gestion des erreurs de validation des requêtes (@Valid)
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		logger.info(e.getMessage());
		String errorMessage = e.getBindingResult().getFieldErrors().stream()
				.map(fieldError -> Optional.ofNullable(fieldError.getDefaultMessage()).orElse(INVALID_REQUEST))
				.findFirst()
				.orElse(INVALID_REQUEST);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errorMessage));
	}

	/**
	 * Gestion des erreurs d'intégrité des données (ex: email unique en base)
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
		logger.error("Integrity violation: {}", e.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new ErrorResponse(DATA_INTEGRITY_VIOLATION));
	}

	/**
	 * Gestion des erreurs liées à la connexion à la base de données
	 */
	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<ErrorResponse> handleDataAccessException(DataAccessException e) {
		logger.error("Database access error: {}", e.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ErrorResponse(DATABASE_ERROR));
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
		logger.info(e.getMessage());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(e.getMessage()));
	}

	@ExceptionHandler(TopicNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleTopicNotFoundException(TopicNotFoundException e) {
		logger.info(e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
		logger.info(e.getMessage());
		String errorMessage = INVALID_PARAMETER.replace("{param}", e.getName());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errorMessage));
	}

	@ExceptionHandler(InvalidJwtException.class)
	public ResponseEntity<ErrorResponse> handleInvalidJwtException(InvalidJwtException e) {
		logger.info(e.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(e.getMessage()));
	}

	@ExceptionHandler(RefreshTokenException.class)
	public ResponseEntity<ErrorResponse> handleRefreshTokenException(RefreshTokenException e) {
		logger.info(e.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(e.getMessage()));
	}

	@ExceptionHandler(PostNotFoundException.class)
	public ResponseEntity<ErrorResponse> handlePostNotFoundException(PostNotFoundException e) {
		logger.info(e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
	}
}