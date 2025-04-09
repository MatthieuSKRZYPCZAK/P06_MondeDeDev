package com.openclassrooms.mddapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO utilisé pour envoyer un simple message de réponse.
 */
@Schema(description = "Simple response DTO containing a message.")
public record ResponseDTO(

	@Schema(
			description = "Response message.",
			example = "Password updated successfully."
	)
	String message
) {
}
