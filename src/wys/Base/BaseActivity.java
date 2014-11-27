package wys.Base;


import wys.Helpers.PreferenceHelper;
import android.app.Activity;
import android.graphics.Typeface;

public class BaseActivity extends Activity {

	private PreferenceHelper _prefHelper;

	public PreferenceHelper getWYSPreferences() {
		if (_prefHelper == null) {
			_prefHelper = new PreferenceHelper(BaseActivity.this);
		}
		return _prefHelper;
	}

	public Typeface GetTypeFace(String Path) {

		Typeface tf = Typeface.createFromAsset(getAssets(), Path);
		return tf;
	}
	
}
