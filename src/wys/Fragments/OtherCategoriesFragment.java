package wys.Fragments;

import java.io.Serializable;
import java.util.ArrayList;

import wys.Adapter.CategoryListAdaptor;
import wys.Adapter.TopicAdapter;
import wys.Api.SessionManager;
import wys.AsyncTask.UserAsynctask;
import wys.Business.CategoryBo;
import wys.Business.UserBo;
import wys.CustomInterfaces.OnUserCatPost;
import wys.DatabaseHelpers.DBAdapter;
import wys.ForumObjects.UserCategoriesActivity;
import wys.Helpers.CategoryHelper;

import com.wys.R;

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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class OtherCategoriesFragment extends Fragment implements OnUserCatPost,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ListView _otherCategoriesList;
	private Context _ctx;
	private DBAdapter _dbAdapter;
	private ArrayList<CategoryBo> CatsSelected;
	public static boolean CheckBoxState[];
	public static boolean IsCheckBoxChecked;
	public static boolean IsDataChanged;
	MyNotiReceiver r;
	
	public OtherCategoriesFragment(Context context, DBAdapter dbAdapter){
	this._ctx = context;
	this._dbAdapter = dbAdapter;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
			View view = inflater.inflate(R.layout.other_categories, container, false);
			_otherCategoriesList = (ListView) view.findViewById(R.id.lv_other_cats);
			CategoryListAdaptor catListAdp = new CategoryListAdaptor(_ctx, getRemainingCategories());
			_otherCategoriesList.setAdapter(catListAdp);
			return view;
			// return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	
	private ArrayList<CategoryBo> getRemainingCategories(){
		UserBo user = (UserBo)SessionManager.getUserBo();
		return CategoryHelper.getRemainingCats(_dbAdapter, user.get_userId());
	}

   
	public void refreshData(){
		CategoryListAdaptor catListAdp = new CategoryListAdaptor(_ctx, getRemainingCategories());
		_otherCategoriesList.setAdapter(catListAdp);
	}
	
	
	public void onEditStatePressed() {
		CategoryListAdaptor.IsEditEnabled = true;
		CategoryListAdaptor catListAdp = new CategoryListAdaptor(_ctx, getRemainingCategories());
		_otherCategoriesList.setAdapter(catListAdp);	
	}
	
	public void onCanCelPressed()
	{
		CategoryListAdaptor.IsEditEnabled = false;
		CategoryListAdaptor catListAdp = new CategoryListAdaptor(_ctx, getRemainingCategories());
		_otherCategoriesList.setAdapter(catListAdp);
	}
	
	public void onConfirmPressed()
	{
		postUserCats();
	}
	
	private void postUserCats() {
		int length = CheckBoxState.length;
		CatsSelected = new ArrayList<CategoryBo>();
		if (IsCheckBoxChecked) {
			for (int i = 0; i < length; i++) {
				if (CheckBoxState[i]) {
					CategoryBo category = (CategoryBo) _otherCategoriesList
							.getItemAtPosition(i);
					CatsSelected.add(category);

				}

			}
			UserBo sessionedUser = (UserBo) SessionManager.getUserBo();
			UserBo user = new UserBo();
			user.set_userId(sessionedUser.get_userId());
			user.setUserCategories(CatsSelected);
			UserAsynctask userAsynctask = new UserAsynctask(_ctx,_dbAdapter,OtherCategoriesFragment.this);
			userAsynctask.executePostUserCategories(user);
		} else {
			Toast.makeText(_ctx, "No Item was Selected",
					Toast.LENGTH_SHORT).show();
		}
	}


	@Override
	public void onPostSuccess() {
		CategoryListAdaptor.IsEditEnabled = false;
		refreshData();
		MyCategoriesFragment.DataChanged= true;
		UserCategoriesActivity.Hide_Layer2();
		
	}

	
	@Override
	public void onResume() {
		super.onResume();
		r = new MyNotiReceiver();
	    LocalBroadcastManager.getInstance(_ctx).registerReceiver(r,
	            new IntentFilter("TAG_REFRESH_Other"));

	}
	
	@Override
	public void onPause() {
		super.onPause();
		LocalBroadcastManager.getInstance(_ctx).unregisterReceiver(r);

	}
	

	@Override
	public void onPostFail() {
		// TODO Auto-generated method stub
		
	}

	
	private class MyNotiReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			OtherCategoriesFragment.IsDataChanged= false;
                   refreshData();
		}
		
	}
	
	
	
	
}
