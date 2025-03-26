package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.model.TopicEnum;
import org.springframework.stereotype.Component;

@Component
public class TopicMapper {

	public TopicDTO toTopicDTO(TopicEnum topic) {
		return new TopicDTO(
				topic.name(),
				topic.getLabel(),
				topic.getDescription()
		);
	}
}
