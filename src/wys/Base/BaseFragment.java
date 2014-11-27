package wys.Base;

import wys.Helpers.PreferenceHelper;
import android.content.Context;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {
	private PreferenceHelper _prefHelper;
	private Context _ctx;

      public BaseFragment(Context context){
    	  this._ctx = context;
      }
	public PreferenceHelper getWYSPreferences() {
		if (_prefHelper == null) {
			_prefHelper = new PreferenceHelper(_ctx);
		}
		return _prefHelper;
	}
	
}
