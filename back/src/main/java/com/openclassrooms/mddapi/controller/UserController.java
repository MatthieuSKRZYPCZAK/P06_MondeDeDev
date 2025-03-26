package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.*;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.model.UserEntity;
import com.openclassrooms.mddapi.security.JwtService;
import com.openclassrooms.mddapi.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static com.openclassrooms.mddapi.common.ApiRoutes.*;
import static com.openclassrooms.mddapi.common.ResponseMessages.PASSWORD_UPDATE_SUCCESS;

@RestController
public class UserController {

	private final UserService userService;
	private final UserMapper userMapper;
	private final JwtService jwtService;

	public UserController(UserService userService, UserMapper userMapper, JwtService jwtService) {
		this.userService = userService;
		this.userMapper = userMapper;
		this.jwtService = jwtService;
	}

	@PutMapping(USER_UPDATE_URL)
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserUpdateDTO userUpdateDTO, HttpServletResponse response) {
		UserEntity authenticatedUser = userService.getUserAuthenticated();
		UserEntity updatedUser = userService.updateUser(userUpdateDTO, authenticatedUser);

		jwtService.generateAndSetRefreshToken(updatedUser, response);

		UserDTO userDTO = userMapper.toUserDTO(updatedUser);
		return ResponseEntity.ok(userDTO);
	}

	@PatchMapping(USER_PASSWORD_URL)
	public ResponseEntity<ResponseDTO> updatePassword(@Valid @RequestBody PasswordUpdateDTO passwordUpdateDTO) {
		UserEntity authenticatedUser = userService.getUserAuthenticated();
		userService.updatePassword(passwordUpdateDTO, authenticatedUser);
		return ResponseEntity.ok(new ResponseDTO(PASSWORD_UPDATE_SUCCESS));
	}

	@PostMapping(USER_SUBSCRIBE_TOPIC_URL)
	public ResponseEntity<UserDTO> subscribeToTopic(@PathVariable String topicName) {
		UserEntity authenticatedUser = userService.getUserAuthenticated();
		UserEntity user = userService.subscribeToTopic(topicName, authenticatedUser);
		return ResponseEntity.ok(userMapper.toUserDTO(user));
	}

	@PostMapping(USER_UNSUBSCRIBE_TOPIC_URL)
	public ResponseEntity<UserDTO> unsubscribeFromTopic(@PathVariable String topicName) {
		UserEntity authenticatedUser = userService.getUserAuthenticated();
		UserEntity user = userService.unsubscribeFromTopic(topicName, authenticatedUser);
		return ResponseEntity.ok(userMapper.toUserDTO(user));
	}
}
