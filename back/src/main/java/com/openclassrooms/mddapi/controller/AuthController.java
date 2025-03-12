package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.common.ResponseMessages;
import com.openclassrooms.mddapi.dto.AuthenticationDTO;
import com.openclassrooms.mddapi.dto.AuthentificationResponseDTO;
import com.openclassrooms.mddapi.dto.RegistrationDTO;
import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.model.UserEntity;
import com.openclassrooms.mddapi.security.JwtService;
import com.openclassrooms.mddapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.openclassrooms.mddapi.common.ApiRoutes.*;

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
	public ResponseEntity<AuthentificationResponseDTO> login(@RequestBody AuthenticationDTO request)  {

		Authentication authentication = userService.authenticate(request);
		if(authentication.isAuthenticated()) {
			UserEntity userEntity = userService.getUserAuthenticated(authentication);
			UserDTO userDTO = userMapper.userEntityToUserDTO(userEntity);
			String token = jwtService.generateToken(authentication);

			return ResponseEntity.ok(new AuthentificationResponseDTO(token, userDTO, null));
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthentificationResponseDTO(null, null, ResponseMessages.INVALID_IDENTIFIER));
	}

	@PostMapping(REGISTER_URL)
	public ResponseEntity<AuthentificationResponseDTO> register(@RequestBody RegistrationDTO request) {
		userService.register(request);

		Authentication authentication = userService.authenticate(new AuthenticationDTO(request.email(), request.password()));
		if(authentication.isAuthenticated()) {
			UserEntity userEntity = userService.getUserAuthenticated(authentication);
			UserDTO userDTO = userMapper.userEntityToUserDTO(userEntity);
			String token = jwtService.generateToken(authentication);

			return ResponseEntity.ok(new AuthentificationResponseDTO(token, userDTO, null));
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthentificationResponseDTO(null, null, ResponseMessages.INVALID_IDENTIFIER));
	}


}
