package wys.ForumObjects;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.wys.R;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.drm.DrmStore.Action;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import wys.Adapter.TopicsPagerAdapter;
import wys.Api.SessionManager;
import wys.AsyncTask.Topictask;
import wys.Base.BaseDbActivity;
import wys.Base.BaseFragmentActivity;
import wys.Business.BaseBusiness;
import wys.Business.TopicBo;
import wys.CustomInterfaces.OnGetTopicsListener;
import wys.DatabaseHelpers.DBAdapter;
import wys.Dialogs.EmptyContentDialog;
import wys.Dialogs.NewTopicDialog;
import wys.FrontLayer.MainActivity;
import wys.Helpers.FontHelper;
import wys.Helpers.TopicHelper;

public class CategoryActivity extends BaseFragmentActivity implements
		OnClickListener, TabListener, OnPageChangeListener {

	// ////// PRIVATE CLASS VARIABLES \\\\\\\\\
	private String _catName;
	private int _catId;
	private Context _ctx = CategoryActivity.this;
	public static boolean isNewTopicAddded;
	private String CategoryId = "CatId";

	private TopicsPagerAdapter mAdapter;
	private String[] tabs = { "Past", "Current", "Upcoming" };

	// //// VIEW VARIABLES \\\\\\\\\\
	private TextView tv_item_heading;
	private ImageView iv_back, iv_logout, iv_deleteItem, iv_confirm, iv_cancel,
			btn_addnew;
	// private ListView itemsListVIew;
	private ViewPager viewPager;
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.topics);
		Intent i = getIntent();
		_catName = i.getStringExtra("catName");
		_catId = i.getIntExtra("catid", -1);
		initTabControls();
		initControls();

	}

	@SuppressLint("NewApi")
	private void initTabControls() {
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TopicsPagerAdapter(getSupportFragmentManager(), _ctx,
				dbAdapter, _catId);

		viewPager.setAdapter(mAdapter);
		viewPager.setOnPageChangeListener(this);

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		View v = LayoutInflater.from(this).inflate(R.layout.action_bar, null);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setCustomView(v);

		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}
		viewPager.setCurrentItem(1);
	}

	private void initControls() {

		tv_item_heading = (TextView) findViewById(R.id.tv_title);
		tv_item_heading.setText(_catName);
		tv_item_heading
				.setTypeface(GetTypeFace(FontHelper.CATEGORY_TITLE_FONTSTYLE));

		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(this);
		iv_logout = (ImageView) findViewById(R.id.iv_logout);
		iv_logout.setOnClickListener(this);
		// iv_deleteItem = (ImageView) findViewById(R.id.iv_DeleteItem);

		btn_addnew = (ImageView) findViewById(R.id.btn_addnew_Item);
		btn_addnew.setOnClickListener(this);

		/*
		 * iv_confirm = (ImageView) findViewById(R.id.iv_delconfirm); iv_cancel
		 * = (ImageView) findViewById(R.id.iv_cancel);
		 */

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == btn_addnew.getId()) {

			Intent i = new Intent(CategoryActivity.this,
					AddNewTopicActivity.class);
			i.putExtra(CategoryId, _catId);
			startActivity(i);
			/*
			 * NewTopicDialog newTopicDialog = new NewTopicDialog(_ctx, _catId);
			 * newTopicDialog.setCanceledOnTouchOutside(false);
			 * newTopicDialog.setOnDismissListener(this); newTopicDialog.show();
			 */
		}

		else if (v.getId() == iv_back.getId()) {
			onBackPressed();
		} else if (v.getId() == iv_logout.getId()) {
			SessionManager.setUserBo(null);
			Intent i = new Intent(_ctx, MainActivity.class);
			startActivity(i);
		}

	}

	@Override
	public void onBackPressed() {

		CategoryActivity.this.finish();

	}

	public void ShowAutoDismissDialog() {
		final EmptyContentDialog emptyContentDialog = new EmptyContentDialog(
				_ctx);
		emptyContentDialog.setCanceledOnTouchOutside(false);
		emptyContentDialog.show();
		final Timer t = new Timer();
		t.schedule(new TimerTask() {
			public void run() {
				emptyContentDialog.dismiss(); // when the task active then close
												// the dialog
				t.cancel(); // also just top the timer thread, otherwise, you
							// may receive a crash report
			}
		}, 5000);
	}

	@Override
	protected void onResume() {

		if (isNewTopicAddded) {
			viewPager.setCurrentItem(2);
			viewPager.getAdapter().notifyDataSetChanged();
		}

		super.onResume();
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int position) {
		actionBar.setSelectedNavigationItem(position);

	}

}
