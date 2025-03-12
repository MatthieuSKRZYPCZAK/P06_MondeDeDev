package com.openclassrooms.mddapi.mapper;


import com.openclassrooms.mddapi.dto.RegistrationDTO;
import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

	private final BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UserMapper(BCryptPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public UserEntity registrationUserToUserEntity(RegistrationDTO registrationDTO) {
		UserEntity userEntity = new UserEntity();
		userEntity.setEmail(registrationDTO.email());
		userEntity.setPassword(passwordEncoder.encode(registrationDTO.password()));
		userEntity.setUsername(registrationDTO.username());

		return userEntity;
	}

	public UserDTO userEntityToUserDTO(UserEntity userEntity) {
		return new UserDTO(
				userEntity.getId(),
				userEntity.getUsername(),
				userEntity.getEmail()
		);
	}

}
