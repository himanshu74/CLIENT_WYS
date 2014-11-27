package wys.Http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.DefaultedHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import wys.Business.BaseBusiness;
import wys.BusinessHandlers.Jsonhandler;

public class HttpApi implements IHttpApi {

	private static final String CLASS_TAG = HttpApi.class.getSimpleName();
	private DefaultHttpClient mHttpApiClient;
	private static final int TIME_OUT = 60;

	public HttpApi(DefaultHttpClient httpClient) {
		this.mHttpApiClient = httpClient;
	}

	private static final HttpParams CreateHttpParams() {
		final HttpParams params = new BasicHttpParams();

		// Turn off stale checking. Our connections break all the time anyway,
		// and it's not worth it to pay the penalty of checking every time.
		HttpConnectionParams.setStaleCheckingEnabled(params, false);
		HttpConnectionParams.setConnectionTimeout(params, TIME_OUT * 1000);
		HttpConnectionParams.setSoTimeout(params, TIME_OUT * 1000);
		HttpConnectionParams.setSocketBufferSize(params, 8192);

		return params;
	}

	public static DefaultHttpClient CreateHttpclient() {
		DefaultHttpClient client = new DefaultHttpClient();
		final HttpParams params = CreateHttpParams();
		client.setParams(params);

		SchemeRegistry registry = new SchemeRegistry();
		SocketFactory sf = PlainSocketFactory.getSocketFactory();
		registry.register(new Scheme("http", sf, 80));
		ThreadSafeClientConnManager threadSafeClientConnManager = new ThreadSafeClientConnManager(
				client.getParams(), registry);
		DefaultHttpClient httpClient = new DefaultHttpClient(
				threadSafeClientConnManager, client.getParams());
		return httpClient;
	}

	public HttpResponse ExecuteHttpRequest(HttpRequestBase httpRequest) {
		HttpResponse response = null;
		try {
			mHttpApiClient.getConnectionManager().closeExpiredConnections();
			response = mHttpApiClient.execute(httpRequest);

		} catch (ClientProtocolException e) {
			Log.e(CLASS_TAG, "", e);
		} catch (IOException e) {
			Log.e(CLASS_TAG, "", e);
		}

		return response;
	}

	@Override
	public ArrayList<? extends BaseBusiness> DoHttpRequestJson(
			HttpRequestBase httpRequest, Jsonhandler parser) {

		HttpResponse response = ExecuteHttpRequest(httpRequest);
		if (response != null) {
			int statusCode = response.getStatusLine().getStatusCode();

			switch (statusCode) {
			case 200:
				if (parser != null) {
					try {
						return parser.Parse(response);
					} catch (Exception ex) {
						Log.e(CLASS_TAG, "", ex);
					}
				}

			default:
				return null;
			}

		}

		return null;

	}

	@Override
	public String DoHttpPost(String url, NameValuePair... nameValuePairs) {

		HttpPost httPost = CreateHttpPost(url, nameValuePairs);
		HttpResponse response = ExecuteHttpRequest(httPost);
		if (response != null) {
			switch (response.getStatusLine().getStatusCode()) {
			case 200:
				try {
					return EntityUtils.toString(response.getEntity());
				} catch (Exception ex) {
					Log.e(CLASS_TAG, "", ex);
				}
				break;

			case 204:
				return "0";
			case 503: {
				return "1";
			}
			default:
				break;
			}
		}
		return null;
	}

	public String DoHttpPostJson(String url, JSONObject json) {
		JSONObject jsonPostResponse = null;
		HttpPost httpPost = createHttpJsonPost(url, json);
		HttpResponse response = ExecuteHttpRequest(httpPost);
		if (response != null) {
			switch (response.getStatusLine().getStatusCode()) {
			case 200:
				try {
					return EntityUtils.toString(response.getEntity());

				} catch (Exception ex) {
					Log.e(CLASS_TAG, "", ex);
				}

				break;

			case 204:

				return "0";

			default:
				break;
			}
		}
		return null;
	}

	@Override
	public HttpPost CreateHttpPost(String url, NameValuePair... nameValuePairs) {

		HttpPost httpPost = new HttpPost(url);

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(
					StripNulls(nameValuePairs), HTTP.UTF_8));

		} catch (UnsupportedEncodingException ue) {
			Log.e(CLASS_TAG, "", ue);
		}
		return httpPost;
	}

	public HttpPost createHttpJsonPost(String url, JSONObject json) {
		HttpPost httpPost = new HttpPost(url);

		try {
			StringEntity entity = new StringEntity(json.toString());
			httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
			httpPost.setEntity(entity);

		} catch (UnsupportedEncodingException ue) {
			Log.e(CLASS_TAG, "", ue);
		}
		return httpPost;

	}

	@Override
	public HttpGet CreateHttpGet(String url, NameValuePair... nameValuePairs) {

		String query = URLEncodedUtils.format(StripNulls(nameValuePairs),
				HTTP.UTF_8);

		HttpGet httpGet = null;

		if (query.equals(null) || query.length() == 0) {
			httpGet = new HttpGet(url);
			return httpGet;
		}

		else {
			httpGet = new HttpGet(url + "&" + query);
			return httpGet;
		}

	}

	public List<NameValuePair> StripNulls(NameValuePair... nameValuePairs) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		for (NameValuePair pair : nameValuePairs) {
			if (!pair.getValue().equals(null)) {
				params.add(pair);
			}
		}
		return params;
	}

}
