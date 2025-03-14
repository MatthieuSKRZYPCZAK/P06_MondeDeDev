package com.openclassrooms.mddapi.util;

import org.springframework.stereotype.Component;

@Component
public class PasswordValidator {

	public boolean isValidPassword(String password) {
		String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
		return password.matches(regex);
	}
}
