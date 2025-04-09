package com.openclassrooms.mddapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import static com.openclassrooms.mddapi.common.ResponseMessages.*;

/**
 * DTO utilisé pour créer un nouvel article (post).
 */
@Schema(description = "DTO used to create a new post. Requires title, content, and associated topic name.")
public record PostCreateDTO(

		@Schema(
				description = "Title of the post.",
				example = "Introduction to Spring Boot"
		)
		@NotBlank(message = TITLE_NOT_BLANK)
		String title,

		@Schema(
				description = "Main content of the post.",
				example = "Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications..."
		)
		@NotBlank(message = CONTENT_NOT_BLANK)
		String content,

		@Schema(
				description = "Name of the topic associated with the post.",
				example = "spring-boot"
		)
		@NotBlank(message = TOPIC_NOT_BLANK)
		String topicName
) {
}
