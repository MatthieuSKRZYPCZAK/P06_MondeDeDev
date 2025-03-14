package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.AuthenticationDTO;
import com.openclassrooms.mddapi.dto.RegistrationDTO;
import com.openclassrooms.mddapi.exception.EmailAlreadyExistsException;
import com.openclassrooms.mddapi.exception.InvalidPasswordException;
import com.openclassrooms.mddapi.exception.UserNotFoundException;
import com.openclassrooms.mddapi.exception.UsernameAlreadyExistsException;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.model.UserEntity;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.security.UserDetailsImpl;
import com.openclassrooms.mddapi.util.PasswordValidator;
import jakarta.validation.Valid;
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

	@Autowired
	public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, BCryptPasswordEncoder passwordEncoder, UserMapper userMapper, PasswordValidator passwordValidator) {
		this.userRepository = userRepository;
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.userMapper = userMapper;
		this.passwordValidator = passwordValidator;
	}

	public Authentication authenticate(@Valid AuthenticationDTO request) {

		UserEntity user = userRepository.findByEmail(request.identifier())
				.orElse(userRepository.findByUsername(request.identifier()).orElse(null));

		if (user == null) {
			throw new UserNotFoundException(USER_NOT_FOUND);
		}

		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getEmail(), request.password()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			return authentication;
		}catch(AuthenticationException e) {
			throw new BadCredentialsException(INVALID_IDENTIFIER);
		}
	}

	public void register(RegistrationDTO user) {
		userExist(user);
		if(!passwordValidator.isValidPassword(user.password())){
			throw new InvalidPasswordException(INVALID_PASSWORD);
		}

		UserEntity userEntity = userMapper.registrationUserToUserEntity(user);
		userEntity.setPassword(passwordEncoder.encode(user.password()));
		userRepository.save(userEntity);
	}

	public void userExist(RegistrationDTO user) {
		boolean existsByUsername = userRepository.existsByUsername(user.username());
		boolean existsByEmail = userRepository.existsByEmail(user.email());
		if (existsByEmail) {
			throw new EmailAlreadyExistsException(EMAIL_ALREADY_EXISTS);
		}

		if(existsByUsername) {
			throw new UsernameAlreadyExistsException(USERNAME_ALREADY_EXISTS);
		}
	}

	public UserEntity getUserAuthenticated(Authentication authentication) {
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		return userRepository.findByEmail(userDetails.getEmail()).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
	}
}
