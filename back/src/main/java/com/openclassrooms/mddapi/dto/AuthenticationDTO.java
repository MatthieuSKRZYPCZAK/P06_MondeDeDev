package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.NotBlank;

import static com.openclassrooms.mddapi.common.ResponseMessages.IDENTIFIER_NOT_BLANK;
import static com.openclassrooms.mddapi.common.ResponseMessages.PASSWORD_NOT_BLANK;

public record AuthenticationDTO(

		@NotBlank(message = IDENTIFIER_NOT_BLANK)
		String identifier,

		@NotBlank(message = PASSWORD_NOT_BLANK)
		String password
){}
