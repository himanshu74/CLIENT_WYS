package wys.ORG;

import java.util.ArrayList;
import java.util.List;

import com.wys.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import wys.ForumObjects.CategoryListActivity;
import wys.FrontLayer.MainActivity;
import wys.Helpers.CategoryHelper;

public class OrgHomeActivity extends BaseDbActivity implements OnClickListener,
		OnGetCategoriesListener {

	private ImageView iv_logout;
	private Button btn_cat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.org_home_new);
		InitControls();
	}

	private void InitControls() {
		 iv_logout = (ImageView) findViewById(R.id.iv_logout);
		 iv_logout.setOnClickListener(this);
		btn_cat = (Button) findViewById(R.id.btn_cat);
		btn_cat.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		 if (v.getId() == iv_logout.getId()) {
		 SessionManager.setUserBo(null);
		 Intent i = new Intent(OrgHomeActivity.this, MainActivity.class);
		 startActivity(i);
		 }

		// /If Org User Logins for the first Time fetch from the server and
		// Insert into the local db

		if (v.getId() == btn_cat.getId()) {
			if (getWYSPreferences().is_firstTimeUse()) {

				CategoryTask cattask = new CategoryTask(OrgHomeActivity.this,
						OrgHomeActivity.this, dbAdapter);
				cattask.ExecuteGetCategories();

			} else {
				ArrayList<CategoryBo> clist = CategoryHelper
						.getCategories(dbAdapter);
				Intent i = new Intent(OrgHomeActivity.this,
						CategoryListActivity.class);
				i.putExtra("list", clist);
				startActivity(i);
			}

		}

	}

	@Override
	public void OnCategoriesReceived(ArrayList<BaseBusiness> list) {
		getWYSPreferences().set_firstTimeUse(false);
		ArrayList<CategoryBo> clist = CategoryHelper.getCategories(dbAdapter);
		Intent i = new Intent(OrgHomeActivity.this, CategoryListActivity.class);
		i.putExtra("list", clist);
		startActivity(i);

	}

	@Override
	public void OnCategoriesNotReceived() {
		// TODO Auto-generated method stub

	}

}
