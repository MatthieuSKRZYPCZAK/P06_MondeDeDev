package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.PasswordUpdateDTO;
import com.openclassrooms.mddapi.dto.RegistrationDTO;
import com.openclassrooms.mddapi.dto.UserUpdateDTO;
import com.openclassrooms.mddapi.exception.*;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.model.TopicEnum;
import com.openclassrooms.mddapi.model.UserEntity;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.security.UserDetailsImpl;
import com.openclassrooms.mddapi.util.PasswordValidator;
import com.openclassrooms.mddapi.util.UsernameValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.openclassrooms.mddapi.common.ResponseMessages.*;

@Service
public class UserService  {


	private final UserRepository userRepository;
	private final AuthenticationManager authenticationManager;
	private final BCryptPasswordEncoder passwordEncoder;
	private final UserMapper userMapper;
	private final PasswordValidator passwordValidator;
	private final UsernameValidator usernameValidator;

	@Autowired
	public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, BCryptPasswordEncoder passwordEncoder, UserMapper userMapper, PasswordValidator passwordValidator, UsernameValidator usernameValidator) {
		this.userRepository = userRepository;
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.userMapper = userMapper;
		this.passwordValidator = passwordValidator;
		this.usernameValidator = usernameValidator;
	}

	/**
	 * Authentifie un utilisateur en fonction de son email ou username
	 */
	public Authentication authenticate(String identifier, String password) {

		UserEntity user = userRepository.findByEmail(identifier)
				.orElseGet(() -> userRepository.findByUsername(identifier)
						.orElseThrow(() -> new BadCredentialsException(INVALID_IDENTIFIER)));

		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getEmail(), password));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			return authentication;
		}catch(AuthenticationException e) {
			throw new BadCredentialsException(INVALID_IDENTIFIER);
		}
	}

	/**
	 * Inscrit un nouvel utilisateur après vérifications
	 */
	@Transactional
	public UserEntity register(RegistrationDTO request) {
		validateUserUniqueness(request);
		validatePassword(request.password());
		validateUsername(request.username());

		UserEntity newUser = userMapper.registrationUserToUserEntity(request);
		newUser.setPassword(passwordEncoder.encode(request.password()));

		return userRepository.save(newUser);
	}

	/**
	 * Vérifie si l'utilisateur existe déjà avec cet email ou username
	 */
	private void validateUserUniqueness(RegistrationDTO user) {
		if (userRepository.existsByEmail(user.email())) {
			throw new EmailAlreadyExistsException(EMAIL_ALREADY_EXISTS);
		}

		if(userRepository.existsByUsername(user.username())) {
			throw new UsernameAlreadyExistsException(USERNAME_ALREADY_EXISTS);
		}
	}

	/**
	 * Vérifie si le mot de passe respecte les critères de sécurité
	 */
	private void validatePassword(String password) {
		if(!passwordValidator.isValidPassword(password)) {
			throw new InvalidPasswordException(INVALID_PASSWORD);
		}
	}

	/**
	 * Vérifie si le nom d'utilisateur est conforme aux règles
	 */
	private void validateUsername(String username) {
		if (!usernameValidator.isValidUsername(username)) {
			throw new InvalidUsernameException(INVALID_USERNAME);
		}
	}

	/**
	 * Récupère l'utilisateur authentifié
	 */
	public UserEntity getUserAuthenticated() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// Cas après le login (manuel): UserDetailsImpl est dans le SecurityContext
		if(auth.getPrincipal() instanceof UserDetailsImpl user) {
			return userRepository.findById(user.getUser().getId()).orElseThrow(() -> new InvalidJwtException(EXPIRED_JWT));
		}

		// Cas après appel avec JWT: le principal est un Jwt
		if(auth.getPrincipal() instanceof Jwt jwt) {
			Long userId = Long.parseLong(jwt.getSubject());
			return userRepository.findById(userId).orElseThrow(() -> new InvalidJwtException(EXPIRED_JWT));
		}
		throw new InvalidJwtException(EXPIRED_JWT);
	}

	@Transactional
	public UserEntity updateUser(@Valid UserUpdateDTO userUpdateDTO, UserEntity authenticatedUser) {

		boolean isEmailChanged = !authenticatedUser.getEmail().equals(userUpdateDTO.email());
		boolean isUsernameChanged = !authenticatedUser.getUsername().equals(userUpdateDTO.username());

		if(isEmailChanged) {
			validateEmailUniqueness(userUpdateDTO.email(), authenticatedUser.getId());
			authenticatedUser.setEmail(userUpdateDTO.email());
		}

		if(isUsernameChanged) {
			validateUsernameUniqueness(userUpdateDTO.username(), authenticatedUser.getId());
			validateUsername(userUpdateDTO.username());
			authenticatedUser.setUsername(userUpdateDTO.username());
		}

		return userRepository.save(authenticatedUser);
	}

	private void validateUsernameUniqueness(String username, Long userId) {
		boolean usernameExists = userRepository.existsByUsername(username);

		if(usernameExists) {
			UserEntity existingUser = userRepository.findByUsername(username)
					.orElseThrow(() -> new UsernameAlreadyExistsException(USERNAME_ALREADY_EXISTS));

			if (!existingUser.getId().equals(userId)) {
				throw new UsernameAlreadyExistsException(USERNAME_ALREADY_EXISTS);
			}
		}
	}

	private void validateEmailUniqueness(String email, Long userId) {
		boolean emailExists = userRepository.existsByEmail(email);

		if(emailExists) {
			UserEntity existingUser = userRepository.findByEmail(email)
					.orElseThrow(() -> new EmailAlreadyExistsException(EMAIL_ALREADY_EXISTS));

			if (!existingUser.getId().equals(userId)) {
				throw new EmailAlreadyExistsException(EMAIL_ALREADY_EXISTS);
			}
		}
	}

	@Transactional
	public UserEntity subscribeToTopic(String topicName, UserEntity authenticatedUser) {
		TopicEnum topic = getValidTopic(topicName);

		if(authenticatedUser.getTopics().contains(topic)) {
			return authenticatedUser;
		}

		authenticatedUser.getTopics().add(topic);
		return userRepository.save(authenticatedUser);
	}

	@Transactional
	public UserEntity unsubscribeFromTopic(String topicName, UserEntity authenticatedUser) {
		TopicEnum topic = getValidTopic(topicName);

		if(!authenticatedUser.getTopics().contains(topic)) {
			return authenticatedUser;
		}

		authenticatedUser.getTopics().remove(topic);
		return userRepository.save(authenticatedUser);
	}

	private TopicEnum getValidTopic(String topicName) {
		try {
			return TopicEnum.valueOf(topicName.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new TopicNotFoundException(String.format(TOPIC_NOT_FOUND, topicName));
		}
	}

	@Transactional
	public void updatePassword(@Valid PasswordUpdateDTO passwordUpdateDTO, UserEntity authenticatedUser) {

		if(!passwordEncoder.matches(passwordUpdateDTO.oldPassword(), authenticatedUser.getPassword())) {
			throw new InvalidPasswordException(INVALID_OLD_PASSWORD);
		}

		if(passwordEncoder.matches(passwordUpdateDTO.newPassword(), authenticatedUser.getPassword())) {
			throw new InvalidPasswordException(NEW_PASSWORD_MUST_BE_DIFFERENT);
		}

		validatePassword(passwordUpdateDTO.newPassword());
		authenticatedUser.setPassword(passwordEncoder.encode(passwordUpdateDTO.newPassword()));
		userRepository.save(authenticatedUser);
	}

	public UserEntity findByUserId(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
	}
}
