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
import wys.Base.BaseDbActivity;
import wys.Business.BaseBusiness;
import wys.Business.CategoryBo;
import wys.CustomInterfaces.OnGetCategoriesListener;
import wys.DatabaseHelpers.DBAdapter;
import wys.ForumObjects.CategoryListActivity;
import wys.ForumObjects.UserCategoriesActivity;
import wys.FrontLayer.MainActivity;
import wys.Helpers.CategoryHelper;
import wys.ORG.OrgHomeActivity;

public class UserHomeActivity extends BaseDbActivity implements
		OnClickListener, OnGetCategoriesListener {

	private ImageView iv_logout;
	private Button btn_cats;
	private Context _ctx = UserHomeActivity.this;

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
		btn_cats = (Button) findViewById(R.id.btn_cat);
		btn_cats.setOnClickListener(this);
	}

	
	/// Here on Category Click button i am checking if the user is Opening Categories for the first time 
	//  if yes then I make the call to the server to fetch all the categories. 
	// Then store them inside the Local db. and then render them on the view. 
	// After that i update the Preferences and set isFIrstTime false;
	
	
	@Override
	public void onClick(View v) {
		if (v.getId() == iv_logout.getId()) {
			SessionManager.setUserBo(null);
			Intent i = new Intent(UserHomeActivity.this, MainActivity.class);
			startActivity(i);
		} else if (v.getId() == btn_cats.getId()) {
			if (getWYSPreferences().is_firstTimeUse()) {

				CategoryTask cattask = new CategoryTask(UserHomeActivity.this,
						UserHomeActivity.this, dbAdapter);
				cattask.ExecuteGetCategories();

			} else {
				ArrayList<CategoryBo> clist = CategoryHelper
						.getCategories(dbAdapter);
				Intent i = new Intent(UserHomeActivity.this,
						UserCategoriesActivity.class);
				i.putExtra("list", clist);
				startActivity(i);
			}

		}

	}

	@Override
	public void OnCategoriesReceived(ArrayList<BaseBusiness> list) {
		getWYSPreferences().set_firstTimeUse(false);
		ArrayList<CategoryBo> clist = CategoryHelper.getCategories(dbAdapter);
		Intent i = new Intent(UserHomeActivity.this, UserCategoriesActivity.class);
		i.putExtra("list", clist);
		startActivity(i);

	}

	@Override
	public void OnCategoriesNotReceived() {
		// TODO Auto-generated method stub

	}

}
