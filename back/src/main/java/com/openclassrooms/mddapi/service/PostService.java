package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.PostCreateDTO;
import com.openclassrooms.mddapi.exception.PostNotFoundException;
import com.openclassrooms.mddapi.mapper.PostMapper;
import com.openclassrooms.mddapi.model.PostEntity;
import com.openclassrooms.mddapi.model.TopicEnum;
import com.openclassrooms.mddapi.model.UserEntity;
import com.openclassrooms.mddapi.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.openclassrooms.mddapi.common.ResponseMessages.POST_NOT_FOUND;

@Service
public class PostService {

	private final PostRepository postRepository;
	private final PostMapper postMapper;

	public PostService(PostRepository postRepository, PostMapper postMapper) {
		this.postRepository = postRepository;
		this.postMapper = postMapper;
	}

	@Transactional
	public PostEntity createPost(PostCreateDTO postCreateDTO, UserEntity author) {
		PostEntity post = postMapper.fromPostCreateDto(postCreateDTO, author);
		return postRepository.save(post);
	}

	public List<PostEntity> getFeedForUser(UserEntity user) {
		Set<TopicEnum> subscribedTopics = user.getTopics();
		return postRepository.findByTopicInOrderByCreatedAtDesc(subscribedTopics);
	}

	public PostEntity getPostById(Long postId) {
		return postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(POST_NOT_FOUND));
	}
}
