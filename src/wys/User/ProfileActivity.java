package wys.User;

import com.wys.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import wys.Api.SessionManager;
import wys.AsyncTask.UserAsynctask;
import wys.Base.BaseDbActivity;
import wys.Business.UserBo;
import wys.CustomInterfaces.OnResponseReceived;
import wys.FrontLayer.MainActivity;

public class ProfileActivity extends BaseDbActivity implements OnClickListener,
		OnResponseReceived {

	private TextView tv_view_Header;
	private EditText et_username;
	private EditText et_email, et_new_password, et_old_password, et_confirm;
	private Context _ctx = ProfileActivity.this;
	private Button btn_update;
	private UserBo userbo;
	private ImageView iv_logout, iv_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userbo = (UserBo) SessionManager.getUserBo();
		setContentView(R.layout.user_profile);
		initControls();

	}

	private void initControls() {
		tv_view_Header = (TextView) findViewById(R.id.tv_view_Header);

		tv_view_Header.setText(userbo.get_username().toUpperCase());
		et_username = (EditText) findViewById(R.id.et_username);
		et_username.setText(userbo.get_username());
		et_email = (EditText) findViewById(R.id.et_email);
		et_email.setText(userbo.get_email());
		et_new_password = (EditText) findViewById(R.id.et_new_password);
		et_confirm = (EditText) findViewById(R.id.et_confirmPass);
		et_old_password = (EditText) findViewById(R.id.et_old_password);
		btn_update = (Button) findViewById(R.id.iv_update);
		iv_logout = (ImageView) findViewById(R.id.iv_logout);
		iv_logout.setOnClickListener(this);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(this);
		btn_update.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.iv_update) {
			String username = et_username.getText().toString();
			String password = et_new_password.getText().toString();
			String confirm = et_confirm.getText().toString();
			String email = et_email.getText().toString();
			String oldPassword = et_old_password.getText().toString();

			if (oldPassword.trim().toLowerCase()
					.equals(userbo.get_password().trim().toLowerCase())) {
				if (validateFields(username, email, password, confirm)) {
					UserBo user = new UserBo();
					user.set_username(username);
					user.set_email(email);
					user.set_password(password);
					user.set_userId(userbo.get_userId());
					UserAsynctask userAsynctask = new UserAsynctask(_ctx,
							dbAdapter);
					userAsynctask.set_onOnResponseReceived(this);
					userAsynctask.executeUpdateUser(user);

				}
			} else {
				Toast.makeText(_ctx, "Please enter correct old password",
						Toast.LENGTH_LONG).show();
			}

		}

		else if (v.getId() == R.id.iv_logout) {
			Intent i = new Intent(_ctx, MainActivity.class);
			startActivity(i);
		} else if (v.getId() == R.id.iv_back) {
			ProfileActivity.this.finish();
		}

	}

	private boolean validateFields(String username, String email,
			String password, String confirmPass) {

		if (username.isEmpty() || email.isEmpty() || password.isEmpty()
				|| confirmPass.isEmpty()) {
			Toast.makeText(_ctx, "All Fields are mandatory", Toast.LENGTH_LONG)
					.show();
			return false;
		} else if (!password.trim().equals(confirmPass)) {
			Toast.makeText(_ctx, "password dont match, please re enter",
					Toast.LENGTH_LONG).show();
			return false;
		} else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email)
				.matches()) {
			Toast.makeText(_ctx, "Not A Valid Email", Toast.LENGTH_LONG).show();
			return false;
		}

		return true;

	}

	@Override
	public void onResponseSuccess() {
		Toast.makeText(_ctx, "Profile Updated", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onResponseFailure() {
		Toast.makeText(_ctx, "Error Updating, Please Try again",
				Toast.LENGTH_SHORT).show();
	}

}
