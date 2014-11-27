package wys.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import wys.Api.WysApi;
import wys.Business.BaseBusiness;
import wys.Business.UserBo;
import wys.CustomInterfaces.OnCheckUserListener;
import wys.CustomInterfaces.OnSignUpListener;
import wys.Dialogs.SignUpDialog;
import wys.Helpers.WebRequest;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class SignupTask extends BaseAsyncTaskManager {

	private static final int AVAIL = 0;
	private static final int NOT_AVAIL = 1;
	private OnSignUpListener _onSignUpListener;
	private OnCheckUserListener _oncheCheckUserListener;
	private Context _ctx;
	private ProgressDialog progressDialog;

	private UserBo _user;

	public SignupTask(BaseBusiness user, OnSignUpListener onSignUpListener,
			Context ctx) {
		this._user = (UserBo) user;
		this._onSignUpListener = onSignUpListener;
		this._ctx = ctx;
	}

	public SignupTask(OnCheckUserListener onCheckUserListener, Context ctx) {
		this._oncheCheckUserListener = onCheckUserListener;
		this._ctx = ctx;
	}

	@Override
	public void ExecuteSignupTask() {
		new PostAsync().execute(_user);

	}

	@Override
	public void ExcecuteCheckUsername(String username) {
		new CheckUserAsync().execute(username);
	}

	private class PostAsync extends AsyncTask<UserBo, Void, Integer> {

		@Override
		protected Integer doInBackground(UserBo... params) {
			WysApi wysApi = new WysApi();
			int result = wysApi.DoSignUp(params[0]);

			return result;
		}

		@Override
		protected void onPreExecute() {

			progressDialog = new ProgressDialog(_ctx);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			// Set the dialog title to 'Loading...'
			progressDialog.setTitle("Please wait..");
			progressDialog.setMessage("while we sign you up");
			// Set the dialog message to 'Loading application View, please
			// wait...'
			// progressDialog.setMessage("Loading application View, please wait...");
			// This dialog can't be canceled by pressing the back key
			progressDialog.setCancelable(false);
			// This dialog isn't indeterminate
			progressDialog.setIndeterminate(false);

			// Display the progress dialog
			progressDialog.show();
		}

		@Override
		protected void onPostExecute(Integer result) {

			progressDialog.dismiss();
			
			if (result == SUCCESS) {
				Toast.makeText(
						_ctx,
						"Email with verification code has been sent to your email address, PLease verify your account",
						Toast.LENGTH_LONG).show();
				if (_onSignUpListener != null) {
					_onSignUpListener.onSignUpSuccess();
				}

			} else if (result == ERROR) {

				if (_onSignUpListener != null) {
					_onSignUpListener.onSignUpFail();
				}

			}

			super.onPostExecute(result);
		}

	}

	public static int CheckUserAvail(String username) {
		ArrayList<BaseBusiness> users = new WysApi().GetUserByUsername(username);
		int result = users.get(0).getStatus();
		return result;
	}

	private class CheckUserAsync extends AsyncTask<String, Void, Integer> {

		@Override
		protected Integer doInBackground(String... params) {
			int result = -1;
			List<BaseBusiness> users = new WysApi()
					.GetUserByUsername(params[0]);
			if (users != null) {
				result = users.get(0).getStatus();
			}
			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {

			if (result == AVAIL) {
				if (_oncheCheckUserListener != null)
					_oncheCheckUserListener.onUserAvail();

			} else if (result == NOT_AVAIL) {
				if (_oncheCheckUserListener != null)
					_oncheCheckUserListener.onUserNotAvail();

			} else {

			}

			super.onPostExecute(result);
		}

	}

}
