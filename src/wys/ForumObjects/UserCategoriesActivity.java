package wys.ForumObjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.wallet.m;
import com.wys.R;

import android.annotation.SuppressLint;
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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import wys.Adapter.CategoryPagerAdaptor;
import wys.Adapter.TopicsPagerAdapter;
import wys.Api.SessionManager;
import wys.AsyncTask.UserAsynctask;
import wys.Base.BaseActivity;
import wys.Base.BaseDbActivity;
import wys.Base.BaseFragmentActivity;
import wys.Business.CategoryBo;
import wys.Business.UserBo;
import wys.CustomInterfaces.OnUserCatPost;
import wys.DatabaseHelpers.DBAdapter;
import wys.Fragments.MyCategoriesFragment;
import wys.Fragments.OtherCategoriesFragment;
import wys.FrontLayer.MainActivity;
import wys.Helpers.CategoryHelper;
import wys.Helpers.FontHelper;
import wys.ORG.OrgHomeActivity;

public class UserCategoriesActivity extends BaseFragmentActivity implements
		OnClickListener, OnUserCatPost,OnPageChangeListener,TabListener,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ListView categoryList;
	private ImageView iv_back;
	private static ImageView iv_logout;
	private static ImageView iv_cancel;
	private static ImageView iv_select_confirm,iv_DeleteCategory;
	private ArrayList<CategoryBo> listCats;
	private TextView tv_Category_title,tv_edit;
	private Context _ctx = UserCategoriesActivity.this;
	private String catName;
	private int catId;
	Button btn_edit;
	public static boolean IsCheckBoxChecked;
	public boolean[] checkBxState;
	private boolean IsEditPresed = false;
	ArrayList<CategoryBo> CatsSelected, categories;
	UserBo user;
	private Button btn_categories;
	
	private CategoryPagerAdaptor mAdapter;
	
	private ViewPager viewPager;
	private ActionBar actionBar;
	private String[] tabs = { "Other", "My Categories"};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_cats);
		user = (UserBo) SessionManager.getUserBo();
		
		initTabConntrols();
		initControls();
		//setRemaininigList();
	}

	@SuppressLint("NewApi")
	private void initTabConntrols(){
		viewPager = (ViewPager) findViewById(R.id.pager_cats);
		actionBar = getActionBar();
		
		mAdapter = new CategoryPagerAdaptor(getSupportFragmentManager(),_ctx,dbAdapter);
		viewPager.setAdapter(mAdapter);
		viewPager.setOnPageChangeListener(this);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		View v = LayoutInflater.from(this).inflate(R.layout.action_bar_categories, null);
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
		/*categoryList = (ListView) findViewById(R.id.lv_categories);
		categoryList.setOnItemClickListener(this);*/
		tv_Category_title = (TextView) findViewById(R.id.tv_title);
		tv_Category_title
				.setTypeface(GetTypeFace(FontHelper.CATEGORY_TITLE_FONTSTYLE));
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(this);
		iv_logout = (ImageView) findViewById(R.id.iv_logout);
		iv_logout.setOnClickListener(this);
		iv_select_confirm = (ImageView) findViewById(R.id.iv_select_confirm);
		iv_select_confirm.setOnClickListener(this);
		iv_cancel = (ImageView) findViewById(R.id.iv_cancel);
		iv_cancel.setOnClickListener(this);
		tv_edit = (TextView)findViewById(R.id.tv_edit);
		tv_edit.setOnClickListener(this);
		iv_DeleteCategory =(ImageView)findViewById(R.id.iv_DeleteCategory);
		iv_DeleteCategory.setOnClickListener(this);
		/*btn_edit = (Button) findViewById(R.id.btn_edit);
		btn_edit.setOnClickListener(this);*/

	}

	

	private void Hide_Layer1() {

		iv_logout.setVisibility(View.GONE);
		iv_DeleteCategory.setVisibility(View.GONE);
		iv_select_confirm.setVisibility(View.VISIBLE);
		iv_cancel.setVisibility(View.VISIBLE);
	}

	public static void Hide_Layer2() {
		iv_DeleteCategory.setVisibility(View.VISIBLE);
		iv_logout.setVisibility(View.VISIBLE);
		iv_select_confirm.setVisibility(View.GONE);
		iv_cancel.setVisibility(View.GONE);
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == tv_edit.getId() ) {
			Hide_Layer1();
			mAdapter.onEditStatePressed();
			Toast.makeText(_ctx, "Edit Pressed", Toast.LENGTH_LONG).show();
			
		} else if (v.getId() == iv_select_confirm.getId()) {
			 mAdapter.onConfirmPressed();
			//postUserCats();
		} else if (v.getId() == iv_cancel.getId()) {
			CategoryPagerAdaptor.IsConfirmDeletePressed= false;
			Hide_Layer2();
			mAdapter.onCanCelPressed();
			
		}
		else if(v.getId()== iv_logout.getId()){
			 SessionManager.setUserBo(null);
			 Intent i = new Intent(UserCategoriesActivity.this, MainActivity.class);
			 startActivity(i);
		}
		else if(v.getId() == iv_back.getId()){
                UserCategoriesActivity.this.finish();
		}
		else if(v.getId() == iv_DeleteCategory.getId()){
			Hide_Layer1();
			CategoryPagerAdaptor.IsConfirmDeletePressed= true;
			mAdapter.onDeletePressed();
		}

	}

	/*private void postUserCats() {
		int length = checkBxState.length;
		CatsSelected = new ArrayList<CategoryBo>();
		if (IsCheckBoxChecked) {
			for (int i = 0; i < length; i++) {
				if (checkBxState[i]) {
					CategoryBo category = (CategoryBo) categoryList
							.getItemAtPosition(i);
					CatsSelected.add(category);

				}

			}
			UserBo sessionedUser = (UserBo) SessionManager.getUserBo();
			UserBo user = new UserBo();
			user.set_userId(sessionedUser.get_userId());
			user.setUserCategories(CatsSelected);
			UserAsynctask userAsynctask = new UserAsynctask(
					UserCategoriesActivity.this, dbAdapter,
					UserCategoriesActivity.this);
			userAsynctask.executePostUserCategories(user);
		} else {
			Toast.makeText(UserCategoriesActivity.this, "No Item was Selected",
					Toast.LENGTH_SHORT).show();
		}
		Hide_Layer2();
	}*/


	
	public void onPostSuccess() {
		IsEditPresed= false;
		

	}

	public void onPostFail() {

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
		// TODO Auto-generated method stub
		actionBar.setSelectedNavigationItem(position);

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		viewPager.setCurrentItem(tab.getPosition());
		if (tab.getPosition() == 1 && MyCategoriesFragment.DataChanged) {

            LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
            Intent i = new Intent("TAG_REFRESH");
            lbm.sendBroadcast(i);

        }
		else if ((tab.getPosition() ==0) && OtherCategoriesFragment.IsDataChanged){
			 LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
	            Intent i = new Intent("TAG_REFRESH_Other");
	            lbm.sendBroadcast(i);
		}
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}
