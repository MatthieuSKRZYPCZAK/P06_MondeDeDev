package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;

public record PostDTO(
		Long id,
		String title,
		String content,
		TopicDTO topic,
		UserDTO author,
		LocalDateTime createdAt
) {
}
