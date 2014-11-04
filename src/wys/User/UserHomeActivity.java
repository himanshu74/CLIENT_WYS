package wys.User;

import java.util.ArrayList;

import com.wys.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import wys.Api.SessionManager;
import wys.AsyncTask.CategoryTask;
import wys.Base.BaseActivity;
import wys.Business.BaseBusiness;
import wys.CustomInterfaces.OnGetCategoriesListener;
import wys.ForumObjects.CategoryListActivity;
import wys.FrontLayer.MainActivity;
import wys.ORG.OrgHomeActivity;

public class UserHomeActivity extends BaseActivity implements OnClickListener,OnGetCategoriesListener {

	private ImageView iv_logout;
	private Button btn_cats;
	private Context _ctx= UserHomeActivity.this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_home);
		InitControls();

	}

	private void InitControls() {
		iv_logout = (ImageView) findViewById(R.id.iv_logout);
		iv_logout.setOnClickListener(this);
		btn_cats =(Button)findViewById(R.id.btn_cat);
		btn_cats.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == iv_logout.getId()) {
			SessionManager.setUserBo(null);
			Intent i = new Intent(UserHomeActivity.this, MainActivity.class);
			startActivity(i);
		}
		else if(v.getId() == btn_cats.getId())
		{
			CategoryTask categoryTask = new CategoryTask(this, _ctx);
			categoryTask.ExecuteGetCategories();
		}

	}

	@Override
	public void OnCategoriesReceived(ArrayList<BaseBusiness> list) {
		Intent i = new Intent(UserHomeActivity.this, CategoryListActivity.class);
		i.putExtra("list", list);
		startActivity(i);
		
	}

	@Override
	public void OnCategoriesNotReceived() {
		// TODO Auto-generated method stub
		
	}

}
