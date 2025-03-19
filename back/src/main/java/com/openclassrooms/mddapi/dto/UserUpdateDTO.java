package com.openclassrooms.mddapi.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import static com.openclassrooms.mddapi.common.ResponseMessages.*;

public record UserUpdateDTO(
		@NotBlank(message = USERNAME_NOT_BLANK)
		String username,

		@NotBlank(message = EMAIL_NOT_BLANK)
		@Email(message = INVALID_EMAIL)
		String email
) {
}
