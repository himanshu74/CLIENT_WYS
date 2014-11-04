package wys.BusinessHandlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import wys.Business.BaseBusiness;

public class Jsonhandler {

	private static final String CLASS_TAG = Jsonhandler.class.getSimpleName();
	public int ResponseCode = 200;
	public Boolean isSingleResultExpected=false;
    
	private void SetValuesFromHeader(HttpResponse response) {

		if (!response.getStatusLine().equals(null)) {
			ResponseCode = response.getStatusLine().getStatusCode();
		}

	}

	public ArrayList<? extends BaseBusiness> Parse(HttpResponse response) throws JSONException {
		SetValuesFromHeader(response);
		ArrayList<BaseBusiness> busObject = null;
		if (ResponseCode != 204 && ResponseCode != 304) {
			try {
				String responseString = ConvertStreamToString(response
						.getEntity().getContent());
				busObject = SaveToModal(responseString);
			} catch (IllegalStateException e) {
				Log.e(CLASS_TAG, "", e);

			} catch (IOException e) {
				Log.e(CLASS_TAG, "", e);

			}
		}
		return busObject;
	}

	private String ConvertStreamToString(InputStream inStream) {
		/*
		 * To convert the InputStream to String we use the
		 * BufferedReader.readLine() method. We iterate until the BufferedReader
		 * return null which means there's no more data to read. Each line will
		 * appended to a StringBuilder and returned as String.
		 */

		BufferedReader buReader = null;
		try {

			buReader = new BufferedReader(new InputStreamReader(inStream,
					"UTF-8"));
		} catch (Exception exception) {
			Log.e(CLASS_TAG, "", exception);
		}

		StringBuilder sb = new StringBuilder();

		String line = null;

		try {
			while ((line = buReader.readLine()) != null) {
				sb.append(line + "\n");
			}
		}

		catch (IOException io) {
			Log.e(CLASS_TAG, "", io);
		}

		finally {
			try {
				inStream.close();
			} catch (IOException ioException) {
				Log.e(CLASS_TAG, "", ioException);
			}

		}
		return sb.toString();
	}

	protected ArrayList<BaseBusiness> SaveToModal(String json) throws JSONException {
		return null;

	}

}
