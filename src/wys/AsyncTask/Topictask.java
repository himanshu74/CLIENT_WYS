package wys.AsyncTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import wys.Api.SessionManager;
import wys.Api.WysApi;
import wys.Background.GcmMessageHandler;
import wys.Business.BaseBusiness;
import wys.Business.TopicBo;
import wys.Business.UserBo;
import wys.CustomInterfaces.OnGetTopicsListener;
import wys.CustomInterfaces.OnTopicPostListener;
import wys.DatabaseHelpers.DBAdapter;
import wys.Helpers.WysConstants;
import wys.Helpers.WysDateConversioHelper;
import wys.Modals.TopicModal;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class Topictask extends BaseAsyncTaskManager {

	private OnGetTopicsListener onGetTopicsListener;
	OnTopicPostListener listener;
	private Context _ctx;
	private ProgressDialog progressDialog;
	private DBAdapter dbAdapter;

	public Topictask(Context context, OnGetTopicsListener onGetTopicsListener,
			DBAdapter dbAdapter) {
		this.onGetTopicsListener = onGetTopicsListener;
		this._ctx = context;
		this.dbAdapter = dbAdapter;
	}

	public void setOntopicPostListener(OnTopicPostListener onTopicPostListener) {
		this.listener = onTopicPostListener;
	}

	public Topictask(Context context, DBAdapter dbAdapter) {
		this._ctx = context;
		this.dbAdapter = dbAdapter;
	}

	public OnTopicPostListener getOnTopicPostListener() {
		return this.listener;
	}

	public DBAdapter getDbAdapter() {
		return dbAdapter;
	}

	public void setDbAdapter(DBAdapter dbAdapter) {
		this.dbAdapter = dbAdapter;
	}

	public void executeGetTopics(int id) {
		// new TopicAsync().execute(id);
	}

	public void executeGetAllTopics() {
		new FetchAllTopicsAsync().execute();
	}

	public void executePostTopics(String topic, String keyword, int domainid,
			long dateUnixUTC) throws ParseException {
		TopicBo topicBo = new TopicBo();
		UserBo user = (UserBo) SessionManager.getUserBo();
		topicBo.set_name(topic);
		topicBo.set_keyword(keyword);
		topicBo.set_domainId(domainid);
		topicBo.set_userId(user.get_userId());
		topicBo.set_bgeindateUnixUTC(dateUnixUTC);
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
					onGetTopicsListener.onTopicsReceived();
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
			int status = -1;
			int InsertedServerId = new WysApi().PostTopic(params[0]);
			if (InsertedServerId != -1) {
				params[0].set_serverId(InsertedServerId);
				params[0]
						.set_beginDateString(WysDateConversioHelper
								.getStringFormatedDate(params[0]
										.get_bgeindateUnixUTC()));
				boolean result = TopicModal.saveTopic(getDbAdapter().getDb(),
						params[0]);
				if (result) {
					status = SUCCESS;
				} else {
					status = ERROR;
				}
			} else {
				status = ERROR;
			}
			return status;
		}

		@Override
		protected void onPostExecute(Integer result) {
			progressDialog.dismiss();
			if (result == SUCCESS) {
				GcmMessageHandler.generateNotification(
						_ctx.getApplicationContext(), "New Topic Posted");
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

	private class FetchAllTopicsAsync extends AsyncTask<Void, Void, Integer> {

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(_ctx);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage("fetching Topics...");
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(false);
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(Void... params) {
			progressDialog.dismiss();
			boolean status = false;
			ArrayList<TopicBo> topics = new WysApi().getAllTopics();

			if (topics.size() > 0) {

				for (TopicBo topic : topics) {
					status = TopicModal.saveTopic(dbAdapter.getDb(), topic);
				}

				if (status) {
					return SUCCESS;
				} else {
					return ERROR;
				}

			} else {
				return EMPTY_SERVER_RECORD;
			}

		}

		@Override
		protected void onPostExecute(Integer result) {
			progressDialog.dismiss();
			if (result == SUCCESS) {
				if (onGetTopicsListener != null) {
					onGetTopicsListener.onTopicsReceived();
				}
			} else if (result == ERROR) {
				if (onGetTopicsListener != null) {
					onGetTopicsListener.onTopicsNotReceived();
				}

			}

			else if (result == EMPTY_SERVER_RECORD) {
				if (onGetTopicsListener != null) {
					onGetTopicsListener.onEmptyServerRecord();
				}
			}
			super.onPostExecute(result);
		}

	}

}