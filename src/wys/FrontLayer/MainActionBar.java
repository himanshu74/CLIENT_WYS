package wys.FrontLayer;

import wys.Adapter.TopicsPagerAdapter;

import com.wys.R;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class MainActionBar extends FragmentActivity implements TabListener {

	private ViewPager viewPager;
	private TopicsPagerAdapter mAdapter;
	private ActionBar actionBar;

	private String[] tabs = { "Past", "Current", "Upcoming" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.action_main);
		initControls();
	}

	private void initControls() {

		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		/*mAdapter = new TopicsPagerAdapter(getSupportFragmentManager(),
				MainActionBar.this);*/

		viewPager.setAdapter(mAdapter);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}
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

}
