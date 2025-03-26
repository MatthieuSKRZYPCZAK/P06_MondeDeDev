package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.PostCreateDTO;
import com.openclassrooms.mddapi.dto.PostDTO;
import com.openclassrooms.mddapi.mapper.PostMapper;
import com.openclassrooms.mddapi.model.CommentEntity;
import com.openclassrooms.mddapi.model.PostEntity;
import com.openclassrooms.mddapi.model.UserEntity;
import com.openclassrooms.mddapi.service.PostService;
import com.openclassrooms.mddapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.openclassrooms.mddapi.common.ApiRoutes.*;

@RestController
public class PostController {

	private final UserService userService;
	private final PostService postService;
	private final PostMapper postMapper;

	public PostController(UserService userService, PostService postService, PostMapper postMapper) {
		this.userService = userService;
		this.postService = postService;
		this.postMapper = postMapper;
	}

	@PostMapping(POST_URL)
	public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostCreateDTO postCreateDTO) {
		UserEntity author = userService.getUserAuthenticated();
		PostEntity post = postService.createPost(postCreateDTO, author);
		PostDTO response = postMapper.toPostDto(post);
		return ResponseEntity.ok(response);
	}

	@GetMapping(POST_FEED_URL)
	public ResponseEntity<List<PostDTO>> getFeed() {
		UserEntity user = userService.getUserAuthenticated();
		List<PostEntity> posts = postService.getFeedForUser(user);
		List<PostDTO> response = posts.stream()
				.map(postMapper::toPostDto)
				.toList();
		return ResponseEntity.ok(response);
	}

	@GetMapping(POST_DETAIL_URL)
	public ResponseEntity<PostDTO> getPostDetail(@PathVariable Long postId) {
		PostEntity post = postService.getPostById(postId);
		List<CommentEntity> comments = postService.getCommentsForPost(post);
		PostDTO response = postMapper.toPostDto(post, comments);
		return ResponseEntity.ok(response);
	}

}
