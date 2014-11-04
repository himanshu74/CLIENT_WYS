package wys.Dialogs;

import com.wys.R;

import wys.Api.SessionManager;
import wys.AsyncTask.SignInTask;
import wys.AsyncTask.VerifyUserTask;
import wys.Business.UserBo;
import wys.CustomInterfaces.OnSIgnInListener;
import wys.CustomInterfaces.OnVerifyingUser;
import wys.User.UserHomeActivity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class VerificationDialog extends Dialog implements
		android.view.View.OnClickListener, OnVerifyingUser, OnSIgnInListener {

	private Context _ctx;
	private EditText et_code;
	private Button btn_verify, btn_resend;

	public VerificationDialog(Context context) {
		super(context);
		this._ctx = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(R.layout.verification_code);
		InitControls();

	}

	private void InitControls() {
		et_code = (EditText) findViewById(R.id.et_code);
		btn_verify = (Button) findViewById(R.id.btn_verify);
		btn_verify.setOnClickListener(this);
		btn_resend = (Button) findViewById(R.id.btn_resend);
		btn_resend.setOnClickListener(this);
	}

	private boolean ValidateCode(String code) {
		if (code.equals(null) || code.length() <= 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == btn_verify.getId()) {
			String code = et_code.getText().toString();
			if (ValidateCode(code)) {
				VerifyUserTask verifyUser = new VerifyUserTask(
						VerificationDialog.this, _ctx);
				verifyUser.ExecuteVerifyUser(code);
			}

			else {
				et_code.setError("code cannot be empty");
			}

		}

		else if (v.getId() == btn_resend.getId()) {
			VerifyUserTask verifyUser = new VerifyUserTask(
					VerificationDialog.this, _ctx);
			verifyUser.ExecuteResendCodeAsync();
		}

	}

	@Override
	public void OnSuccessFulVerification() {

		SignInTask signInTask = new SignInTask(VerificationDialog.this, _ctx);
		signInTask.ExecuteSignIn();
	}

	@Override
	public void OnVerificationFail() {

		Toast.makeText(_ctx, "Verification Failed, please try again",
				Toast.LENGTH_LONG).show();

	}

	@Override
	public void OnSignInFail() {
		// TODO Auto-generated method stub
		VerificationDialog.this.dismiss();
		Toast.makeText(
				_ctx,
				"Something went wrong,  we couldnt sign you in, please try again",
				Toast.LENGTH_LONG).show();
	}

	@Override
	public void OnStillNotVerified() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBackPressed() {
		SessionManager.setUserBo(null);
		VerificationDialog.this.dismiss();
		super.onBackPressed();
	}

	@Override
	public void OnResendSucces() {

		Toast.makeText(_ctx,
				"You New Code has been sent to your email Address",
				Toast.LENGTH_LONG).show();
		;

	}

	@Override
	public void OnResendFail() {
		Toast.makeText(_ctx, "Something went wrong, Please try again",
				Toast.LENGTH_LONG).show();
		;

	}

	@Override
	public void OnUserSignIN() {
		VerificationDialog.this.dismiss();
		Intent i = new Intent(_ctx, UserHomeActivity.class);
		_ctx.startActivity(i);
	}

	@Override
	public void OnOrgSignIN() {
		// TODO Auto-generated method stub

	}

}
