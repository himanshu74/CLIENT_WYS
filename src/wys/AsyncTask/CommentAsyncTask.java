package wys.AsyncTask;

import java.util.ArrayList;

import wys.Api.WysUserApi;
import wys.Business.CommentBo;
import wys.Business.TopicBo;
import wys.CustomInterfaces.OnGetCommentListener;
import wys.CustomInterfaces.OnResponseReceived;
import wys.DatabaseHelpers.DBAdapter;
import wys.Helpers.PreferenceHelper;
import wys.Modals.CommentModal;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class CommentAsyncTask extends BaseAsyncTaskManager {

	private Context _ctx;
	private OnResponseReceived _onOnResponseReceived;
	private OnGetCommentListener _onCommentListener;
	private ProgressDialog progressDialog;
	private DBAdapter _dbAdapter;

	public OnResponseReceived get_onOnResponseReceived() {
		return _onOnResponseReceived;
	}

	public void set_onOnResponseReceived(
			OnResponseReceived _onOnResponseReceived) {
		this._onOnResponseReceived = _onOnResponseReceived;
	}

	public OnGetCommentListener get_onCommentListener() {
		return _onCommentListener;
	}

	public void set_onCommentListener(OnGetCommentListener _onCommentListener) {
		this._onCommentListener = _onCommentListener;
	}

	public CommentAsyncTask(Context context, DBAdapter dbAdapter) {
		this._ctx = context;
		this._dbAdapter = dbAdapter;
	}

	public void executePostComment(CommentBo commentBo) {
		new PostCommentASync().execute(commentBo);
	}

	public void executeGetCommentByTopic(int topic) {
		new CommentAsync().execute(topic);
	}

	public void executeGetLatestComment(int topicId, int commentServerId) {
		new LatestCommentAsync().execute(topicId, commentServerId);
	}

	public void execteGetClosedComments(int topicId) {
		new CLosedCommentsAsync().execute(topicId);
	}

	private class PostCommentASync extends AsyncTask<CommentBo, Void, Integer> {

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(_ctx);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage("Posting Comment...");
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(false);
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(CommentBo... params) {

			int status = new WysUserApi().PostComment(params[0]);

			return status;
		}

		@Override
		protected void onPostExecute(Integer result) {
			progressDialog.dismiss();
			super.onPostExecute(result);
			if (result == SUCCESS) {
				if (get_onOnResponseReceived() != null) {
					get_onOnResponseReceived().onResponseSuccess();
				}

			} else {
				if (get_onOnResponseReceived() != null) {
					get_onOnResponseReceived().onResponseFailure();
					Toast.makeText(_ctx, "OOPS !! SERVER NOT RESPONDING",
							Toast.LENGTH_LONG).show();
				}
			}

		}

	}

	private class CommentAsync extends AsyncTask<Integer, Void, Integer> {

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(_ctx);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage("Fetching Comments...");
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(false);
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(Integer... params) {
			ArrayList<CommentBo> comments = new WysUserApi()
					.getcommentsBytopic(params[0]);
			boolean status = false;
			if (comments != null && comments.size() > 0) {
				PreferenceHelper pref = new PreferenceHelper(_ctx);
				if (pref.get_commentsServerId() != comments.get(0)
						.get_serverId()) {
					pref.set_commentsServerId(comments.get(0).get_serverId());
					for (CommentBo commentBo : comments) {
						status = CommentModal.saveComment(_dbAdapter.getDb(),
								commentBo);
					}
				}

				pref = null;

			}

			if (status) {
				return SUCCESS;
			} else {
				return ERROR;
			}

		}

		@Override
		protected void onPostExecute(Integer result) {
			progressDialog.dismiss();
			if (result == SUCCESS) {
				if (get_onCommentListener() != null) {
					get_onCommentListener().onCommentsReceived();
				}
			} else {
				if (get_onCommentListener() != null) {
					get_onCommentListener().onCommentNotReceived();
				}
			}

			super.onPostExecute(result);
		}

	}

	private class LatestCommentAsync extends AsyncTask<Integer, Void, Integer> {

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(_ctx);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage("Fecthing Latest Comments...");
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(false);
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(Integer... params) {
			ArrayList<CommentBo> comments = new WysUserApi().getLatestComments(
					params[0], params[1]);
			boolean status = false;
			if (comments != null && comments.size() > 0) {
				PreferenceHelper pref = new PreferenceHelper(_ctx);
				if (pref.get_commentsServerId() != comments.get(0)
						.get_serverId()) {
					pref.set_commentsServerId(comments.get(0).get_serverId());
					for (CommentBo commentBo : comments) {
						status = CommentModal.saveComment(_dbAdapter.getDb(),
								commentBo);
					}
				}

				pref = null;

			}

			if (status) {
				return SUCCESS;
			} else {
				return ERROR;
			}

		}

		@Override
		protected void onPostExecute(Integer result) {
			progressDialog.dismiss();
			if (result == SUCCESS) {
				if (get_onCommentListener() != null) {
					get_onCommentListener().onCommentsReceived();
				}
			} else {
				if (get_onCommentListener() != null) {
					get_onCommentListener().onCommentNotReceived();
				}
			}

			super.onPostExecute(result);
		}

	}

	private class CLosedCommentsAsync extends
			AsyncTask<Integer, Void, ArrayList<CommentBo>> {

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(_ctx);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage("Fecthing Comments...");
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(false);
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected ArrayList<CommentBo> doInBackground(Integer... params) {
			ArrayList<CommentBo> comments = new WysUserApi()
					.getcommentsBytopic(params[0]);
			return comments;
		}

		@Override
		protected void onPostExecute(ArrayList<CommentBo> result) {
			progressDialog.dismiss();
			if (result != null) {
				if (get_onCommentListener() != null) {
					get_onCommentListener().onCLosedCommentsReceived(result);
				}
			} else {
				if (get_onCommentListener() != null) {
					get_onCommentListener().onCommentNotReceived();
				}
			}
			super.onPostExecute(result);
		}

	}

}
