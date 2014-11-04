package wys.BusinessHandlers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import wys.Business.BaseBusiness;
import wys.Business.CategoryBo;
import wys.Business.TopicBo;
import android.util.Log;

public class TopicHandler extends Jsonhandler {

	private static String CLASS_TAG = TopicHandler.class.getSimpleName();

	public ArrayList<BaseBusiness> SaveToModal(String json) {
		ArrayList<BaseBusiness> topicList = new ArrayList<BaseBusiness>();
		JSONArray jsonArray;

		if (isSingleResultExpected) {

			try {
				JSONObject jsonObject = new JSONObject(json);
				topicList.add(getTopicBoFromJson(jsonObject));
				return topicList;
			} catch (Exception ex) {
				Log.e(CLASS_TAG, "", ex);
			}

		}

		try {
			jsonArray = new JSONArray(json);

			if (jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
					try {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						TopicBo topic = getTopicBoFromJson(jsonObject);
						topicList.add(topic);
					} catch (Exception ex) {
						Log.e(CLASS_TAG, "", ex);
					}

				}
			}

		} catch (JSONException jsException) {
			Log.e(CLASS_TAG, "", jsException);
		}

		return topicList;

	}

	private TopicBo getTopicBoFromJson(JSONObject json) throws JSONException,
			ParseException {
		TopicBo topicBo = new TopicBo();
		topicBo.set_topicId(json.getInt("TopicId"));
		topicBo.set_domainId(json.getInt("DomainId"));
		topicBo.set_name(json.getString("Name"));
		topicBo.set_userId(json.getInt("UserId"));
		topicBo.set_dateAdded(ConvertJsonToDate(json.getString("DateAdded")));
		topicBo.set_beginDate(ConvertJsonToDate(json.getString("BeginDate")));
		topicBo.set_conclusion(json.getString("Conclusion"));

		return topicBo;

	}

	private Date ConvertJsonToDate(String jsondate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(jsondate);
		return date;
	}

}
