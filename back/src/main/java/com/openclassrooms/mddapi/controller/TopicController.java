package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.openclassrooms.mddapi.common.ApiRoutes.TOPIC_URL;

/**
 * Contrôleur REST pour la gestion des thèmes (topics).
 * Permet de récupérer la liste des thèmes disponibles dans l'application.
 * L'accès à cet endpoint nécessite que l'utilisateur soit authentifié via un token JWT.
 */
@RestController
@Tag(name = "Topics", description = "Endpoints for retrieving the list of available topics. Authentication required.")
public class TopicController {

	private final TopicService topicService;

	public TopicController(TopicService topicService) {
		this.topicService = topicService;
	}

	/**
	 * Endpoint pour récupérer tous les thèmes disponibles.
	 *
	 * @return La liste des thèmes disponibles
	 */
	@GetMapping(TOPIC_URL)
	@Operation(
			summary = "Get list of topics",
			description = "Retrieves the full list of available topics. Requires authentication.",
			security = @SecurityRequirement(name = "bearerAuth"),
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Successfully retrieved list of topics.",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = TopicDTO.class))
					),
					@ApiResponse(
							responseCode = "401",
							description = "Unauthorized. Missing or invalid JWT token.",
							content = @Content(mediaType = "application/json")
					)
			}
	)
	public ResponseEntity<List<TopicDTO>> getTopics() {
		return ResponseEntity.ok(topicService.getAllTopics());
	}

}
