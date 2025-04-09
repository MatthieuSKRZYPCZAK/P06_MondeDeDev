package com.openclassrooms.mddapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import static com.openclassrooms.mddapi.common.ResponseMessages.IDENTIFIER_NOT_BLANK;
import static com.openclassrooms.mddapi.common.ResponseMessages.PASSWORD_NOT_BLANK;

/**
 * DTO utilisé pour l'authentification d'un utilisateur.
 * Contient l'identifiant (email ou username) et le mot de passe.
 */
@Schema(description = "DTO used for user authentication. Contains identifier (email or username) and password.")
public record AuthenticationDTO(
		@Schema(
				description = "User identifier. Can be an email or a username.",
				example = "john.doe@example.com"
		)
		@NotBlank(message = IDENTIFIER_NOT_BLANK)
		String identifier,

		@Schema(
				description = "User password.",
				example = "P@ssw0rd!66"
		)
		@NotBlank(message = PASSWORD_NOT_BLANK)
		String password
){}
