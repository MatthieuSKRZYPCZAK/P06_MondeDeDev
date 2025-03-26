package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.model.TopicEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static com.openclassrooms.mddapi.common.ApiRoutes.TOPIC_URL;

@RestController
public class TopicController {


	@GetMapping(TOPIC_URL)
	public List<TopicDTO> getTopics() {
		return Arrays.stream(TopicEnum.values())
				.map(topic -> new TopicDTO(
						topic.name(),
						topic.getLabel(),
						topic.getDescription()
				))
				.toList();
	}


}
