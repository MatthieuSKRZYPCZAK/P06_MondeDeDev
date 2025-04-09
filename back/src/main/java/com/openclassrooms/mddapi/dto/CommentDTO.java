package com.openclassrooms.mddapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "DTO representing a comment associated with a post.")
public record CommentDTO(
		@Schema(
				description = "Unique identifier of the comment.",
				example = "101"
		)
		Long id,

		@Schema(
				description = "Content of the comment.",
				example = "Very interesting article!"
		)
		String content,

		@Schema(
				description = "Date and time when the comment was created (ISO 8601 format).",
				example = "2025-04-09T13:45:30"
		)
		LocalDateTime createdAt,

		@Schema(
				description = "Details of the user who posted the comment."
		)
		UserDTO author
) {
}
