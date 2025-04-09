package com.openclassrooms.mddapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import static com.openclassrooms.mddapi.common.ResponseMessages.CONTENT_NOT_BLANK;

/**
 * DTO utilisé pour créer un commentaire.
 */
@Schema(description = "DTO used to create a new comment on a post.")
public record CommentCreateDTO(

		@Schema(
				description = "Content of the comment.",
				example = "Great article! Thanks for sharing."
		)
		@NotBlank(message = CONTENT_NOT_BLANK)
		String content
) {
}
