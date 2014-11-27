package wys.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceHelper {

	// DEFINED KEYS
	private static final String PREF_KEY = "wys_pref";
	private static final String IS_FIRST_TIME_USE = "firstTimeUse";
	private static final String IS_TOPICS_EXISTS_LOCAL = "topicsLocal";
	private static final String IS_USER_TOPICS_EXIST_LOCAL="userTopicsLocal";

	// PRIVATE VARIABLES
	private Context ctx;
	private boolean _firstTimeUse;
	private boolean istopicsExistLocal;
    private boolean isUserTopicExistLocal;
    
	

	public boolean isUserTopicExistLocal() {
		return isUserTopicExistLocal;
	}

	public void setUserTopicExistLocal(boolean isUserTopicExistLocal) {
		this.isUserTopicExistLocal = isUserTopicExistLocal;
		UpdateUserTopicsInLocal(isUserTopicExistLocal);
	}

	public boolean isIstopicsExistLocal() {
		return istopicsExistLocal;
	}

	public void setIstopicsExistLocal(boolean istopicsExistLocal) {
		this.istopicsExistLocal = istopicsExistLocal;
		UpdateTopicsInLocal(istopicsExistLocal);
	}

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
		istopicsExistLocal = wysPreferences.getBoolean(IS_TOPICS_EXISTS_LOCAL,
				false);
		isUserTopicExistLocal	 = wysPreferences.getBoolean(IS_USER_TOPICS_EXIST_LOCAL,
				false);
	}

	private void UpdateIsFirstTimeUse(boolean value) {
		SharedPreferences wysPrefs = ctx.getSharedPreferences(PREF_KEY,
				ctx.MODE_PRIVATE);
		SharedPreferences.Editor editor = wysPrefs.edit();
		editor.putBoolean(IS_FIRST_TIME_USE, false);
		editor.commit();
	}

	private void UpdateTopicsInLocal(boolean value) {
		SharedPreferences wysPrefs = ctx.getSharedPreferences(PREF_KEY,
				ctx.MODE_PRIVATE);
		SharedPreferences.Editor editor = wysPrefs.edit();
		editor.putBoolean(IS_TOPICS_EXISTS_LOCAL, value);
		editor.commit();
	}

	
	private void UpdateUserTopicsInLocal(boolean value) {
		SharedPreferences wysPrefs = ctx.getSharedPreferences(PREF_KEY,
				ctx.MODE_PRIVATE);
		SharedPreferences.Editor editor = wysPrefs.edit();
		editor.putBoolean(IS_USER_TOPICS_EXIST_LOCAL, value);
		editor.commit();
	}
}
