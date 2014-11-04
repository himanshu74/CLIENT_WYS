package wys.Dialogs;

import com.wys.R;

import wys.Api.SessionManager;
import wys.AsyncTask.SignInTask;
import wys.Business.UserBo;
import wys.CustomInterfaces.OnResetPassListener;
import wys.CustomInterfaces.OnSIgnInListener;
import wys.ORG.OrgHomeActivity;
import wys.User.UserHomeActivity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignInDialog extends Dialog implements OnClickListener,
		OnSIgnInListener {

	private EditText et_username;
	private EditText et_password;
	private Button btn_signin, btn_forgotPass;
	private Context _ctx;

	public SignInDialog(Context context) {
		super(context);
		this._ctx = context;

		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(R.layout.sign_in);
		InitControls();
	}

	private void InitControls() {
		et_username = (EditText) findViewById(R.id.et_username);
		et_password = (EditText) findViewById(R.id.et_password);
		btn_signin = (Button) findViewById(R.id.btn_signin);
		btn_forgotPass = (Button) findViewById(R.id.btn_forgotPass);
		btn_forgotPass.setOnClickListener(this);
		btn_signin.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == btn_signin.getId()) {

			String username = et_username.getText().toString();
			String password = et_password.getText().toString();
			if (ValidateFields(username, password)) {
				UserBo user = new UserBo();
				user.set_username(username);
				user.set_password(password);

				SignInTask signInTask = new SignInTask(SignInDialog.this, user,
						_ctx);
				signInTask.ExecuteSignIn();
			}
		} else if (v.getId() == btn_forgotPass.getId()) {
			SignInDialog.this.dismiss();
			ForgotPassword forgotPassword = new ForgotPassword(_ctx);
			forgotPassword.setCanceledOnTouchOutside(false);
			forgotPassword.show();

		}
	}

	private boolean ValidateFields(String username, String pass) {
		if (username.equals(null) || username.length() <= 0) {
			et_username.setError("username cannot be empty");
			return false;
		} else if (pass.equals(null) || pass.length() <= 0) {
			et_password.setError("Password cannot be empty");
			return false;
		}
		return true;
	}

	@Override
	public void OnSignInFail() {
		et_username.setText("");
		et_password.setText("");
		Toast.makeText(_ctx, "OOPS!!  We didnt recognize you, Try again",
				Toast.LENGTH_LONG).show();
		;

	}

	@Override
	public void OnStillNotVerified() {
		SignInDialog.this.dismiss();
		VerificationDialog verDialog = new VerificationDialog(_ctx);
		verDialog.setCanceledOnTouchOutside(false);
		verDialog.show();

	}

	@Override
	public void OnUserSignIN() {
		SignInDialog.this.dismiss();
		Intent i = new Intent(_ctx, UserHomeActivity.class);
		_ctx.startActivity(i);
	}

	@Override
	public void OnOrgSignIN() {
		SignInDialog.this.dismiss();
		Intent i = new Intent(_ctx, OrgHomeActivity.class);
		_ctx.startActivity(i);

	}

	@Override
	public void onBackPressed() {
		SessionManager.setUserBo(null);
		SignInDialog.this.dismiss();
		super.onBackPressed();
	}

}
