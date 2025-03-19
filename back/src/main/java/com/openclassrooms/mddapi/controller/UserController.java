package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.PasswordUpdateDTO;
import com.openclassrooms.mddapi.dto.ResponseDTO;
import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.model.UserEntity;
import com.openclassrooms.mddapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static com.openclassrooms.mddapi.common.ApiRoutes.*;
import static com.openclassrooms.mddapi.common.ResponseMessages.PASSWORD_UPDATE_SUCCESS;

@RestController
public class UserController {

	private final UserService userService;
	private final UserMapper userMapper;

	public UserController(UserService userService, UserMapper userMapper) {
		this.userService = userService;
		this.userMapper = userMapper;
	}

	@PutMapping(USER_UPDATE_URL)
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO) {
		UserEntity authenticatedUser = userService.getUserAuthenticated();
		UserEntity updatedUser = userService.updateUser(userDTO, authenticatedUser.getId());
		return ResponseEntity.ok(userMapper.userEntityToUserDTO(updatedUser));
	}

	@PatchMapping(USER_PASSWORD_URL)
	public ResponseEntity<ResponseDTO> updatePassword(@Valid @RequestBody PasswordUpdateDTO passwordUpdateDTO) {
		UserEntity authenticatedUser = userService.getUserAuthenticated();
		userService.updatePassword(passwordUpdateDTO, authenticatedUser);
		return ResponseEntity.ok(new ResponseDTO(PASSWORD_UPDATE_SUCCESS));
	}

	@PostMapping(USER_SUBSCRIBE_TOPIC_URL)
	public ResponseEntity<UserDTO> subscribeToTopic(@PathVariable Long topicId) {
		UserEntity authenticatedUser = userService.getUserAuthenticated();
		UserEntity user = userService.subscribeToTopic(topicId, authenticatedUser);
		return ResponseEntity.ok(userMapper.userEntityToUserDTO(user));
	}

	@PostMapping(USER_UNSUBSCRIBE_TOPIC_URL)
	public ResponseEntity<UserDTO> unsubscribeFromTopic(@PathVariable Long topicId) {
		UserEntity authenticatedUser = userService.getUserAuthenticated();
		UserEntity user = userService.unsubscribeFromTopic(topicId, authenticatedUser);
		return ResponseEntity.ok(userMapper.userEntityToUserDTO(user));
	}
}
