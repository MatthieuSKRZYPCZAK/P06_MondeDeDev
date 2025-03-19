package com.openclassrooms.mddapi.dto;

import com.openclassrooms.mddapi.model.TopicEntity;

import java.util.Set;

public record UserDTO(
		Long id,
		String username,
		String email,
		Set<TopicEntity> topics
) {}
