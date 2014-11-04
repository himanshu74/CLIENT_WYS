package wys.Api;

public class UrlManager {

	
	public static final String FETCH_CHECK_USERNAME_URL ="users/check/?username=%s";
	public static final String FETCH_SIGNUP_URL = "users/create";
	public static final String FETCH_VERIFY_USER_URL = "account/verify/%s/%s";
	public static final String FETCH_SIGNIN_URL = "account/login/?username=%s&password=%s";
	public static final String FETCH_RESEND_CODE_URL ="users/request/code/?username=%s&email=%s";
	public static final String FETCH_GETCATEGORIES_URL ="categories";
	public static final String FETCH_GETTOPICS_URL="category/%d/topics";
	public static final String FETCH_POST_TOPIC_URL="topic";
	public static final String FETCH_RESET_PASSWORD_URL ="account/resetpassword/%s";
	public static final String FETCH_GET_USER_CATS_URL="users/%d/categories";
	public static final String FETCH_GET_USER_REMAIN_CATS_URL="users/%d/categories/remaining";
	public static final String FETCH_POST_USER_CATS_URL="users/categories";
	
}
