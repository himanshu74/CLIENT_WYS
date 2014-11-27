package wys.Base;

import wys.DatabaseHelpers.DBAdapter;
import wys.Helpers.PreferenceHelper;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseFragmentActivity extends FragmentActivity {
	private PreferenceHelper _prefHelper;
	public DBAdapter dbAdapter;

	@Override
	protected void onCreate(Bundle arg0) {

		super.onCreate(arg0);
		if (dbAdapter == null) {
			dbAdapter = new DBAdapter(BaseFragmentActivity.this);
			dbAdapter.open(true);
		}
	}

	public PreferenceHelper GetWYSPreferences() {
		if (_prefHelper == null) {
			_prefHelper = new PreferenceHelper(BaseFragmentActivity.this);
		}
		return _prefHelper;
	}

	public Typeface GetTypeFace(String Path) {

		Typeface tf = Typeface.createFromAsset(getAssets(), Path);
		return tf;
	}
}
