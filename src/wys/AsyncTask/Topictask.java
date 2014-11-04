package wys.AsyncTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import wys.Api.SessionManager;
import wys.Api.WysApi;
import wys.Business.BaseBusiness;
import wys.Business.TopicBo;
import wys.Business.UserBo;
import wys.CustomInterfaces.OnGetTopicsListener;
import wys.CustomInterfaces.OnTopicPostListener;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class Topictask extends BaseAsyncTaskManager {

	private OnGetTopicsListener onGetTopicsListener;
	OnTopicPostListener listener;
	private Context _ctx;
	private ProgressDialog progressDialog;

	public Topictask(Context context, OnGetTopicsListener onGetTopicsListener) {
		this.onGetTopicsListener = onGetTopicsListener;
		this._ctx = context;
	}

	public void setOntopicPostListener(OnTopicPostListener onTopicPostListener) {
		this.listener = onTopicPostListener;
	}

	public Topictask(Context context) {
		this._ctx = context;
	}

	public OnTopicPostListener getOnTopicPostListener() {
		return this.listener;
	}

	public void executeGetTopics(int id) {
		new TopicAsync().execute(id);
	}

	public void executePostTopics(String topic, int domainid)
			throws ParseException {
		TopicBo topicBo = new TopicBo();
		UserBo user = (UserBo) SessionManager.getUserBo();
		topicBo.set_name(topic);
		topicBo.set_domainId(domainid);
		topicBo.set_userId(user.get_userId());
		/*
		 * String date = new Date().; SimpleDateFormat sdf = new
		 * SimpleDateFormat("yyyy-MM-dd");
		 * topicBo.set_beginDate(sdf.parse(date));
		 */
		new TopicPostAsync().execute(topicBo);
	}

	private class TopicAsync extends
			AsyncTask<Integer, Void, ArrayList<BaseBusiness>> {

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(_ctx);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage("Fetching topics...");
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(false);
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected ArrayList<BaseBusiness> doInBackground(Integer... params) {

			ArrayList<BaseBusiness> list = new WysApi().getTopics(params[0]);

			return list;
		}

		@Override
		protected void onPostExecute(ArrayList<BaseBusiness> result) {
			progressDialog.dismiss();
			if (result != null) {
				if (onGetTopicsListener != null) {
					onGetTopicsListener.onTopicsReceived(result);
				}
			} else {
				if (onGetTopicsListener != null) {
					onGetTopicsListener.onTopicsNotReceived();
				}
			}

			super.onPostExecute(result);
		}

	}

	private class TopicPostAsync extends AsyncTask<TopicBo, Void, Integer> {

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(_ctx);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage("posting...");
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(false);
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(TopicBo... params) {

			int status = new WysApi().PostTopic(params[0]);

			return status;
		}

		@Override
		protected void onPostExecute(Integer result) {
			progressDialog.dismiss();
			if (result == SUCCESS) {
				if (listener != null) {
					listener.onTopicPosted();
				}
			} else if (result == ERROR) {
				if (listener != null) {
					listener.onTopicPostError();
				}
			}
			super.onPostExecute(result);
		}

	}

}