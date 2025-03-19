package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.model.UserEntity;
import com.openclassrooms.mddapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;


import static com.openclassrooms.mddapi.common.ApiRoutes.*;
import static com.openclassrooms.mddapi.common.ResponseMessages.ACCESS_DENIED;

@RestController
public class UserController {

	private final UserService userService;
	private final UserMapper userMapper;

	public UserController(UserService userService, UserMapper userMapper) {
		this.userService = userService;
		this.userMapper = userMapper;
	}

	@PutMapping(USER_ID_URL)
	public ResponseEntity<UserDTO> putUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
		verifyAccess(id);
		UserEntity updatedUser = userService.updateUser(userDTO);
		return ResponseEntity.ok(userMapper.userEntityToUserDTO(updatedUser));
	}

	private void verifyAccess(Long id) {
		UserEntity authenticatedUser = userService.getUserAuthenticated();
		if(!authenticatedUser.getId().equals(id)) {
			throw new AccessDeniedException(ACCESS_DENIED);
		}
	}


}
