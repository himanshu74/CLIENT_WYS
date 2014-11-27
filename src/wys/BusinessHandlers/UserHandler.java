package wys.BusinessHandlers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import wys.Business.BaseBusiness;
import wys.Business.UserBo;

public class UserHandler extends Jsonhandler {

	private static final String CLASS_TAG = UserHandler.class.getSimpleName();

	@Override
	protected ArrayList<BaseBusiness> SaveToModal(String json) throws JSONException {
		ArrayList<BaseBusiness> userList = new ArrayList<BaseBusiness>();
		JSONArray jsonArray;
		
		if(isSingleResultExpected){
			JSONObject jsonObject =new JSONObject(json);
			userList.add(getUserBoFromJson(jsonObject));
			return userList;
		}
		
		try {
			jsonArray = new JSONArray(json);

			if (jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonObject = jsonArray.getJSONObject(i);
					UserBo user = getUserBoFromJson(jsonObject);
					userList.add(user);
				}
			}

		} catch (JSONException jsException) {
			Log.e(CLASS_TAG, "", jsException);
		}

		return userList;

	}
	
	private UserBo getUserBoFromJson(JSONObject jsonObject) throws JSONException{
		UserBo user = new UserBo();
		user.set_userId(jsonObject.getInt("UserId"));
		user.set_serverId(jsonObject.getInt("UserId"));
		user.set_username(jsonObject.getString("Username"));
		user.set_password(jsonObject.getString("Password"));
		user.set_email(jsonObject.getString("Email"));
		user.set_roleId(jsonObject.getInt("RoleId"));
		user.set_domainId(jsonObject.getInt("DomainId"));
		user.set_isVerified(jsonObject.getInt("IsVerified"));
		user.setToken(jsonObject.getString("Token"));
		
		return user;
	}

}
