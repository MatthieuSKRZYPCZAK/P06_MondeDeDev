package com.openclassrooms.mddapi.dto;

public record AuthentificationResponseDTO(
		String token,
		UserDTO user
) {}
