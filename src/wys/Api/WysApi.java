package wys.Api;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import wys.Business.BaseBusiness;
import wys.Business.CategoryBo;
import wys.Business.ErrorReporter;
import wys.Business.TopicBo;
import wys.Business.UserBo;
import wys.BusinessHandlers.CategoryHandler;
import wys.BusinessHandlers.ObjectHandler;
import wys.BusinessHandlers.TopicHandler;
import wys.BusinessHandlers.UserHandler;
import wys.Http.HttpApi;
import wys.Http.IHttpApi;

public class WysApi {

	private static final int SUCCESS = 0;
	private static final int ERROR = 1;

	private static final String HOST = "http://";
	private static final String DOMAIN = "192.168.0.5/WYS/api/";
	// private static final String DOMAIN = "129.107.144.192/WYS/api/";
	// private static final String DOMAIN = "10.226.50.83/WYS/api/";
	// private static final String DOMAIN ="jangra.com.s10.dotnetpark.com/api/";

	private IHttpApi mHttpApi;

	private final DefaultHttpClient mHttpClient = HttpApi.CreateHttpclient();

	public String GetUrl(String path) {
		return HOST + DOMAIN + path;
	}

	public WysApi() {
		this.mHttpApi = new HttpApi(mHttpClient);
	}

	// //////////// NETWORK OPERATIONS METHODS \\\\\\\\\\\\\\\

	public int DoSignUp(UserBo user) {

		NameValuePair[] postBody = new NameValuePair[6];
		postBody[0] = (new BasicNameValuePair("Username", user.get_username()));
		postBody[1] = (new BasicNameValuePair("Password", user.get_password()));
		postBody[2] = (new BasicNameValuePair("Email", user.get_email()));
		postBody[3] = (new BasicNameValuePair("RoleId", Integer.toString(user
				.get_roleId())));
		postBody[4] = (new BasicNameValuePair("DomainId", Integer.toString(user
				.get_domainId())));
		postBody[5] = (new BasicNameValuePair("RegId", user.getRegId()));

		String url = GetUrl(UrlManager.FETCH_SIGNUP_URL);
		String response = mHttpApi.DoHttpPost(url, postBody);

		if (!response.equals(null) && response.equals("0")) {
			SessionManager.setUserBo(user);
			return SUCCESS;

		} else {
			return ERROR;

		}

	}

	public int PostTopic(TopicBo topic) {
		int status = -1;

		JSONObject jsonTopic = new JSONObject();
		try {
			jsonTopic.put("Name", topic.get_name());
			jsonTopic.put("DomainId", topic.get_domainId());
			jsonTopic.put("UserId", topic.get_userId());
			jsonTopic.put("BeginDateUnix", topic.get_bgeindateUnixUTC());
			jsonTopic.put("BeginDateString", topic.get_beginDateString());
			jsonTopic.put("EndDateString", topic.get_endDateString());
			jsonTopic.put("KeyWord", topic.get_keyword());

		} catch (JSONException e) {
			e.printStackTrace();
		}

		String url = GetUrl(UrlManager.FETCH_POST_TOPIC_URL);
		String response = mHttpApi.DoHttpPostJson(url, jsonTopic);
		if (!response.equals(null) && !response.equals("-1")) {
			status = Integer.parseInt(response);
		} else
			status = ERROR;
		return status;
	}

	public int postUserCategories(UserBo user) {
		int status = -1;
		JSONObject jsonUser = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		try {
			jsonUser.put("UserId", user.get_userId());
			ArrayList<CategoryBo> cats = user.getUserCategories();
			for (int i = 0; i < cats.size(); i++) {

				JSONObject jsonCats = new JSONObject();
				jsonCats.put("CategoryId", cats.get(i).get_serverId());
				/* jsonCats.put("CategoryId", cats.get(i).get_categoryId()); */
				jsonArray.put(jsonCats);
			}
			jsonUser.put("CategoryList", jsonArray);
			String response = mHttpApi.DoHttpPostJson(
					GetUrl(UrlManager.FETCH_POST_USER_CATS_URL), jsonUser);
			if (!response.equals(null) && response.equals("0")) {
				status = SUCCESS;
			} else if (response.equals(null) || response.equals("1")) {
				status = ERROR;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	public int postUserTopics(UserBo user) {
		int status = -1;
		JSONObject jsonUser = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		try {
			jsonUser.put("UserId", user.get_userId());
			ArrayList<TopicBo> topics = user.getUserTopics();
			for (int i = 0; i < topics.size(); i++) {

				JSONObject jsonTopics = new JSONObject();
				jsonTopics.put("TopicId", topics.get(i).get_serverId());
				jsonArray.put(jsonTopics);
			}
			jsonUser.put("TopicsList", jsonArray);
			String response = mHttpApi.DoHttpPostJson(
					GetUrl(UrlManager.FETCH_POST_USER_CATS_URL), jsonUser);
			if (!response.equals(null) && response.equals("0")) {
				status = SUCCESS;
			} else if (response.equals(null) || response.equals("1")) {
				status = ERROR;

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return status;
	}

	public int postUserDeletedCategories(UserBo user) {
		int status = -1;
		JSONObject jsonUser = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		try {
			jsonUser.put("UserId", user.get_userId());
			ArrayList<CategoryBo> cats = user.getUserCategories();
			for (int i = 0; i < cats.size(); i++) {

				JSONObject jsonCats = new JSONObject();
				jsonCats.put("CategoryId", cats.get(i).get_categoryId());
				/* jsonCats.put("CategoryId", cats.get(i).get_categoryId()); */
				jsonArray.put(jsonCats);
			}
			jsonUser.put("CategoryList", jsonArray);
			String response = mHttpApi.DoHttpPostJson(
					GetUrl(UrlManager.FETCH_DELETE_USER_CAT_URL), jsonUser);
			if (!response.equals(null) && response.equals("0")) {
				status = SUCCESS;
			} else if (response.equals(null) || response.equals("1")) {
				status = ERROR;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	public ArrayList<BaseBusiness> GetUserByUsername(String username) {

		String path = String.format(UrlManager.FETCH_CHECK_USERNAME_URL,
				username);

		HttpGet get = mHttpApi.CreateHttpGet(GetUrl(path));
		ObjectHandler objectHanlder = new ObjectHandler();

		@SuppressWarnings("unchecked")
		ArrayList<BaseBusiness> users = (ArrayList<BaseBusiness>) mHttpApi
				.DoHttpRequestJson(get, objectHanlder);

		return users;

	}

	public ArrayList<BaseBusiness> DoVerifyUser(String username, String code) {
		String path = String.format(UrlManager.FETCH_VERIFY_USER_URL, username,
				code);
		HttpGet get = mHttpApi.CreateHttpGet(GetUrl(path));
		ObjectHandler objectHandler = new ObjectHandler();

		@SuppressWarnings("unchecked")
		ArrayList<BaseBusiness> users = (ArrayList<BaseBusiness>) mHttpApi
				.DoHttpRequestJson(get, objectHandler);

		return users;

	}

	public ArrayList<BaseBusiness> DoSignIn(String username, String password) {
		String path = String.format(UrlManager.FETCH_SIGNIN_URL, username,
				password);
		HttpGet get = mHttpApi.CreateHttpGet(GetUrl(path));
		UserHandler userHandler = new UserHandler();
		userHandler.isSingleResultExpected = true;
		@SuppressWarnings("unchecked")
		ArrayList<BaseBusiness> users = (ArrayList<BaseBusiness>) mHttpApi
				.DoHttpRequestJson(get, userHandler);
		if (users != null) {
			SessionManager.setUserBo(users.get(0));
		}
		return users;
	}

	public ArrayList<BaseBusiness> DoResendVerificationCode(String username,
			String email) {
		String path = String.format(UrlManager.FETCH_RESEND_CODE_URL, username,
				email);
		HttpGet get = mHttpApi.CreateHttpGet(GetUrl(path));
		ObjectHandler objectHandler = new ObjectHandler();

		@SuppressWarnings("unchecked")
		ArrayList<BaseBusiness> users = (ArrayList<BaseBusiness>) mHttpApi
				.DoHttpRequestJson(get, objectHandler);

		return users;

	}

	public ArrayList<BaseBusiness> GetCategories() {
		String path = String.format(UrlManager.FETCH_GETCATEGORIES_URL);
		HttpGet get = mHttpApi.CreateHttpGet(GetUrl(path));
		CategoryHandler categoryHandler = new CategoryHandler();

		@SuppressWarnings("unchecked")
		ArrayList<BaseBusiness> cats = (ArrayList<BaseBusiness>) mHttpApi
				.DoHttpRequestJson(get, categoryHandler);

		return cats;
	}

	public ArrayList<TopicBo> getTopics(int catId) {

		String path = String.format(UrlManager.FETCH_GETTOPICS_URL, catId);
		HttpGet get = mHttpApi.CreateHttpGet(GetUrl(path));
		TopicHandler topicHandler = new TopicHandler();
		@SuppressWarnings("unchecked")
		ArrayList<TopicBo> topics = (ArrayList<TopicBo>) mHttpApi
				.DoHttpRequestJson(get, topicHandler);

		return topics;

	}

	public ArrayList<TopicBo> getAllTopics() {
		String path = String.format(UrlManager.FETCH_ALL_TOPICS_URL);
		HttpGet get = mHttpApi.CreateHttpGet(GetUrl(path));
		TopicHandler topicHandler = new TopicHandler();
		@SuppressWarnings("unchecked")
		ArrayList<TopicBo> topics = (ArrayList<TopicBo>) mHttpApi
				.DoHttpRequestJson(get, topicHandler);

		return topics;
	}

	public ArrayList<BaseBusiness> doResetPassword(String username) {
		String path = String.format(UrlManager.FETCH_RESET_PASSWORD_URL,
				username);
		HttpGet get = mHttpApi.CreateHttpGet(GetUrl(path));
		ObjectHandler objectHandler = new ObjectHandler();

		@SuppressWarnings("unchecked")
		ArrayList<BaseBusiness> users = (ArrayList<BaseBusiness>) mHttpApi
				.DoHttpRequestJson(get, objectHandler);
		return users;

	}

	public ArrayList<CategoryBo> doGetUserCategories(int userid) {

		String path = String.format(UrlManager.FETCH_GET_USER_CATS_URL, userid);
		HttpGet get = mHttpApi.CreateHttpGet(GetUrl(path));
		CategoryHandler categoryHandler = new CategoryHandler();
		@SuppressWarnings("unchecked")
		ArrayList<CategoryBo> cats = (ArrayList<CategoryBo>) mHttpApi
				.DoHttpRequestJson(get, categoryHandler);

		return cats;
	}

	public ArrayList<CategoryBo> doGetUserRemainCategories(int userid) {

		String path = String.format(UrlManager.FETCH_GET_USER_REMAIN_CATS_URL,
				userid);
		HttpGet get = mHttpApi.CreateHttpGet(GetUrl(path));
		CategoryHandler categoryHandler = new CategoryHandler();
		@SuppressWarnings("unchecked")
		ArrayList<CategoryBo> cats = (ArrayList<CategoryBo>) mHttpApi
				.DoHttpRequestJson(get, categoryHandler);

		return cats;
	}

	public void doRegisterGcm(String registrationId) {
		String path = String.format(UrlManager.FETCH_GCM_REGISTER_URL,
				registrationId);
		HttpGet get = mHttpApi.CreateHttpGet(GetUrl(path));
		ObjectHandler objectHandler = new ObjectHandler();
		ArrayList<BaseBusiness> bus = (ArrayList<BaseBusiness>) mHttpApi
				.DoHttpRequestJson(get, objectHandler);

	}

}
