package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.AuthenticationDTO;
import com.openclassrooms.mddapi.dto.AuthenticationResponseDTO;
import com.openclassrooms.mddapi.dto.RegistrationDTO;
import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.exception.AuthenticationFailedException;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.model.UserEntity;
import com.openclassrooms.mddapi.security.JwtService;
import com.openclassrooms.mddapi.service.UserService;
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
	public ResponseEntity<AuthenticationResponseDTO> login(@Valid @RequestBody AuthenticationDTO request)  {

		Authentication authentication = userService.authenticate(request.identifier(), request.password());
		if(!authentication.isAuthenticated()) {
			throw new AuthenticationFailedException(AUTHENTICATION_FAILED);
		}

		UserEntity userEntity = userService.getUserAuthenticated();
		UserDTO userDTO = userMapper.userEntityToUserDTO(userEntity);
		String token = jwtService.generateToken(authentication);

		return ResponseEntity.ok(new AuthenticationResponseDTO(token, userDTO));
	}

	@PostMapping(REGISTER_URL)
	public ResponseEntity<AuthenticationResponseDTO> register(@Valid @RequestBody RegistrationDTO request) {
		UserEntity savedUser = userService.register(request);

		Authentication authentication = userService.authenticate(savedUser.getEmail(), request.password());
		if(!authentication.isAuthenticated()) {
			throw new AuthenticationFailedException(AUTHENTICATION_FAILED);
		}

		String token = jwtService.generateToken(authentication);
		if(token == null || token.isEmpty()) {
			throw new RuntimeException(INTERNAL_SERVER_ERROR);
		}

		UserDTO userDTO = userMapper.userEntityToUserDTO(savedUser);
		return ResponseEntity.ok(new AuthenticationResponseDTO(token, userDTO));
	}

	@GetMapping(ME_URL)
	public ResponseEntity<UserDTO> getUserAuthenticated() {
		UserEntity user = userService.getUserAuthenticated();
		UserDTO userDTO = userMapper.userEntityToUserDTO(user);
		return ResponseEntity.ok(userDTO);
	}
}