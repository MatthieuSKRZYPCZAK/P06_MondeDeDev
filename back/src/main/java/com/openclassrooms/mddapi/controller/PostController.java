package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.PostCreateDTO;
import com.openclassrooms.mddapi.dto.PostDTO;
import com.openclassrooms.mddapi.mapper.PostMapper;
import com.openclassrooms.mddapi.model.CommentEntity;
import com.openclassrooms.mddapi.model.PostEntity;
import com.openclassrooms.mddapi.model.UserEntity;
import com.openclassrooms.mddapi.service.PostService;
import com.openclassrooms.mddapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.openclassrooms.mddapi.common.ApiRoutes.*;

/**
 * Contrôleur REST pour la gestion des articles (posts).
 * Permet de créer un article, récupérer le fil d'actualités personnalisé de l'utilisateur, et obtenir le détail d'un article avec ses commentaires.
 * Tous les endpoints nécessitent que l'utilisateur soit authentifié via un token JWT.
 */
@RestController
@Tag(name = "Posts", description = "Endpoints for creating posts, retrieving the user feed, and getting post details. Authentication required.")
public class PostController {

	private final UserService userService;
	private final PostService postService;
	private final PostMapper postMapper;

	public PostController(UserService userService, PostService postService, PostMapper postMapper) {
		this.userService = userService;
		this.postService = postService;
		this.postMapper = postMapper;
	}

	/**
	 * Endpoint pour créer un nouvel article (post).
	 *
	 * @param postCreateDTO Les informations du post à créer
	 * @return L'article créé
	 */
	@PostMapping(POST_URL)
	@Operation(
			summary = "Create a post",
			description = "Creates a new post. Requires the user to be authenticated.",
			security = @SecurityRequirement(name = "bearerAuth"),
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Post successfully created.",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostDTO.class))
					),
					@ApiResponse(
							responseCode = "400",
							description = "Bad Request. Invalid post data.",
							content = @Content(mediaType = "application/json")
					),
					@ApiResponse(
							responseCode = "401",
							description = "Unauthorized. Missing or invalid JWT token.",
							content = @Content(mediaType = "application/json")
					)
			}
	)
	public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostCreateDTO postCreateDTO) {
		UserEntity author = userService.getUserAuthenticated();
		PostEntity post = postService.createPost(postCreateDTO, author);
		PostDTO response = postMapper.toPostDto(post);
		return ResponseEntity.ok(response);
	}

	/**
	 * Endpoint pour récupérer le fil d'actualités personnalisé de l'utilisateur connecté.
	 *
	 * @return La liste des articles du fil d'actualités
	 */
	@GetMapping(POST_FEED_URL)
	@Operation(
			summary = "Get user feed",
			description = "Retrieves the personalized feed for the authenticated user based on their subscriptions.",
			security = @SecurityRequirement(name = "bearerAuth"),
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Feed successfully retrieved.",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostDTO.class))
					),
					@ApiResponse(
							responseCode = "401",
							description = "Unauthorized. Missing or invalid JWT token.",
							content = @Content(mediaType = "application/json")
					)
			}
	)
	public ResponseEntity<List<PostDTO>> getFeed() {
		UserEntity user = userService.getUserAuthenticated();
		List<PostEntity> posts = postService.getFeedForUser(user);
		List<PostDTO> response = posts.stream()
				.map(postMapper::toPostDto)
				.toList();
		return ResponseEntity.ok(response);
	}

	/**
	 * Endpoint pour obtenir le détail d'un article, incluant ses commentaires.
	 *
	 * @param postId L'identifiant de l'article
	 * @return L'article et ses commentaires
	 */
	@GetMapping(POST_DETAIL_URL)
	@Operation(
			summary = "Get post details",
			description = "Retrieves detailed information about a specific post, including all its comments. Requires authentication.",
			security = @SecurityRequirement(name = "bearerAuth"),
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Post details successfully retrieved.",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostDTO.class))
					),
					@ApiResponse(
							responseCode = "401",
							description = "Unauthorized. Missing or invalid JWT token.",
							content = @Content(mediaType = "application/json")
					),
					@ApiResponse(
							responseCode = "404",
							description = "Post not found.",
							content = @Content(mediaType = "application/json")
					)
			}
	)
	public ResponseEntity<PostDTO> getPostDetail(@PathVariable Long postId) {
		PostEntity post = postService.getPostById(postId);
		List<CommentEntity> comments = postService.getCommentsForPost(post);
		PostDTO response = postMapper.toPostDto(post, comments);
		return ResponseEntity.ok(response);
	}

}
