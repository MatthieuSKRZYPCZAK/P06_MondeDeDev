package com.openclassrooms.mddapi.common;

public class ApiRoutes {

	public static final String BASE_URL = "/api";


	/* Auth URL */
	public static final String AUTH_URL = BASE_URL+"/auth";

	public static final String LOGIN_URL = AUTH_URL+"/login";

	public static final String REGISTER_URL = AUTH_URL+"/register";

	public static final String ME_URL = AUTH_URL+"/me";

	/* Users URL */
	public static final String USER_URL = BASE_URL+"/user";

	public static final String USER_ID_URL = USER_URL+"/{id}";

}
