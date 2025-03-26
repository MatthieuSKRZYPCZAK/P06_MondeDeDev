package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.PostCreateDTO;
import com.openclassrooms.mddapi.dto.PostDTO;
import com.openclassrooms.mddapi.exception.TopicNotFoundException;
import com.openclassrooms.mddapi.model.PostEntity;
import com.openclassrooms.mddapi.model.TopicEnum;
import com.openclassrooms.mddapi.model.UserEntity;
import org.springframework.stereotype.Component;

import static com.openclassrooms.mddapi.common.ResponseMessages.TOPIC_NOT_FOUND;

@Component
public class PostMapper {

	private final UserMapper userMapper;
	private final TopicMapper topicMapper;

	public PostMapper(UserMapper userMapper, TopicMapper topicMapper) {
		this.userMapper = userMapper;
		this.topicMapper = topicMapper;
	}

	public PostEntity fromPostCreateDto(PostCreateDTO dto, UserEntity author) {
		TopicEnum topic;
		try {
			topic = TopicEnum.valueOf(dto.topicName().toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new TopicNotFoundException(String.format(TOPIC_NOT_FOUND, dto.topicName()));
		}

		PostEntity post = new PostEntity();
		post.setTitle(dto.title());
		post.setContent(dto.content());
		post.setAuthor(author);
		post.setTopic(topic);

		return post;
	}

	public PostDTO toPostDto(PostEntity post) {
		return new PostDTO(
				post.getId(),
				post.getTitle(),
				post.getContent(),
				topicMapper.toTopicDTO(post.getTopic()),
				userMapper.toUserDTO(post.getAuthor()),
				post.getCreatedAt()
		);
	}
}