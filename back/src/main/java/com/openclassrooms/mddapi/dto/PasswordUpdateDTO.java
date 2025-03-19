package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.NotBlank;

import static com.openclassrooms.mddapi.common.ResponseMessages.*;

public record PasswordUpdateDTO(
		@NotBlank(message=OLD_PASSWORD_NOT_BLANK)
		String oldPassword,

		@NotBlank(message=NEW_PASSWORD_NOT_BLANK)
		String newPassword
) {
}
