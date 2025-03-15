package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.RegistrationDTO;
import com.openclassrooms.mddapi.exception.*;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.model.UserEntity;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.security.UserDetailsImpl;
import com.openclassrooms.mddapi.util.PasswordValidator;
import com.openclassrooms.mddapi.util.UsernameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
	 * Récupère l'utilisateur authentifié à partir de l'objet Authentication
	 */
	public UserEntity getUserAuthenticated(Authentication authentication) {
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		return userRepository.findByEmail(userDetails.getEmail()).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
	}
}
