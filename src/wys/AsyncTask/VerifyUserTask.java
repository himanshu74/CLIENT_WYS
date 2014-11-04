package wys.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import wys.Api.SessionManager;
import wys.Api.WysApi;
import wys.Business.BaseBusiness;
import wys.Business.UserBo;
import wys.CustomInterfaces.OnVerifyingUser;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class VerifyUserTask extends BaseAsyncTaskManager {

	private String CLASS_TAG = VerifyUserTask.class.getSimpleName();
	private OnVerifyingUser _onVerifyingUser;

	private Context _ctx;
	private ProgressBar progressBar = null;

	public VerifyUserTask(OnVerifyingUser OnVerifyingUser, Context context) {
		_onVerifyingUser = OnVerifyingUser;
		this._ctx = context;

	}

	public void ExecuteVerifyUser(String code) {
		UserBo user = (UserBo) SessionManager.getUserBo();
		user.setVerificationCode(code);
		new VerifyAsync().execute(user);
	}

	public void ExecuteResendCodeAsync() {
		if (SessionManager.getUserBo() != null) {
			UserBo user = (UserBo) SessionManager.getUserBo();
			new ResendCodeAsync().execute(user);
		}
	}

	private class VerifyAsync extends AsyncTask<UserBo, Void, Integer> {
		@Override
		protected Integer doInBackground(UserBo... params) {
			int result = -1;
			ArrayList<BaseBusiness> users = new WysApi().DoVerifyUser(
					params[0].get_username(), params[0].getVerificationCode());
			if (users != null) {
				result = users.get(0).getStatus();
			}

			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {

			if (result == VERIFIED) {
				if (_onVerifyingUser != null) {
					_onVerifyingUser.OnSuccessFulVerification();
				}

			}

			else if (result == NOT_VERIFIED) {
				if (_onVerifyingUser != null)

				{
					_onVerifyingUser.OnVerificationFail();
				}
			} else {

			}
			super.onPostExecute(result);
		}
	}

	private class ResendCodeAsync extends AsyncTask<UserBo, Void, Integer> {
		@Override
		protected Integer doInBackground(UserBo... params) {

			int result = -1;
			String username = params[0].get_username();
			String email = params[0].get_email();
			ArrayList<BaseBusiness> users = new WysApi()
					.DoResendVerificationCode(username, email);
			result = users.get(0).getStatus();
			return result;
		}

		@Override
		protected void onPreExecute() {
			/*
			 * progressDialog = new ProgressDialog(_ctx);
			 * progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			 * progressDialog.setMessage("Logging in...");
			 * progressDialog.setIndeterminate(false); progressDialog.show();
			 */

			progressBar = new ProgressBar(_ctx);
			progressBar.setIndeterminate(true);
			progressBar.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Integer result) {

			progressBar.setVisibility(View.INVISIBLE);
			if (result == SUCCESS) {
				if (_onVerifyingUser != null) {
					_onVerifyingUser.OnResendSucces();
				}
			} else if (result == ERROR) {
				_onVerifyingUser.OnResendFail();
			}

			else {
				Log.e(CLASS_TAG, "There is error in wysApi call");
			}

			super.onPostExecute(result);
		}
	}

}
