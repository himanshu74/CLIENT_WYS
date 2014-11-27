package wys.Api;

import android.annotation.SuppressLint;
import java.util.ArrayList;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import wys.Business.BaseBusiness;
import wys.Business.TopicBo;
import wys.Business.UserBo;
import wys.BusinessHandlers.ObjectHandler;
import wys.BusinessHandlers.TopicHandler;
import wys.Http.HttpApi;
import wys.Http.IHttpApi;

public class WysUserApi {

	private static final int SUCCESS = 0;
	private static final int ERROR = 1;

	private static final String HOST = "http://";
	//private static final String DOMAIN = "192.168.0.7/WYS/api/";
	// private static final String DOMAIN = "129.107.144.192/WYS/api/";
	// private static final String DOMAIN = "10.226.50.83/WYS/api/";
	private static final String DOMAIN = "jangra.com.s10.dotnetpark.com/api/";


	private IHttpApi mHttpApi;

	private final DefaultHttpClient mHttpClient = HttpApi.CreateHttpclient();

	public String GetUrl(String path) {
		return HOST + DOMAIN + path;
	}
	
	public WysUserApi(){
		this.mHttpApi = new HttpApi(mHttpClient);
	}
	
	
	@SuppressLint("DefaultLocale")
	public ArrayList<TopicBo> doGetUserTopics(int userId)
	{
		String path = String.format(UrlManager.FETCH_USER_TOPICS_URL,
				userId);

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
			ArrayList<TopicBo> topics =user.getUserTopics();
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
	
	
	
	
	
}
