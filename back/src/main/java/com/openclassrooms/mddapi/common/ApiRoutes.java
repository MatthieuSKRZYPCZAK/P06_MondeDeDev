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

	public static final String USER_UPDATE_URL = USER_URL+"/update";

	public static final String USER_PASSWORD_URL = USER_URL+"/password";

	public static final String USER_SUBSCRIBE_TOPIC_URL = USER_URL+"/subscribe/{topicId}";

	public static final String USER_UNSUBSCRIBE_TOPIC_URL = USER_URL+"/unsubscribe/{topicId}";

	/* Topics URL */
	public static final String TOPIC_URL = BASE_URL+"/topic";

	public static final String TOPIC_ID_URL = TOPIC_URL+"/{id}";

}
