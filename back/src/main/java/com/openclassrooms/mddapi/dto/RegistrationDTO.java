package com.openclassrooms.mddapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import static com.openclassrooms.mddapi.common.ResponseMessages.*;

/**
 * DTO utilisé pour l'inscription d'un nouvel utilisateur.
 */
@Schema(description = "DTO used for registering a new user. Requires email, username, and password.")
public record RegistrationDTO(

		@Schema(
				description = "Email address of the user. Must be unique and valid.",
				example = "john.doe@example.com"
		)
		@NotBlank(message = EMAIL_NOT_BLANK)
		@Email(message = INVALID_EMAIL)
		String email,

		@Schema(
				description = "Username chosen by the user. Must be unique.",
				example = "johnDoe"
		)
		@NotBlank(message = USERNAME_NOT_BLANK)
		String username,

		@Schema(
				description = "Password chosen by the user. Must respect security rules.",
				example = "Secur3P@ssword!"
		)
		@NotBlank(message = PASSWORD_NOT_BLANK)
		String password
) {}
