package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.service.TopicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.openclassrooms.mddapi.common.ApiRoutes.TOPIC_URL;

@RestController
public class TopicController {

	private final TopicService topicService;

	public TopicController(TopicService topicService) {
		this.topicService = topicService;
	}

	@GetMapping(TOPIC_URL)
	public ResponseEntity<List<TopicDTO>> getTopics() {
		return ResponseEntity.ok(topicService.getAllTopics());
	}

}
