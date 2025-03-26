package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.NotBlank;

import static com.openclassrooms.mddapi.common.ResponseMessages.CONTENT_NOT_BLANK;

public record CommentCreateDTO(
		@NotBlank(message = CONTENT_NOT_BLANK)
		String content
) {
}
