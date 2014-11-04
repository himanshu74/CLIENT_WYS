package wys.Dialogs;

import com.wys.R;

import wys.AsyncTask.IAsyncTask;
import wys.AsyncTask.SignupTask;
import wys.Business.UserBo;
import wys.CustomInterfaces.OnCheckUserListener;
import wys.CustomInterfaces.OnSignUpListener;
import wys.Helpers.PreferenceHelper;
import wys.Http.*;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpDialog extends Dialog implements
		android.view.View.OnClickListener, OnSignUpListener,
		OnCheckUserListener {

	private Context _ctx;
	private Button btn_signup;
	private EditText et_username, et_password, et_email, et_confirm;
	private static final int USER_ROLEID = 1;

	public SignUpDialog(Context context, PreferenceHelper preferenceHelper) {
		super(context);
		this._ctx = context;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(R.layout.signup);
		InitControls();
	}

	private void InitControls() {
		btn_signup = (Button) findViewById(R.id.btn_signup);
	btn_signup.setOnClickListener(this);
		et_username = (EditText) findViewById(R.id.et_username);

		et_password = (EditText) findViewById(R.id.et_password);
		et_email = (EditText) findViewById(R.id.et_email);
		et_confirm = (EditText) findViewById(R.id.et_confirm);

	}

	@Override
	public void onClick(View v) {

		ValidateAndSignUpUser();

	}

	private void OpenVerificationScreen() {

		SignUpDialog.this.dismiss();

		VerificationDialog veriDialog = new VerificationDialog(_ctx);
		veriDialog.setCanceledOnTouchOutside(false);
		veriDialog.show();

	}

	private void ValidateAndSignUpUser() {

		String username = et_username.getText().toString();
		String password = et_password.getText().toString();
		String email = et_email.getText().toString();
		String confirm = et_confirm.getText().toString();

		if (ValidateFields(username, password, email, confirm)) {
			ValidateUsernameAvail(username);

		}

	}

	private boolean ValidateFields(String username, String pass, String email,
			String confirmPass) {
		if (username.isEmpty() && pass.isEmpty() && email.isEmpty()
				&& confirmPass.isEmpty()) {
			et_username.setError("username cannot be empty");
			et_email.setError("email cannot be empty");
			et_password.setError("password cannot be empty");
			et_confirm.setError("please enter confirm password");

			return false;
		}

		else if (username.isEmpty() || username.length() <= 0) {

			et_username.setError("username cannot be empty");
			return false;

		}

		else if (email.isEmpty() || email.length() <= 0) {
			et_email.setError("email cannot be empty");
			return false;

		} else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email)
				.matches()) {
			et_email.setError("Please enter a valid email address");
			return false;
		}

		else if (pass.isEmpty() || pass.length() <= 0) {
			et_password.setError("password cannot be empty");
			return false;
		} else if (confirmPass.isEmpty() || confirmPass.length() <= 0) {
			et_confirm.setError("please enter confirm password");
			return false;
		} else {
			if (!pass.equals(confirmPass)) {
				et_confirm.setError("Passwords dont match, please re enter");
				return false;
			}

		}

		return true;

	}

	private void ValidateUsernameAvail(String username) {
		IAsyncTask asyncTask = new SignupTask(SignUpDialog.this, _ctx);

		asyncTask.ExcecuteCheckUsername(username);

		// return SignupTask.CheckUserAvail(username);
	}

	private void SaveUser(String username, String pass, String email) {
		UserBo user = new UserBo();
		user.set_username(username);
		user.set_password(pass);
		user.set_email(email);
		user.set_roleId(USER_ROLEID);
		IAsyncTask asyncTask = new SignupTask(user, SignUpDialog.this, _ctx);
		asyncTask.ExecuteSignupTask();
	}

	@Override
	public void OnUserAvail() {

		SaveUser(et_username.getText().toString(), et_password.getText()
				.toString(), et_email.getText().toString());
	}

	@Override
	public void OnUserNotAvail() {

		et_username.setError("Sorry, Username is not available");

	}

	@Override
	public void OnSignUpSuccess() {

		SignUpDialog.this.dismiss();

		OpenVerificationScreen();

	}

	@Override
	public void OnSignUpFail() {

		SignUpDialog.this.dismiss();
		Toast.makeText(_ctx, "Oops, Something went wrong, Try again",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void OnUnsuccessfulOperation() {

		Toast.makeText(_ctx,
				"Sorry, Something went wrong wiith the server, Try again",
				Toast.LENGTH_LONG).show();
	}

}
