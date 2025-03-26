package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.NotBlank;

import static com.openclassrooms.mddapi.common.ResponseMessages.*;

public record PostCreateDTO(
		@NotBlank(message = TITLE_NOT_BLANK)
		String title,
		@NotBlank(message = CONTENT_NOT_BLANK)
		String content,
		@NotBlank(message = TOPIC_NOT_BLANK)
		String topicName
) {
}
