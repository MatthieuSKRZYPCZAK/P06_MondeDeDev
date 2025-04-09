package com.openclassrooms.mddapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import static com.openclassrooms.mddapi.common.ResponseMessages.*;

/**
 * DTO utilisé pour mettre à jour le mot de passe d'un utilisateur.
 */
@Schema(description = "DTO used to update a user's password.")
public record PasswordUpdateDTO(

		@Schema(
				description = "Current password of the user.",
				example = "OldP@ssword123"
		)
		@NotBlank(message=OLD_PASSWORD_NOT_BLANK)
		String oldPassword,

		@Schema(
				description = "New password that the user wants to set.",
				example = "NewP@ssword456"
		)
		@NotBlank(message=NEW_PASSWORD_NOT_BLANK)
		String newPassword
) {
}
