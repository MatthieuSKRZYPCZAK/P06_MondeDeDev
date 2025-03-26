package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;

public record CommentDTO(
		Long id,
		String content,
		LocalDateTime createdAt,
		UserDTO author
) {
}
