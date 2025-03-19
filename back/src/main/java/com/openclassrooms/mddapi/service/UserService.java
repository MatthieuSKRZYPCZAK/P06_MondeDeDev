package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.PasswordUpdateDTO;
import com.openclassrooms.mddapi.dto.RegistrationDTO;
import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.exception.*;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.model.TopicEntity;
import com.openclassrooms.mddapi.model.UserEntity;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.util.PasswordValidator;
import com.openclassrooms.mddapi.util.UsernameValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	private final TopicRepository topicRepository;

	@Autowired
	public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, BCryptPasswordEncoder passwordEncoder, UserMapper userMapper, PasswordValidator passwordValidator, UsernameValidator usernameValidator, TopicRepository topicRepository) {
		this.userRepository = userRepository;
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.userMapper = userMapper;
		this.passwordValidator = passwordValidator;
		this.usernameValidator = usernameValidator;
		this.topicRepository = topicRepository;
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
		return userRepository.findByEmail(auth.getName()).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
	}

	@Transactional
	public UserEntity updateUser(@Valid UserDTO userDTO, Long userId) {
		UserEntity user = verifyAccess(userId);

		validateUsername(userDTO.username());

		user.setUsername(userDTO.username());
		user.setEmail(userDTO.email());

		return userRepository.save(user);
	}

	@Transactional
	public UserEntity subscribeToTopic(Long topicId, UserEntity user) {
		UserEntity userVerify = verifyAccess(user.getId());
		TopicEntity topic = topicRepository.findById(topicId).orElseThrow(() -> new TopicNotFoundException(TOPIC_NOT_FOUND));

		if(userVerify.getTopics().contains(topic)) {
			return userVerify;
		}

		user.getTopics().add(topic);
		return userRepository.save(userVerify);
	}

	@Transactional
	public UserEntity unsubscribeFromTopic(Long topicId, UserEntity user) {
		UserEntity userVerify = verifyAccess(user.getId());
		TopicEntity topic = topicRepository.findById(topicId).orElseThrow(() -> new TopicNotFoundException(TOPIC_NOT_FOUND));

		if(!userVerify.getTopics().contains(topic)) {
			return userVerify;
		}

		userVerify.getTopics().remove(topic);
		return userRepository.save(userVerify);
	}

	private UserEntity verifyAccess(Long id) {
		UserEntity authenticatedUser = getUserAuthenticated();

		if(!authenticatedUser.getId().equals(id)) {
			throw new AccessDeniedException(ACCESS_DENIED);
		}

		return authenticatedUser;
	}

	@Transactional
	public void updatePassword(@Valid PasswordUpdateDTO passwordUpdateDTO, UserEntity userAuth) {
		UserEntity user = verifyAccess(userAuth.getId());

		if(!passwordEncoder.matches(passwordUpdateDTO.oldPassword(), user.getPassword())) {
			throw new InvalidPasswordException(INVALID_OLD_PASSWORD);
		}

		validatePassword(passwordUpdateDTO.newPassword());
		user.setPassword(passwordEncoder.encode(passwordUpdateDTO.newPassword()));
		userRepository.save(user);
	}
}
