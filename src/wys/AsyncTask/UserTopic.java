package wys.AsyncTask;

import java.util.ArrayList;

import wys.Api.SessionManager;
import wys.Api.WysUserApi;
import wys.Business.TopicBo;
import wys.Business.UserBo;
import wys.CustomInterfaces.OnGetTopicsListener;
import wys.CustomInterfaces.OnTopicPostListener;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class UserTopic {

	private OnGetTopicsListener _ongetGetTopicsListener;
	private ProgressDialog progressDialog;
	private Context _ctx;

	public UserTopic(OnGetTopicsListener ongetGetTopicsListener, Context context) {
		this._ongetGetTopicsListener = ongetGetTopicsListener;
		this._ctx = context;
	}

	public void executeGetCurrentTopic(int catId) {
		new UserCurrentAsync().execute(catId);
	}

	public void executeGetUpcomingTopic(int catId) {
		new UserUpcomingAsync().execute(catId);
	}

	public void executeGetClosedTopics(int catId) {
		new UserClosedAsync().execute(catId);
	}

	private class UserCurrentAsync extends
			AsyncTask<Integer, Void, ArrayList<TopicBo>> {

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(_ctx);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage("Refereshing topics...");
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(false);
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected ArrayList<TopicBo> doInBackground(Integer... params) {
			UserBo user = (UserBo) SessionManager.getUserBo();

			WysUserApi wysUserApi = new WysUserApi();
			ArrayList<TopicBo> list = wysUserApi.getUserCurrentTopics(
					user.get_userId(), params[0]);

			return list;
		}

		@Override
		protected void onPostExecute(ArrayList<TopicBo> result) {
			progressDialog.dismiss();
			if (result != null) {
				if (_ongetGetTopicsListener != null) {
					_ongetGetTopicsListener.onTopicsReceived(result);
				}
			} else {
				if (_ongetGetTopicsListener != null) {
					_ongetGetTopicsListener.onTopicsNotReceived();
				}
			}

			super.onPostExecute(result);
		}

	}

	private class UserUpcomingAsync extends
			AsyncTask<Integer, Void, ArrayList<TopicBo>> {

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(_ctx);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage("Refereshing topics...");
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(false);
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected ArrayList<TopicBo> doInBackground(Integer... params) {
			UserBo user = (UserBo) SessionManager.getUserBo();

			WysUserApi wysUserApi = new WysUserApi();
			ArrayList<TopicBo> list = wysUserApi.getUserUpcomingTopics(
					user.get_userId(), params[0]);

			return list;

		}

		@Override
		protected void onPostExecute(ArrayList<TopicBo> result) {
			progressDialog.dismiss();
			if (result != null) {
				if (_ongetGetTopicsListener != null) {
					_ongetGetTopicsListener.onTopicsReceived(result);
				}
			} else {
				if (_ongetGetTopicsListener != null) {
					_ongetGetTopicsListener.onTopicsNotReceived();
				}

			}

			super.onPostExecute(result);
		}
	}

	private class UserClosedAsync extends
			AsyncTask<Integer, Void, ArrayList<TopicBo>> {

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(_ctx);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage("Refereshing topics...");
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(false);
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected ArrayList<TopicBo> doInBackground(Integer... params) {
			UserBo user = (UserBo) SessionManager.getUserBo();

			WysUserApi wysUserApi = new WysUserApi();
			ArrayList<TopicBo> list = wysUserApi.getUserUpcomingTopics(
					user.get_userId(), params[0]);

			return list;

		}

		@Override
		protected void onPostExecute(ArrayList<TopicBo> result) {
			progressDialog.dismiss();
			if (result != null) {
				if (_ongetGetTopicsListener != null) {
					_ongetGetTopicsListener.onTopicsReceived(result);
				}
			} else {
				if (_ongetGetTopicsListener != null) {
					_ongetGetTopicsListener.onTopicsNotReceived();
				}

			}
			super.onPostExecute(result);
		}
	}

}
