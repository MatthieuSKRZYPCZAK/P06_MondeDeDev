package com.openclassrooms.mddapi.dto;

import com.openclassrooms.mddapi.model.TopicEnum;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

/**
 * DTO représentant un utilisateur de l'application.
 */
@Schema(description = "DTO representing a user of the application.")
public record UserDTO(

		@Schema(
				description = "Unique identifier of the user.",
				example = "1"
		)
		Long id,

		@Schema(
				description = "Username chosen by the user.",
				example = "johnDoe"
		)
		String username,

		@Schema(
				description = "Email address of the user.",
				example = "john.doe@example.com"
		)
		String email,

		@Schema(
				description = "Set of topics the user is subscribed to."
		)
		Set<TopicEnum> topics
) {}
