package com.openclassrooms.mddapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO représentant un thème (topic) auquel un utilisateur peut s'abonner.
 */
@Schema(description = "DTO representing a topic available for users to subscribe to or browse posts.")
public record TopicDTO(

		@Schema(
				description = "Technical name of the topic (used internally).",
				example = "SPRINGBOOT"
		)
		String name,

		@Schema(
				description = "Label of the topic displayed to users.",
				example = "Spring Boot"
		)
		String label,

		@Schema(
				description = "Description of the topic for users.",
				example = "Learn how to use Spring Boot to quickly build applications."
		)
		String description
) {
}
