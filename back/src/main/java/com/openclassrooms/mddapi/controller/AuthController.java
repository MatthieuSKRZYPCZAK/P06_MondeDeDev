package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.AuthenticationDTO;
import com.openclassrooms.mddapi.dto.AuthenticationResponseDTO;
import com.openclassrooms.mddapi.dto.RegistrationDTO;
import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.exception.AuthenticationFailedException;
import com.openclassrooms.mddapi.exception.InvalidJwtException;
import com.openclassrooms.mddapi.exception.RefreshTokenException;
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
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.openclassrooms.mddapi.common.ApiRoutes.*;
import static com.openclassrooms.mddapi.common.ResponseMessages.*;

/**
 * Contrôleur REST responsable de l'authentification des utilisateurs.
 * Gère la connexion, l'inscription, la récupération de l'utilisateur connecté et le rafraîchissement du token JWT.
 */
@RestController
@Tag(name = "Authentication", description = "Endpoints for user authentication and management")
public class AuthController {

	private final JwtService jwtService;
	private final UserService userService;
	private final UserMapper userMapper;

	@Autowired
	public AuthController(JwtService jwtService, UserService userService, UserMapper userMapper) {
		this.jwtService = jwtService;
		this.userService = userService;
		this.userMapper = userMapper;
	}

	/**
	 * Endpoint de connexion (login).
	 * Authentifie l'utilisateur et retourne un access token JWT ainsi que ses informations.
	 *
	 * @param request Les identifiants de connexion de l'utilisateur (email ou username + mot de passe)
	 * @param response La réponse HTTP utilisée pour stocker le refresh token en cookie sécurisé
	 * @return L'access token JWT et les informations de l'utilisateur
	 */
	@PostMapping(LOGIN_URL)
	@Operation(
			summary = "User login",
			description = """
					Authenticates a user based on identifier (email or username) and password.
					Returns a JWT access token in the response body and a refresh token stored in a secure HttpOnly cookie.
					The access token must be used for subsequent authenticated requests.""",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Successful authentication. Returns a JWT token and user details.",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponseDTO.class))
					),
					@ApiResponse(
							responseCode = "401",
							description = "Unauthorized. Invalid credentials (wrong identifier or password).",
							content = @Content(mediaType = "application/json")
					)
			}
	)
	public ResponseEntity<AuthenticationResponseDTO> login(@Valid @RequestBody AuthenticationDTO request, HttpServletResponse response)  {

		Authentication authentication = userService.authenticate(request.identifier(), request.password());
		if(!authentication.isAuthenticated()) {
			throw new AuthenticationFailedException(AUTHENTICATION_FAILED);
		}

		UserEntity userEntity = userService.getUserAuthenticated();
		UserDTO userDTO = userMapper.toUserDTO(userEntity);
		String token = jwtService.generateToken(userEntity);

		// Stocke le refresh token en cookie sécurisé
		jwtService.generateAndSetRefreshToken(userEntity, response);

		return ResponseEntity.ok(new AuthenticationResponseDTO(token, userDTO));
	}

	/**
	 * Endpoint d'inscription (register).
	 * Crée un nouvel utilisateur, l'authentifie immédiatement et retourne son JWT et ses informations.
	 *
	 * @param request Les informations d'inscription de l'utilisateur (email, username, mot de passe)
	 * @param response La réponse HTTP utilisée pour stocker le refresh token en cookie sécurisé
	 * @return L'access token JWT et les informations de l'utilisateur
	 */
	@PostMapping(REGISTER_URL)
	@Operation(
			summary = "User registration",
			description = """
					Registers a new user by providing required registration details.
					After successful registration, the user is automatically authenticated.
					Returns a JWT access token in the response body and stores a refresh token in a secure HttpOnly cookie.""",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Successful registration and authentication. Returns a JWT token and user details.",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponseDTO.class))
					),
					@ApiResponse(
							responseCode = "400",
							description = "Bad Request. Password does not meet the required format or validation failed.",
							content = @Content(mediaType = "application/json")
					),
					@ApiResponse(
							responseCode = "409",
							description = "Conflict. The provided email or username already exists.",
							content = @Content(mediaType = "application/json")
					),
					@ApiResponse(
							responseCode = "401",
							description = "Unauthorized. Registration succeeded but automatic authentication failed.",
							content = @Content(mediaType = "application/json")
					)
			}
	)
	public ResponseEntity<AuthenticationResponseDTO> register(@Valid @RequestBody RegistrationDTO request, HttpServletResponse response) {
		UserEntity savedUser = userService.register(request);

		Authentication authentication = userService.authenticate(savedUser.getEmail(), request.password());
		if(!authentication.isAuthenticated()) {
			throw new AuthenticationFailedException(AUTHENTICATION_FAILED);
		}

		String token = jwtService.generateToken(savedUser);
		// Stocke le refresh token en cookie sécurisé
		jwtService.generateAndSetRefreshToken(savedUser, response);


		UserDTO userDTO = userMapper.toUserDTO(savedUser);
		return ResponseEntity.ok(new AuthenticationResponseDTO(token, userDTO));
	}

	/**
	 * Endpoint pour récupérer l'utilisateur actuellement authentifié.
	 *
	 * @return Les informations de l'utilisateur authentifié
	 */
	@GetMapping(ME_URL)
	@Operation(
			summary = "Get authenticated user",
			description = "Retrieves the details of the currently authenticated user.\n"
					+ "Requires a valid JWT access token in the Authorization header.",
			security = @SecurityRequirement(name = "bearerAuth"),
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Successfully retrieved the authenticated user's details.",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))
					),
					@ApiResponse(
							responseCode = "401",
							description = "Unauthorized. Missing, invalid or expired JWT token.",
							content = @Content(mediaType = "application/json")
					)
			}
	)
	public ResponseEntity<UserDTO> getUserAuthenticated() {
		UserEntity user = userService.getUserAuthenticated();
		UserDTO userDTO = userMapper.toUserDTO(user);
		return ResponseEntity.ok(userDTO);
	}

	/**
	 * Endpoint pour rafraîchir le JWT d'un utilisateur à partir du refresh token stocké dans un cookie.
	 *
	 * @param request La requête HTTP contenant le cookie du refresh token
	 * @param response La réponse HTTP utilisée pour stocker un nouveau refresh token
	 * @return Un nouvel access token JWT et les informations de l'utilisateur
	 */
	@PostMapping(JWT_REFRESH_URL)
	@Operation(
			summary = "Refresh JWT token",
			description = """
					Refreshes the JWT access token using the refresh token stored in a secure HttpOnly cookie.
					If the refresh token is valid, a new access token and refresh token are generated.
					Requires the presence of a valid 'refreshToken' cookie.""",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Successfully refreshed the JWT access token and generated a new refresh token.",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponseDTO.class))
					),
					@ApiResponse(
							responseCode = "401",
							description = "Unauthorized. Missing, invalid, or expired refresh token.",
							content = @Content(mediaType = "application/json")
					)
			}
	)
	public ResponseEntity<AuthenticationResponseDTO> refreshToken(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			jwtService.clearRefreshToken(response);
			throw new RefreshTokenException(REFRESH_TOKEN_NOT_FOUND);
		}

		String refreshToken = null;
		for (Cookie cookie : cookies) {
			if ("refreshToken".equals(cookie.getName())) {
				refreshToken = cookie.getValue();
				break;
			}
		}

		if (refreshToken == null) {
			jwtService.clearRefreshToken(response);
			throw new RefreshTokenException(INVALID_JWT);
		}

		Long userId;
		try {
			// Vérifier et extraire l'email depuis le refresh token
			 userId = jwtService.extractUserIdFromToken(refreshToken);
		} catch (InvalidJwtException e) {
			jwtService.clearRefreshToken(response);
			throw new RefreshTokenException(INVALID_JWT);
		}

		UserEntity user = userService.findByUserId(userId);

		// Génère un nouvel access token et refresh token
		String newAccessToken = jwtService.generateToken(user);
		jwtService.generateAndSetRefreshToken(user, response);

		return ResponseEntity.ok(new AuthenticationResponseDTO(newAccessToken, userMapper.toUserDTO(user)));
	}
}