package wys.Api;

import android.annotation.SuppressLint;
import java.util.ArrayList;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import wys.Business.BaseBusiness;
import wys.Business.CommentBo;
import wys.Business.TopicBo;
import wys.Business.UserBo;
import wys.BusinessHandlers.CommentHandler;
import wys.BusinessHandlers.ObjectHandler;
import wys.BusinessHandlers.TopicHandler;
import wys.Http.HttpApi;
import wys.Http.IHttpApi;

public class WysUserApi {

	private static final int SUCCESS = 0;
	private static final int ERROR = 1;

	private static final String HOST = "http://";
	private static final String DOMAIN = "192.168.0.5/WYS/api/";
	// private static final String DOMAIN = "129.107.144.192/WYS/api/";
	// private static final String DOMAIN = "10.226.50.83/WYS/api/";
	// private static final String DOMAIN =
	// "jangra.com.s10.dotnetpark.com/api/";

	private IHttpApi mHttpApi;

	private final DefaultHttpClient mHttpClient = HttpApi.CreateHttpclient();

	public String GetUrl(String path) {
		return HOST + DOMAIN + path;
	}

	public WysUserApi() {
		this.mHttpApi = new HttpApi(mHttpClient);
	}

	@SuppressLint("DefaultLocale")
	public ArrayList<TopicBo> doGetUserTopics(int userId) {
		String path = String.format(UrlManager.FETCH_USER_TOPICS_URL, userId);

		HttpGet get = mHttpApi.CreateHttpGet(GetUrl(path));
		TopicHandler topicHanlder = new TopicHandler();

		@SuppressWarnings("unchecked")
		ArrayList<TopicBo> topics = (ArrayList<TopicBo>) mHttpApi
				.DoHttpRequestJson(get, topicHanlder);

		return topics;

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
					GetUrl(UrlManager.FETCH_POST_USER_Topics_URL), jsonUser);
			if (!response.equals(null) && response.equals("0")) {
				status = SUCCESS;
			} else
				status = ERROR;

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return status;
	}

	public int updateUser(UserBo user) {
		int status = -1;
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("Username", user.get_username());
			jsonObject.put("Password", user.get_password());
			jsonObject.put("Email", user.get_email());
			jsonObject.put("UserId", user.get_userId());
			String response = mHttpApi.DoHttpPostJson(
					GetUrl(UrlManager.FETCH_UPDATE_USERS_URL), jsonObject);
			if (!response.equals(null) && response.equals("0")) {
				status = SUCCESS;
			} else
				status = ERROR;

		} catch (JSONException josnJsonException) {
			josnJsonException.printStackTrace();
		}

		return status;
	}

	public ArrayList<TopicBo> getUserUpcomingTopics(int userId, int catId) {
		String path = String.format(UrlManager.FETCH_USER_UPCOMING_TOPIC_URL,
				userId, catId);

		HttpGet get = mHttpApi.CreateHttpGet(GetUrl(path));

		TopicHandler topicHandler = new TopicHandler();
		@SuppressWarnings("unchecked")
		ArrayList<TopicBo> topics = (ArrayList<TopicBo>) mHttpApi
				.DoHttpRequestJson(get, topicHandler);

		return topics;
	}

	public ArrayList<TopicBo> getUserCurrentTopics(int userId, int catId) {
		String path = String.format(UrlManager.FETCH_USER_CURRENT_TOPICS_URL,
				userId, catId);

		HttpGet get = mHttpApi.CreateHttpGet(GetUrl(path));

		TopicHandler topicHandler = new TopicHandler();
		@SuppressWarnings("unchecked")
		ArrayList<TopicBo> topics = (ArrayList<TopicBo>) mHttpApi
				.DoHttpRequestJson(get, topicHandler);

		return topics;
	}

	public ArrayList<TopicBo> getUserPastTopics(int userId, int catId) {
		String path = String.format(UrlManager.FETCH_USER_POST_TOPICS_URL,
				userId, catId);

		HttpGet get = mHttpApi.CreateHttpGet(GetUrl(path));

		TopicHandler topicHandler = new TopicHandler();
		@SuppressWarnings("unchecked")
		ArrayList<TopicBo> topics = (ArrayList<TopicBo>) mHttpApi
				.DoHttpRequestJson(get, topicHandler);

		return topics;
	}

	public int PostComment(CommentBo commBo) {
		int status = -1;
		JSONObject jsonComment = new JSONObject();
		try {
			jsonComment.put("TopicId", commBo.get_topicId());
			jsonComment.put("UserId", commBo.get_userId());
			jsonComment.put("Comment", commBo.get_comment());

			String response = mHttpApi.DoHttpPostJson(
					GetUrl(UrlManager.FETCH_COMMENT_POST_URL), jsonComment);
			if (!response.equals(null) && response.equals("0")) {
				status = SUCCESS;
			} else
				status = ERROR;

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return status;
	}

	public ArrayList<CommentBo> getcommentsBytopic(int topicid) {
		String path = String.format(UrlManager.FETCH_GETCOMMENT_URL, topicid);

		HttpGet get = mHttpApi.CreateHttpGet(GetUrl(path));

		CommentHandler comentHandler = new CommentHandler();
		comentHandler.isSingleCommentExpected = true;
		@SuppressWarnings("unchecked")
		ArrayList<CommentBo> comments = (ArrayList<CommentBo>) mHttpApi
				.DoHttpRequestJson(get, comentHandler);

		return comments;
	}

	public ArrayList<CommentBo> getLatestComments(int topicid, int serverid) {
		String path = String.format(UrlManager.FETCH_GETLATESTCOMMENTS_URL,
				topicid, serverid);

		HttpGet get = mHttpApi.CreateHttpGet(GetUrl(path));

		CommentHandler comentHandler = new CommentHandler();
		comentHandler.isSingleCommentExpected = true;
		@SuppressWarnings("unchecked")
		ArrayList<CommentBo> comments = (ArrayList<CommentBo>) mHttpApi
				.DoHttpRequestJson(get, comentHandler);
		return comments;
	}

	public ArrayList<TopicBo> getTopicsbyKeyword(String keyword) {
		String path = String.format(UrlManager.FETCH_TOPIC_BY_KEYWORD_URL,
				keyword);

		HttpGet get = mHttpApi.CreateHttpGet(GetUrl(path));

		TopicHandler topicHandler = new TopicHandler();
		@SuppressWarnings("unchecked")
		ArrayList<TopicBo> topics = (ArrayList<TopicBo>) mHttpApi
				.DoHttpRequestJson(get, topicHandler);

		return topics;
	}

	public ArrayList<TopicBo> GetOrgUpcomingTopics(int userid, int catid) {
		String path = String.format(UrlManager.FETCH_ORG_UPCOMING_TOPIC_URL,
				userid, catid);

		HttpGet get = mHttpApi.CreateHttpGet(GetUrl(path));

		TopicHandler topicHandler = new TopicHandler();
		@SuppressWarnings("unchecked")
		ArrayList<TopicBo> topics = (ArrayList<TopicBo>) mHttpApi
				.DoHttpRequestJson(get, topicHandler);

		return topics;
	}

	public ArrayList<TopicBo> GetOrgCurrentTopics(int userid, int catid) {
		String path = String.format(UrlManager.FETCH_ORG_CURRENT_TOPIC_URL,
				userid, catid);

		HttpGet get = mHttpApi.CreateHttpGet(GetUrl(path));

		TopicHandler topicHandler = new TopicHandler();
		@SuppressWarnings("unchecked")
		ArrayList<TopicBo> topics = (ArrayList<TopicBo>) mHttpApi
				.DoHttpRequestJson(get, topicHandler);

		return topics;
	}

	public ArrayList<TopicBo> GetOrgClosedTopics(int userid, int catid) {
		String path = String.format(UrlManager.FETCH_ORG_CLOSED_TOPIC_URL,
				userid, catid);

		HttpGet get = mHttpApi.CreateHttpGet(GetUrl(path));

		TopicHandler topicHandler = new TopicHandler();
		@SuppressWarnings("unchecked")
		ArrayList<TopicBo> topics = (ArrayList<TopicBo>) mHttpApi
				.DoHttpRequestJson(get, topicHandler);

		return topics;
	}

	public int postConclusion(TopicBo topic) {
		int status = -1;
		JSONObject jsonConclusion = new JSONObject();
		try {
			jsonConclusion.put("TopicId", topic.get_topicId());
			jsonConclusion.put("Conclusion", topic.get_conclusion());

			String response = mHttpApi.DoHttpPostJson(
					GetUrl(UrlManager.FETCH_ORG_POST_CONCLUSION_URL),
					jsonConclusion);
			if (!response.equals(null) && response.equals("0")) {
				status = SUCCESS;
			} else
				status = ERROR;

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return status;

	}

}
