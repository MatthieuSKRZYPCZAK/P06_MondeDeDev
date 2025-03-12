package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AuthentificationResponseDTO(
		String token,
		UserDTO user,
		String errorMessage
) {
}
