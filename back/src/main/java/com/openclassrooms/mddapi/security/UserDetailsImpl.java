package com.openclassrooms.mddapi.security;

import com.openclassrooms.mddapi.model.UserEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Implémentation de `UserDetails` pour Spring Security.
 */
@Data
public class UserDetailsImpl implements UserDetails {

	private String email;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(UserEntity user) {
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.authorities = Collections.emptyList();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}
}
