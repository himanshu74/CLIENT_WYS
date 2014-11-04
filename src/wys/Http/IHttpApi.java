package wys.Http;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.json.JSONObject;

import wys.Business.BaseBusiness;
import wys.BusinessHandlers.Jsonhandler;
import wys.Modals.AppModal;

public interface IHttpApi {

	public ArrayList<? extends BaseBusiness> DoHttpRequestJson(HttpRequestBase httpRequest, Jsonhandler parser);

	public String DoHttpPost(String url, NameValuePair... nameValuePairs );
	
	public HttpPost CreateHttpPost(String url, NameValuePair... nameValuePairs);
	
	public HttpGet CreateHttpGet(String url,NameValuePair... nameValuePairs);
	
	public HttpResponse ExecuteHttpRequest(HttpRequestBase httpRequest);
	
	public String DoHttpPostJson(String url,JSONObject jsonObject);
	
	public HttpPost createHttpJsonPost(String url, JSONObject jsonObject);
	
}
