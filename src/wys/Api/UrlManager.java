package wys.Api;

public class UrlManager {

	
	public static final String FETCH_CHECK_USERNAME_URL ="users/check/?username=%s";
	public static final String FETCH_SIGNUP_URL = "users/create";
	public static final String FETCH_VERIFY_USER_URL = "account/verify/%s/%s";
	public static final String FETCH_SIGNIN_URL = "account/login/?username=%s&password=%s";
	public static final String FETCH_RESEND_CODE_URL ="users/request/code/?username=%s&email=%s";
	public static final String FETCH_GETCATEGORIES_URL ="categories";
	public static final String FETCH_GETTOPICS_URL="category/%d/topics";
	public static final String FETCH_POST_TOPIC_URL="topic/create";
	public static final String FETCH_RESET_PASSWORD_URL ="account/resetpassword/%s";
	public static final String FETCH_GET_USER_CATS_URL="users/%d/categories";
	public static final String FETCH_GET_USER_REMAIN_CATS_URL="users/%d/categories/remaining";
	public static final String FETCH_POST_USER_CATS_URL="users/categories";
	public static final String FETCH_GCM_REGISTER_URL="notification/%s";
	public static final String FETCH_ALL_TOPICS_URL="topics";
	public static final String FETCH_DELETE_USER_CAT_URL="users/categories/delete";
	public static final String FETCH_USER_TOPICS_URL="users/%d/topics";
	public static final String FETCH_POST_USER_Topics_URL="users/topics";
	public static final String FETCH_USER_UPCOMING_TOPIC_URL="users/%d/categories/%d/topics/upcoming";
	public static final String FETCH_USER_CURRENT_TOPICS_URL="users/%d/categories/%d/topics/current";
	public static final String FETCH_USER_POST_TOPICS_URL="users/%d/categories/%d/topics/past";
	public static final String FETCH_COMMENT_POST_URL="users/topics/comment";
	public static final String FETCH_GETCOMMENT_URL="topics/%d/comments";
	public static final String FETCH_GETLATESTCOMMENTS_URL="topics/%d/comment/%d";
	public static final String FETCH_UPDATE_USERS_URL="users/update";
	public static final String FETCH_TOPIC_BY_KEYWORD_URL="topics/%s";
	public static final String FETCH_ORG_UPCOMING_TOPIC_URL="org/%d/categories/%d/topics/upcoming";
	public static final String FETCH_ORG_CURRENT_TOPIC_URL="org/%d/categories/%d/topics/current";
	public static final String FETCH_ORG_CLOSED_TOPIC_URL="org/%d/categories/%d/topics/closed";
	public static final String FETCH_ORG_POST_CONCLUSION_URL="organisation/topic/conclusion";
	
}
