package wys.ForumObjects;

import com.wys.R;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import wys.Adapter.CategoryPagerAdaptor;
import wys.Adapter.UserTopicPagerAdapter;
import wys.Adapter.UserUpcomingTopicsListAdaptor;
import wys.Api.SessionManager;
import wys.Base.BaseFragmentActivity;
import wys.FrontLayer.MainActivity;
import wys.Helpers.FontHelper;
import wys.Helpers.WysConstants;
import wys.Users.Fragments.UserMyTopicFragment;

public class UserTopicsActivity extends BaseFragmentActivity implements
		OnPageChangeListener, TabListener, OnClickListener {

	private ViewPager viewPager;
	private ActionBar actionBar;
	private UserTopicPagerAdapter mAdapter;
	private Context _ctx = UserTopicsActivity.this;
	private int _catId;
	private String[] tabs = { "Past", "Current", "Upcoming" };

	private TextView tv_topic_title, tv_edit;
	private ImageView iv_back;
	private static ImageView iv_logout;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		_catId = (int) getIntent().getIntExtra("catid", -1);
		setContentView(R.layout.user_topics);
		initTabControls();
		initControls();

	}

	private void initControls() {
		tv_topic_title = (TextView) findViewById(R.id.tv_title);
		tv_topic_title
				.setTypeface(GetTypeFace(FontHelper.CATEGORY_TITLE_FONTSTYLE));
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(this);
		iv_logout = (ImageView) findViewById(R.id.iv_logout);
		iv_logout.setOnClickListener(this);
	}

	private void initTabControls() {
		viewPager = (ViewPager) findViewById(R.id.pager_user_topics);
		actionBar = getActionBar();
		mAdapter = new UserTopicPagerAdapter(getSupportFragmentManager(), _ctx,
				dbAdapter, _catId);
		viewPager.setAdapter(mAdapter);
		viewPager.setOnPageChangeListener(this);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		View v = LayoutInflater.from(this).inflate(
				R.layout.action_bar_user_topics, null);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setCustomView(v);
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}
		viewPager.setCurrentItem(1);

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());
		if (tab.getPosition() == WysConstants.USER_MY_TOPIC_FRAG_INDEX
				&& UserMyTopicFragment.IsDataChanged) {
			LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
			Intent i = new Intent(WysConstants.TAG_MY_TOPIC_REFERESH);
			lbm.sendBroadcast(i);
		}

		

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

	}

	@Override
	public void onPageSelected(int position) {

		actionBar.setSelectedNavigationItem(position);

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == iv_logout.getId()) {
			SessionManager.setUserBo(null);
			Intent i = new Intent(UserTopicsActivity.this, MainActivity.class);
			startActivity(i);
		} else if (v.getId() == iv_back.getId()) {
			UserTopicsActivity.this.finish();
		}

	}

}
