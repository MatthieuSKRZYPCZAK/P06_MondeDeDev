package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.*;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.model.UserEntity;
import com.openclassrooms.mddapi.security.JwtService;
import com.openclassrooms.mddapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static com.openclassrooms.mddapi.common.ApiRoutes.*;
import static com.openclassrooms.mddapi.common.ResponseMessages.PASSWORD_UPDATE_SUCCESS;

/**
 * Contrôleur REST pour la gestion du profil utilisateur et des abonnements aux thèmes.
 * Permet de mettre à jour le profil, changer le mot de passe, s'abonner et se désabonner de thèmes.
 * Toutes les opérations nécessitent que l'utilisateur soit authentifié via un JWT valide.
 */
@RestController
@Tag(name = "Users", description = "Endpoints for managing the user profile and topic subscriptions. Authentication required.")
public class UserController {

	private final UserService userService;
	private final UserMapper userMapper;
	private final JwtService jwtService;

	public UserController(UserService userService, UserMapper userMapper, JwtService jwtService) {
		this.userService = userService;
		this.userMapper = userMapper;
		this.jwtService = jwtService;
	}

	/**
	 * Met à jour les informations du profil de l'utilisateur authentifié.
	 *
	 * @param userUpdateDTO Les nouvelles informations du profil
	 * @param response La réponse HTTP pour rafraîchir le refresh token
	 * @return L'utilisateur mis à jour
	 */
	@PutMapping(USER_UPDATE_URL)
	@Operation(
			summary = "Update user profile",
			description = "Updates the profile information of the authenticated user.",
			security = @SecurityRequirement(name = "bearerAuth"),
			responses = {
					@ApiResponse(responseCode = "200", description = "User profile updated successfully.",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
					@ApiResponse(responseCode = "400", description = "Bad Request. Invalid user update data."),
					@ApiResponse(responseCode = "401", description = "Unauthorized. Missing or invalid JWT token.")
			}
	)
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserUpdateDTO userUpdateDTO, HttpServletResponse response) {
		UserEntity authenticatedUser = userService.getUserAuthenticated();
		UserEntity updatedUser = userService.updateUser(userUpdateDTO, authenticatedUser);

		jwtService.generateAndSetRefreshToken(updatedUser, response);

		UserDTO userDTO = userMapper.toUserDTO(updatedUser);
		return ResponseEntity.ok(userDTO);
	}

	/**
	 * Met à jour le mot de passe de l'utilisateur authentifié.
	 *
	 * @param passwordUpdateDTO Les informations nécessaires pour changer le mot de passe
	 * @param response La réponse HTTP pour rafraîchir le refresh token
	 * @return Message de succès
	 */
	@PatchMapping(USER_PASSWORD_URL)
	@Operation(
			summary = "Update user password",
			description = "Updates the password of the authenticated user. Requires the current password and the new password.",
			security = @SecurityRequirement(name = "bearerAuth"),
			responses = {
					@ApiResponse(responseCode = "200", description = "Password updated successfully.",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))),
					@ApiResponse(responseCode = "400", description = "Bad Request. Validation error."),
					@ApiResponse(responseCode = "401", description = "Unauthorized. Missing or invalid JWT token.")
			}
	)
	public ResponseEntity<ResponseDTO> updatePassword(@Valid @RequestBody PasswordUpdateDTO passwordUpdateDTO, HttpServletResponse response) {
		UserEntity authenticatedUser = userService.getUserAuthenticated();
		userService.updatePassword(passwordUpdateDTO, authenticatedUser);

		jwtService.generateAndSetRefreshToken(authenticatedUser, response);
		return ResponseEntity.ok(new ResponseDTO(PASSWORD_UPDATE_SUCCESS));
	}

	/**
	 * Permet à l'utilisateur authentifié de s'abonner à un thème.
	 *
	 * @param topicName Le nom du thème à suivre
	 * @return L'utilisateur mis à jour avec les nouveaux abonnements
	 */
	@PostMapping(USER_SUBSCRIBE_TOPIC_URL)
	@Operation(
			summary = "Subscribe to a topic",
			description = "Subscribes the authenticated user to a specific topic.",
			security = @SecurityRequirement(name = "bearerAuth"),
			responses = {
					@ApiResponse(responseCode = "200", description = "Subscribed to topic successfully.",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
					@ApiResponse(responseCode = "404", description = "Bad Request. Topic does not exist."),
					@ApiResponse(responseCode = "401", description = "Unauthorized. Missing or invalid JWT token.")
			}
	)
	public ResponseEntity<UserDTO> subscribeToTopic(@PathVariable String topicName) {
		UserEntity authenticatedUser = userService.getUserAuthenticated();
		UserEntity user = userService.subscribeToTopic(topicName, authenticatedUser);
		return ResponseEntity.ok(userMapper.toUserDTO(user));
	}

	/**
	 * Permet à l'utilisateur authentifié de se désabonner d'un thème.
	 *
	 * @param topicName Le nom du thème à quitter
	 * @return L'utilisateur mis à jour après désabonnement
	 */
	@PostMapping(USER_UNSUBSCRIBE_TOPIC_URL)
	@Operation(
			summary = "Unsubscribe from a topic",
			description = "Unsubscribes the authenticated user from a specific topic.",
			security = @SecurityRequirement(name = "bearerAuth"),
			responses = {
					@ApiResponse(responseCode = "200", description = "Unsubscribed from topic successfully.",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
					@ApiResponse(responseCode = "404", description = "Bad Request. Topic does not exist."),
					@ApiResponse(responseCode = "401", description = "Unauthorized. Missing or invalid JWT token.")
			}
	)
	public ResponseEntity<UserDTO> unsubscribeFromTopic(@PathVariable String topicName) {
		UserEntity authenticatedUser = userService.getUserAuthenticated();
		UserEntity user = userService.unsubscribeFromTopic(topicName, authenticatedUser);
		return ResponseEntity.ok(userMapper.toUserDTO(user));
	}
}
