package wys.BusinessHandlers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import wys.Business.BaseBusiness;
import wys.Business.CategoryBo;

public class CategoryHandler extends Jsonhandler {

	private String CLASS_TAG = CategoryHandler.class.getSimpleName();

	public ArrayList<BaseBusiness> SaveToModal(String json) {
		ArrayList<BaseBusiness> catList = new ArrayList<BaseBusiness>();
		JSONArray jsonArray;

		if (isSingleResultExpected) {

			try {
				JSONObject jsonObject = new JSONObject(json);
				catList.add(getCategoryBoFromJson(jsonObject));
				return catList;
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
						CategoryBo cat = getCategoryBoFromJson(jsonObject);
						catList.add(cat);
					} catch (Exception ex) {
						Log.e(CLASS_TAG, "", ex);
					}

				}
			}

		} catch (JSONException jsException) {
			Log.e(CLASS_TAG, "", jsException);
		}

		return catList;

	}

	private CategoryBo getCategoryBoFromJson(JSONObject json)
			throws JSONException, ParseException {
		CategoryBo categoryBo = new CategoryBo();
		categoryBo.set_categoryId(json.getInt("CategoryId"));
		categoryBo
				.set_dateAdded(ConvertJsonToDate(json.getString("DateAdded")));
		categoryBo.set_categoryName(json.getString("CategoryName"));
		categoryBo.set_dateDeleted(ConvertJsonToDate(json
				.getString("DateDeleted")));
		categoryBo.set_dateModified(ConvertJsonToDate(json
				.getString("DateModified")));
		categoryBo.set_isDeleted(json.getInt("IsDeleted"));
		return categoryBo;

	}

	private Date ConvertJsonToDate(String jsondate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(jsondate);
		return date;
	}

}
