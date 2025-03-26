package com.openclassrooms.mddapi.mapper;


import com.openclassrooms.mddapi.dto.RegistrationDTO;
import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.model.UserEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper permettant de convertir entre les entités de type UserEntity et leurs représentations DTO.
 */
@Component
public class UserMapper {

	/**
	 * Convertit un RegistrationDTO en une entité UserEntity.
	 * Utilisé lors de l'inscription d'un nouvel utilisateur.
	 *
	 * @param registrationDTO Objet contenant les données du formulaire d'inscription.
	 * @return Une nouvelle instance de UserEntity avec les données du DTO.
	 */
	public UserEntity registrationUserToUserEntity(RegistrationDTO registrationDTO) {
		UserEntity userEntity = new UserEntity();
		userEntity.setEmail(registrationDTO.email());
		userEntity.setPassword(registrationDTO.password());
		userEntity.setUsername(registrationDTO.username());
		return userEntity;
	}

	/**
	 * Convertit une entité UserEntity en un DTO UserDTO.
	 * Utilisé pour exposer les données utilisateur côté front-end.
	 *
	 * @param userEntity L'entité utilisateur à convertir.
	 * @return Un objet UserDTO contenant les informations exposables de l'utilisateur.
	 */
	public UserDTO toUserDTO(UserEntity userEntity) {
		return new UserDTO(
				userEntity.getId(),
				userEntity.getUsername(),
				userEntity.getEmail(),
				userEntity.getTopics()
		);
	}
}
