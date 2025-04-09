package com.openclassrooms.mddapi.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import static com.openclassrooms.mddapi.common.ResponseMessages.*;

/**
 * DTO utilisé pour mettre à jour le profil de l'utilisateur (nom d'utilisateur et e-mail).
 */
@Schema(description = "DTO used to update a user's profile information (username and email).")
public record UserUpdateDTO(

		@Schema(
				description = "Updated username of the user.",
				example = "newJohnDoe"
		)
		@NotBlank(message = USERNAME_NOT_BLANK)
		String username,

		@Schema(
				description = "Updated email address of the user. Must be valid.",
				example = "new.john.doe@example.com"
		)
		@NotBlank(message = EMAIL_NOT_BLANK)
		@Email(message = INVALID_EMAIL)
		String email
) {
}
