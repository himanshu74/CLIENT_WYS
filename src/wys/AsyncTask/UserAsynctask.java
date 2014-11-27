package wys.AsyncTask;

import java.util.ArrayList;

import wys.Api.WysApi;
import wys.Api.WysUserApi;
import wys.Business.CategoryBo;
import wys.Business.UserBo;
import wys.CustomInterfaces.OnResponseReceived;
import wys.CustomInterfaces.OnUserCatPost;
import wys.DatabaseHelpers.DBAdapter;
import wys.Modals.CategoryModal;
import wys.Modals.TopicModal;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class UserAsynctask extends BaseAsyncTaskManager {

	private ProgressDialog progressDialog;
	private Context _ctx;
	private DBAdapter dbAdapter;
	private OnUserCatPost _onUserCatPost;
	private OnResponseReceived _onOnResponseReceived;

	public OnResponseReceived get_onOnResponseReceived() {
		return _onOnResponseReceived;
	}

	public void set_onOnResponseReceived(
			OnResponseReceived _onOnResponseReceived) {
		this._onOnResponseReceived = _onOnResponseReceived;
	}

	public UserAsynctask(Context context, DBAdapter dbAdapter,
			OnUserCatPost onUserCatPost) {
		this._ctx = context;
		this.dbAdapter = dbAdapter;
		this._onUserCatPost = onUserCatPost;
	}

	public UserAsynctask(Context context, DBAdapter dbAdapter) {
		this._ctx = context;
		this.dbAdapter = dbAdapter;
	}

	public void executePostUserDeletedCategories(UserBo user) {
		new PostDeletedCategories().execute(user);
	}

	public void executePostUserCategories(UserBo user) {
		/*
		 * CategoryBo cat1 = new CategoryBo(); cat1.set_categoryId(1);
		 * CategoryBo cat2 = new CategoryBo(); cat2.set_categoryId(2);
		 * 
		 * ArrayList<CategoryBo> categories = new ArrayList<CategoryBo>();
		 * categories.add(cat1);
		 * 
		 * categories.add(cat2);
		 * 
		 * UserBo user = new UserBo(); user.setUserCategories(categories);
		 * user.set_userId(1);
		 */
		new PostCategories().execute(user);
	}

	public void executePostJoinTopic(UserBo user)
	{
		new PostJoinTopic().execute(user);
	}
	
	
	private class PostCategories extends AsyncTask<UserBo, Void, Integer> {

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(_ctx);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage("Posting Selection...");
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(false);
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(UserBo... params) {
			boolean isSaved = false;
			int status = new WysApi().postUserCategories(params[0]);
			if (status == SUCCESS) {
				UserBo user = params[0];
				int length = user.getUserCategories().size();
				for (int i = 0; i < length; i++) {
					isSaved = CategoryModal.saveCatUser(dbAdapter.getDb(), user
							.getUserCategories().get(i).get_serverId(),
							user.get_userId());
				}
				if (!isSaved) {
					status = ERROR;
				}
			}
			return status;
		}

		@Override
		protected void onPostExecute(Integer result) {
			progressDialog.dismiss();
			if (result == SUCCESS) {
				if (_onUserCatPost != null) {
					_onUserCatPost.onPostSuccess();
				}
			} else {
				if (_onUserCatPost != null) {
					_onUserCatPost.onPostFail();
				}
			}
			super.onPostExecute(result);
		}
	}

	private class PostDeletedCategories extends
			AsyncTask<UserBo, Void, Integer> {

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(_ctx);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage("Deleting Selection...");
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(false);
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(UserBo... params) {
			boolean isDeleted = false;
			int status = new WysApi().postUserDeletedCategories(params[0]);
			if (status == SUCCESS) {
				UserBo user = params[0];
				int length = user.getUserCategories().size();
				for (int i = 0; i < length; i++) {
					isDeleted = CategoryModal.DeleteUserCats(dbAdapter.getDb(),
							user.getUserCategories().get(i).get_serverId(),
							user.get_userId());
				}
				if (!isDeleted) {
					status = ERROR;
				}
			}
			return status;
		}

		@Override
		protected void onPostExecute(Integer result) {
			progressDialog.dismiss();
			if (result == SUCCESS) {
				if (_onOnResponseReceived != null) {
					_onOnResponseReceived.onResponseSuccess();
				}
			} else {
				if (_onOnResponseReceived != null) {
					_onOnResponseReceived.onResponseFailure();
				}
			}
			super.onPostExecute(result);

		}

	}

	private class PostJoinTopic extends AsyncTask<UserBo, Void, Integer> {

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(_ctx);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage("Posting Selection...");
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(false);
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(UserBo... params) {

			boolean isSaved = false;
			int status = new WysUserApi().postUserTopics(params[0]);
			if (status == SUCCESS) {
				UserBo user = params[0];
				int length = user.getUserTopics().size();
				for (int i = 0; i < length; i++) {
					isSaved = TopicModal.saveTopicUser(dbAdapter.getDb(), user
							.getUserTopics().get(i).get_serverId(),
							user.get_userId());
				}
				if (!isSaved) {
					status = ERROR;
				}
			}
			return status;
		}

		@Override
		protected void onPostExecute(Integer result) {
			progressDialog.dismiss();
			if (result == SUCCESS) {
				if (_onOnResponseReceived != null) {
					_onOnResponseReceived.onResponseSuccess();
				}
			} else {
				if (_onOnResponseReceived != null) {
					_onOnResponseReceived.onResponseFailure();
				}
			}
			super.onPostExecute(result);
		}

	}

}
