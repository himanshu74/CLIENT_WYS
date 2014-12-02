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
	private static final String COMMENTS_SERVERID="CserverID";
	private static final String IS_COMMENT_DOWNLOADED="isCdown";

	// PRIVATE VARIABLES
	private Context ctx;
	private boolean _firstTimeUse;
	private boolean istopicsExistLocal;
    private boolean isUserTopicExistLocal;
    private int _commentsServerId;
    private boolean _isCommentFirsttimeDown;
    
	

	public boolean is_isCommentFirsttimeDown() {
		return _isCommentFirsttimeDown;
	}

	public void set_isCommentFirsttimeDown(boolean _isCommentFirsttimeDown) {
		this._isCommentFirsttimeDown = _isCommentFirsttimeDown;
		UpdateCOmmentDownloaded(_isCommentFirsttimeDown);
	}

	public int get_commentsServerId() {
		return _commentsServerId;
	}

	public void set_commentsServerId(int _commentsServerId) {
		this._commentsServerId = _commentsServerId;
		UpdateCommentServerId(_commentsServerId);
	}

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
	   _commentsServerId = wysPreferences.getInt(COMMENTS_SERVERID, -1);
		_isCommentFirsttimeDown= wysPreferences.getBoolean(IS_COMMENT_DOWNLOADED, true);
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
	
	private void UpdateCommentServerId(int id){
		SharedPreferences wysPrefs = ctx.getSharedPreferences(PREF_KEY,
				ctx.MODE_PRIVATE);
		SharedPreferences.Editor editor = wysPrefs.edit();
		editor.putInt(COMMENTS_SERVERID, id);
		editor.commit();
	}
	
	private void UpdateCOmmentDownloaded(boolean value){
		SharedPreferences wysPrefs = ctx.getSharedPreferences(PREF_KEY,
				ctx.MODE_PRIVATE);
		SharedPreferences.Editor editor = wysPrefs.edit();
		editor.putBoolean(IS_COMMENT_DOWNLOADED, value);
		editor.commit();
	}
}
