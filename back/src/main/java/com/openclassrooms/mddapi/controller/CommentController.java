package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.CommentCreateDTO;
import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.mapper.CommentMapper;
import com.openclassrooms.mddapi.model.CommentEntity;
import com.openclassrooms.mddapi.model.UserEntity;
import com.openclassrooms.mddapi.service.CommentService;
import com.openclassrooms.mddapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.openclassrooms.mddapi.common.ApiRoutes.POST_COMMENT_URL;

@RestController
public class CommentController {

	private final UserService userService;
	private final CommentService commentService;
	private final CommentMapper commentMapper;

	public CommentController(UserService userService, CommentService commentService, CommentMapper commentMapper) {
		this.userService = userService;
		this.commentService = commentService;
		this.commentMapper = commentMapper;
	}

	@PostMapping(POST_COMMENT_URL)
	public ResponseEntity<CommentDTO> createComment(@PathVariable Long postId, @Valid @RequestBody CommentCreateDTO commentCreateDTO) {
		UserEntity author = userService.getUserAuthenticated();
		CommentEntity comment = commentService.createComment(postId, commentCreateDTO, author);
		CommentDTO response = commentMapper.toCommentDTO(comment);
		return ResponseEntity.ok(response);
	}
}
