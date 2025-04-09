package com.openclassrooms.mddapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO utilisé pour la réponse d'authentification.
 * Contient le token d'accès JWT et les informations de l'utilisateur.
 */
@Schema(description = "DTO representing the response after user authentication. Contains JWT access token and user details.")
public record AuthenticationResponseDTO(
		@Schema(
				description = "JWT access token to be used in the Authorization header.",
				example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
		)
		String token,

		@Schema(
				description = "Authenticated user details."
		)
		UserDTO user
) {}
