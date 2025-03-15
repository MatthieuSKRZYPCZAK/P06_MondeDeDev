package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import static com.openclassrooms.mddapi.common.ResponseMessages.*;

public record RegistrationDTO(
		@NotBlank(message = EMAIL_NOT_BLANK)
		@Email(message = INVALID_EMAIL)
		String email,

		@NotBlank(message = USERNAME_NOT_BLANK)
		String username,

		@NotBlank(message = PASSWORD_NOT_BLANK)
		String password
) {}
