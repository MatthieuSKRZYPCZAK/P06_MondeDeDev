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

@RestController
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

	@PostMapping(LOGIN_URL)
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

	@PostMapping(REGISTER_URL)
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

	@GetMapping(ME_URL)
	public ResponseEntity<UserDTO> getUserAuthenticated() {
		UserEntity user = userService.getUserAuthenticated();
		UserDTO userDTO = userMapper.toUserDTO(user);
		return ResponseEntity.ok(userDTO);
	}

	@PostMapping(JWT_REFRESH_URL)
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