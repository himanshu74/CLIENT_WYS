package wys.AsyncTask;

import java.util.ArrayList;

import wys.Api.SessionManager;
import wys.Api.WysUserApi;
import wys.Business.TopicBo;
import wys.Business.UserBo;
import wys.CustomInterfaces.OnGetTopicsListener;
import wys.CustomInterfaces.OnResponseReceived;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class OrgAsync extends BaseAsyncTaskManager {
	private OnGetTopicsListener _ongetGetTopicsListener;
	private OnResponseReceived _onOnResponseReceived;
	public OnResponseReceived get_onOnResponseReceived() {
		return _onOnResponseReceived;
	}

	public void set_onOnResponseReceived(OnResponseReceived _onOnResponseReceived) {
		this._onOnResponseReceived = _onOnResponseReceived;
	}

	private ProgressDialog progressDialog;
	private Context _ctx;

	public OrgAsync(Context context, OnGetTopicsListener onGetTopicsListener) {
		this._ctx = context;
		this._ongetGetTopicsListener = onGetTopicsListener;
	}

	public OrgAsync(Context context){
		this._ctx = context;
	}
	
	public void executeGetOrgUpTopics(int catid) {
		new OrgUpcomingTopics().execute(catid);
	}

	public void executeGetOrgCurrentTopics(int catid) {
		new OrgCurrentTopics().execute(catid);
	}

	public void executeGetOrgClosedTopics(int catid) {
		new OrgclosedTopics().execute(catid);
	}
	
	public void executePostCOnclusion(TopicBo topicBo){
		new OrgPostConclusion().execute(topicBo);
	}

	private class OrgUpcomingTopics extends
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
			ArrayList<TopicBo> list = wysUserApi.GetOrgUpcomingTopics(
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

	private class OrgCurrentTopics extends
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
			ArrayList<TopicBo> list = wysUserApi.GetOrgCurrentTopics(
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

	private class OrgclosedTopics extends
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
			ArrayList<TopicBo> list = wysUserApi.GetOrgClosedTopics(
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

	private class OrgPostConclusion extends AsyncTask<TopicBo, Void, Integer> {

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(_ctx);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage("Posting Conclusion...");
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(false);
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(TopicBo... params) {
			WysUserApi wysUserApi = new WysUserApi();
			int status = wysUserApi.postConclusion(params[0]);
			return status;
		}

		@Override
		protected void onPostExecute(Integer result) {
			progressDialog.dismiss();
			if (result != null) {
				if (get_onOnResponseReceived() != null) {
					get_onOnResponseReceived().onResponseSuccess();
				}
			} else {
				if (get_onOnResponseReceived() != null) {
					get_onOnResponseReceived().onResponseFailure();
				}
			}
			super.onPostExecute(result);
		}

	}

}
