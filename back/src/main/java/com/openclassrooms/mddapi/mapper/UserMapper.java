package com.openclassrooms.mddapi.mapper;


import com.openclassrooms.mddapi.dto.RegistrationDTO;
import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

	public UserEntity registrationUserToUserEntity(RegistrationDTO registrationDTO) {
		UserEntity userEntity = new UserEntity();
		userEntity.setEmail(registrationDTO.email());
		userEntity.setPassword(registrationDTO.password());
		userEntity.setUsername(registrationDTO.username());
		return userEntity;
	}

	public UserDTO userEntityToUserDTO(UserEntity userEntity) {
		return new UserDTO(
				userEntity.getId(),
				userEntity.getUsername(),
				userEntity.getEmail(),
				userEntity.getTopics()
		);
	}
}
