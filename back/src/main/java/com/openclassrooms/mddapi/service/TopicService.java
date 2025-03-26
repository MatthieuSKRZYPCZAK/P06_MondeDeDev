package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.model.TopicEnum;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TopicService {

	public List<TopicDTO> getAllTopics() {
		return Arrays.stream(TopicEnum.values())
				.map(topic -> new TopicDTO(
						topic.name(),
						topic.getLabel(),
						topic.getDescription()
				))
				.toList();
	}
}
