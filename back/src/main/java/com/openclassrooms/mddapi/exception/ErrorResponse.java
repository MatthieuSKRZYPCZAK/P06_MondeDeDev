package com.openclassrooms.mddapi.exception;

/**
 * DTO représentant une réponse d'erreur standard de l'API.
 */
public record ErrorResponse(
		String error
){}
