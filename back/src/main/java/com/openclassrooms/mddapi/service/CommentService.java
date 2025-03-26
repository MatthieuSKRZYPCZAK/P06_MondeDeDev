package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.CommentCreateDTO;
import com.openclassrooms.mddapi.exception.PostNotFoundException;
import com.openclassrooms.mddapi.mapper.CommentMapper;
import com.openclassrooms.mddapi.model.CommentEntity;
import com.openclassrooms.mddapi.model.PostEntity;
import com.openclassrooms.mddapi.model.UserEntity;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.openclassrooms.mddapi.common.ResponseMessages.POST_NOT_FOUND;

@Service
public class CommentService {

	private final PostRepository postRepository;
	private final CommentMapper commentMapper;
	private final CommentRepository commentRepository;

	public CommentService(PostRepository postRepository, CommentMapper commentMapper, CommentRepository commentRepository) {
		this.postRepository = postRepository;
		this.commentMapper = commentMapper;
		this.commentRepository = commentRepository;
	}

	@Transactional
	public CommentEntity createComment(Long postId, CommentCreateDTO commentCreateDTO, UserEntity author) {
		PostEntity post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(POST_NOT_FOUND));
		CommentEntity comment = commentMapper.fromCreateDTO(commentCreateDTO, author, post);
		return commentRepository.save(comment);
	}
}
