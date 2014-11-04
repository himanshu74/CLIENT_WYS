package wys.Base;

import wys.DatabaseHelpers.DBAdapter;
import wys.Helpers.PreferenceHelper;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;

public class BaseDbActivity extends Activity {

	protected DBAdapter dbAdapter;
	protected Cursor cursor = null;

	private PreferenceHelper _prefHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		if (dbAdapter == null) 
		{
			dbAdapter = new DBAdapter(BaseDbActivity.this);
			dbAdapter.open(true);
		}

	}

	private PreferenceHelper GetWYSPreferences() 
	{
		if (_prefHelper == null) 
		{
			_prefHelper = new PreferenceHelper(BaseDbActivity.this);
		}

		return _prefHelper;
	}

}