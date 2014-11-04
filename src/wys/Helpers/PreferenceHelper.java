package wys.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceHelper {

	// DEFINED KEYS
	private static final String PREF_KEY = "wys_pref";
	private static final String IS_FIRST_TIME_USE = "firstTimeUse";

	// PRIVATE VARIABLES
	private Context ctx;
	private boolean _firstTimeUse;

	public boolean is_firstTimeUse() {
		return _firstTimeUse;
	}

	public void set_firstTimeUse(boolean _firstTimeUse) {
		this._firstTimeUse = _firstTimeUse;
		UpdateIsFirstTimeUse(_firstTimeUse);
	}

	
	// Constructor
	public PreferenceHelper(Context context) {
		this.ctx = context;
		LoadPreferences();
	}

	private void LoadPreferences() {
		SharedPreferences wysPreferences = ctx.getSharedPreferences(PREF_KEY,
				ctx.MODE_PRIVATE);

		_firstTimeUse = wysPreferences.getBoolean(IS_FIRST_TIME_USE, true);

	}

	private void UpdateIsFirstTimeUse(boolean value) {
		SharedPreferences wysPrefs = ctx.getSharedPreferences(PREF_KEY,
				ctx.MODE_PRIVATE);
		SharedPreferences.Editor editor = wysPrefs.edit();
		editor.putBoolean(IS_FIRST_TIME_USE, false);
		editor.commit();
	}

}
