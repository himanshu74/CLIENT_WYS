package wys.Adapter;

import wys.Business.CategoryBo;
import wys.DatabaseHelpers.DBAdapter;
import wys.Fragments.CurrentTopicFragment;
import wys.Fragments.MyCategoriesFragment;
import wys.Fragments.OtherCategoriesFragment;
import wys.Fragments.PastTopicFragment;
import wys.Fragments.UpcomingTopicFragment;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class CategoryPagerAdaptor extends FragmentPagerAdapter {

	private static final int USER_CAT_FRAG_COUNT=2;
	private Context _ctx;
	private DBAdapter _dbAdapter;
	private OtherCategoriesFragment _otherCategoriesFragment;
	private MyCategoriesFragment _myCategoriesFragment;
	public static boolean IsConfirmDeletePressed;
	
	
	public CategoryPagerAdaptor(FragmentManager fm,Context context,
			DBAdapter dbAdapter) {
		super(fm);
		this._dbAdapter = dbAdapter;
		this._ctx = context;
	}

	@Override
	public Fragment getItem(int index) {
		
		switch (index) {
		case 0: {
			_otherCategoriesFragment =new OtherCategoriesFragment(_ctx,_dbAdapter);
			return _otherCategoriesFragment;

		}

		case 1: {

			_myCategoriesFragment = new MyCategoriesFragment(_ctx,_dbAdapter);
			return _myCategoriesFragment;
		}


		}

		return null;
	}

	@Override
	public int getCount() {
		
		return USER_CAT_FRAG_COUNT;
	}

	
	public void onEditStatePressed() {
         	if(_otherCategoriesFragment !=null){
         		_otherCategoriesFragment.onEditStatePressed();
         	}
         	else {
         		//Toast.makeText(_ctx, "NullValue for OtherCategories Object", Toast.LENGTH_LONG).show();
         	}
	}
	
	public void onDeletePressed(){
		if(_myCategoriesFragment !=null){
			_myCategoriesFragment.onDeletePressed();
     	}
     	else {
     		//Toast.makeText(_ctx, "NullValue for MyCategories Object", Toast.LENGTH_LONG).show();
     	}
	}
	
	public void onCanCelPressed()
	{
		if(_otherCategoriesFragment !=null){
     		_otherCategoriesFragment.onCanCelPressed();
     	}
     	else {
     		//Toast.makeText(_ctx, "NullValue for OtherCategories Object", Toast.LENGTH_LONG).show();
     	}
	}
	
	public void onConfirmPressed()
	{
		if(IsConfirmDeletePressed){
			if(_myCategoriesFragment !=null){
				_myCategoriesFragment.onConfirmDelete();
			}
		}else {
			if(_otherCategoriesFragment !=null){
	     		_otherCategoriesFragment.onConfirmPressed();
	     	}
	     	else {
	     		//Toast.makeText(_ctx, "NullValue for OtherCategories Object", Toast.LENGTH_LONG).show();
	     	}
		}
		
	}

}
