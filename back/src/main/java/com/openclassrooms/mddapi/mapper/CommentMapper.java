package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.CommentCreateDTO;
import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.model.CommentEntity;
import com.openclassrooms.mddapi.model.PostEntity;
import com.openclassrooms.mddapi.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

	private final UserMapper userMapper;


	public CommentMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public CommentDTO toCommentDTO(CommentEntity comment) {
		return new CommentDTO(
				comment.getId(),
				comment.getContent(),
				comment.getCreatedAt(),
				userMapper.toUserDTO(comment.getAuthor())
		);
	}

	public CommentEntity fromCreateDTO(CommentCreateDTO dto, UserEntity author, PostEntity post) {
		CommentEntity comment = new CommentEntity();
		comment.setContent(dto.content());
		comment.setAuthor(author);
		comment.setPost(post);
		return comment;
	}
}
