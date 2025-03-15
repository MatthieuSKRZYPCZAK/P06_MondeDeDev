package com.openclassrooms.mddapi.dto;

public record AuthenticationResponseDTO(
		String token,
		UserDTO user
) {}
