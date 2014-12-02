package wys.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import wys.Api.SessionManager;
import wys.Api.WysApi;
import wys.Business.BaseBusiness;
import wys.Business.UserBo;
import wys.CustomInterfaces.OnResetPassListener;
import wys.CustomInterfaces.OnSIgnInListener;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class SignInTask extends BaseAsyncTaskManager {

	private OnSIgnInListener _onSignInListener;
	private OnResetPassListener onResetPassListener;
	private UserBo _user;
	private boolean _isVerified = false;
	private int _roleID = -1;
	private ProgressDialog progressDialog;
	private Context _ctx;

	public SignInTask(OnSIgnInListener onSIgnInListener, Context context) {
		this._onSignInListener = onSIgnInListener;
		this._ctx = context;
	}

	public SignInTask(OnSIgnInListener onSIgnInListener, UserBo user,
			Context ctx) {
		this._onSignInListener = onSIgnInListener;
		this._user = user;
		this._ctx = ctx;
	}

	public SignInTask() {

	}

	public void ExecuteSignIn() {
		if (SessionManager.getUserBo() != null) {
			UserBo user = (UserBo) SessionManager.getUserBo();
			new SignInAsync().execute(user);
		} else {

			new SignInAsync().execute(this._user);
		}

	}

	public void executeResetPass(String username) {
		new ResetPassAsync().execute(username);
	}

	public void setOnResetPassListener(OnResetPassListener onResetPassListener) {
		this.onResetPassListener = onResetPassListener;
	}

	public OnResetPassListener getOnResetPassListener() {
		return this.onResetPassListener;
	}

	private class SignInAsync extends AsyncTask<UserBo, Void, Integer> {
		@Override
		protected Integer doInBackground(UserBo... params) {

			int result = -1;
			ArrayList<BaseBusiness> users = new WysApi().DoSignIn(
					params[0].get_username(), params[0].get_password());

			if (users != null && users.size() > 0) {
				result = SUCCESS;
				UserBo user = (UserBo) users.get(0);
				if (user.get_isVerified() == VERIFIED) {
					_isVerified = true;
					_roleID = user.get_roleId();
				}
			} else {
				return ERROR;
			}

			return result;
		}

		@Override
		protected void onPreExecute() {

			progressDialog = new ProgressDialog(_ctx);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage("Logging in...");
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(false);
			progressDialog.show();

			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Integer result) {
			progressDialog.dismiss();

			if (result == SUCCESS) {
				if (_isVerified) {
					if (_roleID == USER_ROLE_ID && _onSignInListener != null) {
						_onSignInListener.OnUserSignIN();
					} else if (_roleID == ORG_ROLE_ID
							&& _onSignInListener != null) {
						_onSignInListener.OnOrgSignIN();
					}
				} else {
					if (_onSignInListener != null) {
						_onSignInListener.OnStillNotVerified();
					}
				}
			} else if (result == SERVER_ERROR) {
				Toast.makeText(_ctx, "OOPS !! SERVER NOT RESPONDING",
						Toast.LENGTH_SHORT).show();
			} else if (result == ERROR) {
				if (_onSignInListener != null) {
					_onSignInListener.OnSignInFail();
				}
				super.onPostExecute(result);
			}

		}
	}

	private class ResetPassAsync extends AsyncTask<String, Void, Integer> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(String... params) {

			int status = -1;
			ArrayList<BaseBusiness> users = new WysApi()
					.doResetPassword(params[0]);
			if (users != null) {
				status = users.get(0).getStatus();
			} else {
				return SERVER_ERROR;
			}

			return status;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (result == SUCCESS) {
				if (getOnResetPassListener() != null) {
					getOnResetPassListener().onRestSuccess();
				}
			}

			else if (result == ERROR) {
				if (getOnResetPassListener() != null) {
					getOnResetPassListener().onResetFail();
				}
			} else if (result == SERVER_ERROR) {
				Toast.makeText(_ctx, "OOPS !! SERVER NOT RESPONDING",
						Toast.LENGTH_LONG).show();
			}

			super.onPostExecute(result);
		}

	}

}
