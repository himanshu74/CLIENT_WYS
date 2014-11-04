package wys.Dialogs;



import wys.AsyncTask.SignInTask;
import wys.CustomInterfaces.OnResetPassListener;

import com.wys.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ForgotPassword extends Dialog implements OnClickListener,OnResetPassListener {

	private Button btn_forgotPass;
	private Context _ctx;
	private ImageView iv_back;
	private EditText et_username;

	public ForgotPassword(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this._ctx = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(R.layout.forgot_pass);
		InitControls();
	}

	private void InitControls() {
		btn_forgotPass = (Button) findViewById(R.id.btn_send);
		btn_forgotPass.setOnClickListener(this);
		iv_back = (ImageView) findViewById(R.id.iv_backbtn);
		iv_back.setOnClickListener(this);
		et_username =(EditText) findViewById(R.id.et_username);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == btn_forgotPass.getId()) {
			String username = et_username.getText().toString();
			if(username.length()<=0)
			{
				et_username.setError("username cannot be empty");
			}
			else {
			SignInTask signInTask =	new SignInTask();
			signInTask.setOnResetPassListener(this);
			signInTask.executeResetPass(username);
			}
			

		} else if (v.getId() == iv_back.getId()) {
			ForgotPassword.this.dismiss();
			SignInDialog signInDialog = new SignInDialog(_ctx);
			signInDialog.setCanceledOnTouchOutside(false);
			signInDialog.show();
		}
	}

	@Override
	public void onRestSuccess() {
		ForgotPassword.this.dismiss();
		Toast.makeText(_ctx, "New password sent at your E-mail address", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onResetFail() {
		Toast.makeText(_ctx, "OOPS !! Something went wrong,try again", Toast.LENGTH_SHORT).show();;

		
	}
}
