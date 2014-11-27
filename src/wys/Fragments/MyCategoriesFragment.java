package wys.Fragments;

import java.io.Serializable;
import java.util.ArrayList;

import com.wys.R;

import wys.Adapter.CategoryListAdaptor;
import wys.Api.SessionManager;
import wys.AsyncTask.Topictask;
import wys.AsyncTask.UserAsynctask;
import wys.Background.UserTopicGetService;
import wys.Base.BaseFragment;
import wys.BroadcastReceivers.WysBroadcastConstants;
import wys.Business.CategoryBo;
import wys.Business.UserBo;
import wys.CustomInterfaces.OnGetTopicsListener;
import wys.CustomInterfaces.OnResponseReceived;
import wys.DatabaseHelpers.DBAdapter;
import wys.ForumObjects.UserCategoriesActivity;
import wys.ForumObjects.UserTopicsActivity;
import wys.Helpers.CategoryHelper;
import wys.Helpers.WysConstants;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MyCategoriesFragment extends BaseFragment implements Serializable,
		OnResponseReceived, OnItemClickListener,OnGetTopicsListener {

	private ListView _myCatsList;
	private Context _ctx;
	private DBAdapter _dbAdapter;
	public static boolean DataChanged;
	public static boolean CheckBoxState[];
	public static boolean IsCheckBoxChecked;
	private CategoryBo categoryClicked;
	public static String DbAdaptorFlag = "dbAdbFlag";

	public static final String TOPIC_RECEIVER_FLAG = "topic_flag";

	ArrayList<CategoryBo> CatsSelected;
	MyReceiver r;

	private UserTopicReceiver _topicReceiver;

	public MyCategoriesFragment(Context context, DBAdapter dbAdapter) {
		super(context);
		this._ctx = context;
		this._dbAdapter = dbAdapter;
	}

	private void RegisterUserTopicReceiver() {
		IntentFilter intentFilter = new IntentFilter(
				WysBroadcastConstants.USER_GET_TOPIC_INTENT_ACTION);
		intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
		_topicReceiver = new UserTopicReceiver();
		_ctx.registerReceiver(_topicReceiver, intentFilter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		RegisterUserTopicReceiver();
		View view = inflater.inflate(R.layout.my_categories, container, false);
		_myCatsList = (ListView) view.findViewById(R.id.lv_my_cats);
		_myCatsList.setOnItemClickListener(this);

		CategoryListAdaptor catListAdp = new CategoryListAdaptor(_ctx,
				getMyCategories());

		_myCatsList.setAdapter(catListAdp);

		return view;
	}

	private ArrayList<CategoryBo> getMyCategories() {

		UserBo user = (UserBo) SessionManager.getUserBo();
		return CategoryHelper.getUserCategories(_dbAdapter, user.get_userId());
	}

	@Override
	public void onPause() {
		super.onPause();
		LocalBroadcastManager.getInstance(_ctx).unregisterReceiver(r);
	}

	@Override
	public void onResume() {
		super.onResume();
		r = new MyReceiver();
		LocalBroadcastManager.getInstance(_ctx).registerReceiver(r,
				new IntentFilter("TAG_REFRESH"));

	}

	private void refreshData() {
		CategoryListAdaptor catListAdp = new CategoryListAdaptor(_ctx,
				getMyCategories());

		_myCatsList.setAdapter(catListAdp);

	}

	public void onDeletePressed() {
		CategoryListAdaptor.IsDeletePressed = true;
		refreshData();
	}

	public void onConfirmDelete() {
		PostDeletedCategories();
	}

	private void PostDeletedCategories() {
		int length = CheckBoxState.length;
		CatsSelected = new ArrayList<CategoryBo>();
		if (IsCheckBoxChecked) {
			for (int i = 0; i < length; i++) {
				if (CheckBoxState[i]) {
					CategoryBo category = (CategoryBo) _myCatsList
							.getItemAtPosition(i);
					CatsSelected.add(category);

				}

			}
			UserBo sessionedUser = (UserBo) SessionManager.getUserBo();
			UserBo user = new UserBo();
			user.set_userId(sessionedUser.get_userId());
			user.setUserCategories(CatsSelected);
			UserAsynctask userAsynctask = new UserAsynctask(_ctx, _dbAdapter);
			userAsynctask.set_onOnResponseReceived(MyCategoriesFragment.this);
			userAsynctask.executePostUserDeletedCategories(user);
		} else {
			Toast.makeText(_ctx, "No Item was Selected", Toast.LENGTH_SHORT)
					.show();
		}
	}

	private class MyReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			MyCategoriesFragment.DataChanged = false;
			MyCategoriesFragment.this.refreshData();
		}

	}

	@Override
	public void onResponseSuccess() {
		CategoryListAdaptor.IsDeletePressed = false;
		refreshData();
		OtherCategoriesFragment.IsDataChanged = true;
		UserCategoriesActivity.Hide_Layer2();

	}

	@Override
	public void onResponseFailure() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View arg1, int position,
			long arg3) {
		categoryClicked = (CategoryBo) parent.getItemAtPosition(position);
		if (getWYSPreferences().isUserTopicExistLocal()) {
			Intent i = new Intent(_ctx, UserTopicsActivity.class);
			i.putExtra("catid", categoryClicked.get_serverId());
			startActivity(i);
		} else {
			/*Intent i = new Intent(_ctx, UserTopicGetService.class);
			i.putExtra("catid", categoryClicked.get_serverId());
			UserTopicGetService._dbAdapter = this._dbAdapter;
			_ctx.startService(i);*/
			
			FetchAllTopicsFromServer();
			
		}
		Toast.makeText(_ctx, "Item Clicked", Toast.LENGTH_LONG).show();
	}
	
	private void FetchAllTopicsFromServer(){
		Topictask topicTask = new Topictask(_ctx, this, _dbAdapter);
		topicTask.executeGetAllTopics();
		
	}

	private class UserTopicReceiver extends BroadcastReceiver  {

		@Override
		public void onReceive(Context context, Intent intent) {
			if ((intent.getIntExtra(UserTopicGetService.RESPONSE_TAG, -1) == WysConstants.RECEIVED_USER_TOPICS)) {
				getWYSPreferences().setUserTopicExistLocal(true);
				int catId = intent.getIntExtra("catid", -1);
				Intent i = new Intent(_ctx, UserTopicsActivity.class);
				i.putExtra("catid", catId);
				startActivity(i);
			} else if ((intent
					.getIntExtra(UserTopicGetService.RESPONSE_TAG, -1) == WysConstants.NOT_RECEIVED_USER_TOPICS)) {
				Toast.makeText(_ctx, "Something went wrong please try again",
						Toast.LENGTH_SHORT).show();
			}
		}

	}
	
	
	////// ON GetTopicsLIstener Implemented Methods
	

	@Override
	public void onTopicsReceived() {
		getWYSPreferences().setUserTopicExistLocal(true);
		Intent i = new Intent(_ctx, UserTopicsActivity.class);
		i.putExtra("catid", categoryClicked.get_serverId());
		startActivity(i);
	}

	@Override
	public void onTopicsNotReceived() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEmptyServerRecord() {
		// TODO Auto-generated method stub
		
	}

}
