package com.openclassrooms.mddapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO représentant un article (post).
 */
@Schema(description = "DTO representing a post published by a user, including topic, author, creation date, and comments.")
public record PostDTO(
		@Schema(
				description = "Unique identifier of the post.",
				example = "42"
		)
		Long id,

		@Schema(
				description = "Title of the post.",
				example = "Understanding Spring Boot Dependency Injection"
		)
		String title,

		@Schema(
				description = "Main content of the post.",
				example = "In this post, we'll dive deep into how dependency injection works in Spring Boot..."
		)
		String content,

		@Schema(
				description = "Topic associated with the post."
		)
		TopicDTO topic,

		@Schema(
				description = "Author of the post."
		)
		UserDTO author,

		@Schema(
				description = "Creation date and time of the post (ISO 8601 format).",
				example = "2025-04-09T14:00:00"
		)
		LocalDateTime createdAt,

		@Schema(
				description = "List of comments associated with the post."
		)
		List<CommentDTO> comments
) {
}
