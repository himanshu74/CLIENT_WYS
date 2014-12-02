package wys.BusinessHandlers;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;
import wys.Business.BaseBusiness;
import wys.Business.CommentBo;

public class CommentHandler extends Jsonhandler {

	private String CLASS_TAG = CommentHandler.class.getSimpleName();

	public ArrayList<BaseBusiness> SaveToModal(String json) {
		ArrayList<BaseBusiness> commentList = new ArrayList<BaseBusiness>();
		JSONArray jsonArray;


		try {
			jsonArray = new JSONArray(json);

			if (jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
					try {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						CommentBo com = getCommentFromJson(jsonObject);
						commentList.add(com);
					} catch (Exception ex) {
						Log.e(CLASS_TAG, "", ex);
					}

				}
			}

		} catch (JSONException jsException) {
			Log.e(CLASS_TAG, "", jsException);
		}

		return commentList;

	}

	private CommentBo getCommentFromJson(JSONObject jsonObject)
			throws JSONException {
		CommentBo comment = new CommentBo();
		comment.set_comment(jsonObject.getString("Comment"));
		comment.set_serverId(jsonObject.getInt("CommentId"));
		comment.set_topicId(jsonObject.getInt("TopicId"));
		comment.set_userId(jsonObject.getInt("UserId"));
		comment.setCommentId(jsonObject.getInt("CommentId"));
		comment.set_username(jsonObject.getString("Username"));
		comment.set_timeAdded(jsonObject.getString("TimeAdded"));

		return comment;
	}

}
