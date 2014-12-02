package wys.FrontLayer;

import wys.AsyncTask.NotificationRegister;
import wys.Base.BaseDbActivity;
import wys.Dialogs.SignInDialog;
import wys.Dialogs.SignUpDialog;

import com.wys.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends BaseDbActivity implements OnClickListener {

	// Private Variables and Views
	private Button btn_SignIn, btn_BackUp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		initControls();
		//new NotificationRegister(MainActivity.this);

	}

	@Override
	public void onBackPressed() {

		// super.onBackPressed();
	}

	private void initControls() {

		btn_SignIn = (Button) findViewById(R.id.btn_signin);
		btn_SignIn.setOnClickListener(MainActivity.this);
		btn_BackUp = (Button) findViewById(R.id.btn_backup);
		btn_BackUp.setOnClickListener(this);

	}

	public void signMeUp(final View v) {

		SignUpDialog signUpDialog = new SignUpDialog(MainActivity.this);
		signUpDialog.setCanceledOnTouchOutside(false);
		signUpDialog.show();

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == btn_SignIn.getId()) {
			SignInDialog signInDialog = new SignInDialog(MainActivity.this);
			signInDialog.setCanceledOnTouchOutside(false);
			signInDialog.show();
		} else if (v.getId() == btn_BackUp.getId()) {
			TakeBackup(MainActivity.this);
		}

	}

}
