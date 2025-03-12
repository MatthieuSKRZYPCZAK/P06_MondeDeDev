package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.AuthenticationDTO;
import com.openclassrooms.mddapi.dto.RegistrationDTO;
import com.openclassrooms.mddapi.exception.EmailAlreadyExistsException;
import com.openclassrooms.mddapi.exception.UsernameAlreadyExistsException;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.model.UserEntity;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.security.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService  {


	private final UserRepository userRepository;
	private final AuthenticationManager authenticationManager;
	private final BCryptPasswordEncoder passwordEncoder;
	private final UserMapper userMapper;

	@Autowired
	public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, BCryptPasswordEncoder passwordEncoder, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.userMapper = userMapper;
	}

	public Authentication authenticate(@Valid AuthenticationDTO request) {

		UserEntity user = userRepository.findByEmail(request.identifier())
				.orElse(userRepository.findByUsername(request.identifier()).orElse(null));

		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password");
		}

		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getEmail(), request.password()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			return authentication;
		}catch(AuthenticationException e) {
			throw new BadCredentialsException("Invalid username or password");
		}
	}

	public void register(RegistrationDTO user) {
		UserEntity userEntity = userMapper.registrationUserToUserEntity(user);
		userExist(userEntity);
		userRepository.save(userEntity);
	}

	private void userExist(UserEntity user) {
		boolean existsByUsername = userRepository.existsByUsername(user.getUsername());
		boolean existsByEmail = userRepository.existsByEmail(user.getEmail());
		if (existsByEmail) {
			throw new EmailAlreadyExistsException("Email already exists");
		}

		if(existsByUsername) {
			throw new UsernameAlreadyExistsException("Username already exists");
		}
	}

	public UserEntity getUserAuthenticated(Authentication authentication) {
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		return userRepository.findByEmail(userDetails.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}
}
