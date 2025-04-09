package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.CommentCreateDTO;
import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.mapper.CommentMapper;
import com.openclassrooms.mddapi.model.CommentEntity;
import com.openclassrooms.mddapi.model.UserEntity;
import com.openclassrooms.mddapi.service.CommentService;
import com.openclassrooms.mddapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.openclassrooms.mddapi.common.ApiRoutes.POST_COMMENT_URL;

/**
 * Contrôleur REST pour la gestion des commentaires.
 * Permet de créer de nouveaux commentaires sur des articles.
 * Tous les endpoints nécessitent que l'utilisateur soit authentifié via un JWT valide.
 */
@RestController
@Tag(
		name = "Comments",
		description = "Operations related to creating and managing comments on posts. Authentication is required."
)
public class CommentController {

	private final UserService userService;
	private final CommentService commentService;
	private final CommentMapper commentMapper;

	public CommentController(UserService userService, CommentService commentService, CommentMapper commentMapper) {
		this.userService = userService;
		this.commentService = commentService;
		this.commentMapper = commentMapper;
	}

	/**
	 * Endpoint pour créer un nouveau commentaire sur un article spécifique.
	 * Requiert un JWT valide.
	 *
	 * @param postId L'identifiant de l'article auquel le commentaire est rattaché
	 * @param commentCreateDTO Les données du commentaire à créer
	 * @return Le commentaire créé
	 */
	@PostMapping(POST_COMMENT_URL)
	@Operation(
			summary = "Create a comment",
			description = "Creates a new comment on a specific post.\n"
					+ "Requires the user to be authenticated (JWT access token).",
			security = @SecurityRequirement(name = "bearerAuth"),
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Comment successfully created.",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDTO.class))
					),
					@ApiResponse(
							responseCode = "400",
							description = "Bad Request. Invalid comment content or missing fields.",
							content = @Content(mediaType = "application/json")
					),
					@ApiResponse(
							responseCode = "401",
							description = "Unauthorized. Missing or invalid JWT token.",
							content = @Content(mediaType = "application/json")
					),
					@ApiResponse(
							responseCode = "404",
							description = "Post not found. Cannot add a comment to a non-existing post.",
							content = @Content(mediaType = "application/json")
					)
			}
	)
	public ResponseEntity<CommentDTO> createComment(@PathVariable Long postId, @Valid @RequestBody CommentCreateDTO commentCreateDTO) {
		UserEntity author = userService.getUserAuthenticated();
		CommentEntity comment = commentService.createComment(postId, commentCreateDTO, author);
		CommentDTO response = commentMapper.toCommentDTO(comment);
		return ResponseEntity.ok(response);
	}
}
