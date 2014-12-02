package wys.Background;

import java.util.ArrayList;
import java.util.List;

import wys.Api.SessionManager;
import wys.Api.WysUserApi;
import wys.BroadcastReceivers.WysBroadcastConstants;
import wys.Business.TopicBo;
import wys.Business.UserBo;
import wys.DatabaseHelpers.DBAdapter;
import wys.Fragments.MyCategoriesFragment;
import wys.Helpers.WysConstants;
import wys.Modals.TopicModal;
import wys.Users.Topics.Fragments.UserMyTopicFragment;
import wys.Users.Topics.Fragments.UserPastTopicFragment;
import wys.Users.Topics.Fragments.UserUpcomingTopicFragment;
import android.app.IntentService;
import android.content.Intent;

public class UserTopicGetService extends IntentService {

	private UserBo _user;
	private WysUserApi _wyUserApi;
	private String mesg1 = "Received";
	private String mesg2 = "Not Received";
	public static String RESPONSE_TAG = "response";
	public static DBAdapter _dbAdapter;
	private int _catId;
	private String TOPIC_FLAG = "Tflag";
	public static String TOPIC_DOWN_REQ = "TDR";
	private ArrayList<TopicBo> _listUpcoming;
	private ArrayList<TopicBo> _listCurrent;
	private ArrayList<TopicBo> _listPast;

	public UserTopicGetService() {
		super("UserTopicGetService");
	}

	/*
	 * @Override protected void onHandleIntent(Intent intent) {
	 * 
	 * boolean status;
	 * 
	 * user = (UserBo) SessionManager.getUserBo(); // this._dbAdapter =
	 * (DBAdapter
	 * )intent.getSerializableExtra(MyCategoriesFragment.TOPIC_RECEIVER_FLAG);
	 * this._catId = intent.getExtras().getInt("catid");
	 * 
	 * _wyUserApi = new WysUserApi(); ArrayList<TopicBo> topics =
	 * _wyUserApi.doGetUserTopics(user .get_userId());
	 * 
	 * if(topics !=null){ for(TopicBo topic:topics){ if(_dbAdapter !=null){
	 * 
	 * status = TopicModal.saveTopic(_dbAdapter.getDb(), topic); status
	 * =TopicModal.saveTopicUser(_dbAdapter.getDb(), topic.get_serverId(),
	 * user.get_userId()); _dbAdapter=null; }
	 * 
	 * } }
	 * 
	 * Intent broadCastIntent = new Intent();
	 * broadCastIntent.setAction(WysBroadcastConstants
	 * .USER_GET_TOPIC_INTENT_ACTION);
	 * broadCastIntent.addCategory(Intent.CATEGORY_DEFAULT); if (topics != null)
	 * { broadCastIntent.putExtra(RESPONSE_TAG,
	 * WysConstants.RECEIVED_USER_TOPICS); broadCastIntent.putExtra("catid",
	 * _catId);
	 * 
	 * } else { broadCastIntent.putExtra(RESPONSE_TAG,
	 * WysConstants.NOT_RECEIVED_USER_TOPICS); broadCastIntent.putExtra("catid",
	 * _catId); } sendBroadcast(broadCastIntent); }
	 */

	@Override
	protected void onHandleIntent(Intent intent) {

		_catId= intent.getIntExtra("catid", -1);
		boolean status = false;
		UserBo user = (UserBo) SessionManager.getUserBo();
		TOPIC_FLAG = intent.getExtras().getString(TOPIC_DOWN_REQ);

		_wyUserApi = new WysUserApi();
		
		UserUpcomingTopicFragment._UpcomingTopics = _wyUserApi
				.getUserUpcomingTopics(user.get_userId(),_catId);
		if(UserUpcomingTopicFragment._UpcomingTopics !=null){
		UserMyTopicFragment._usersCurrentTopics=	_wyUserApi.getUserCurrentTopics(user.get_userId(),_catId);
		}
		if(UserMyTopicFragment._usersCurrentTopics !=null)
		{
			UserPastTopicFragment._usersPastTopics = _wyUserApi.getUserPastTopics(user.get_userId(),_catId);
		}
		
		if(!UserUpcomingTopicFragment._UpcomingTopics.equals(null)  && !UserMyTopicFragment._usersCurrentTopics.equals(null) && !UserPastTopicFragment._usersPastTopics.equals(null)){
			status = true;
		}
		
		sendBroadcast(WysBroadcastConstants.USER_GET_TOPIC_INTENT_ACTION,status);

	}

	private void sendBroadcast(String IntentAction, boolean status) {
		Intent broadCastIntent = new Intent();
		broadCastIntent.setAction(IntentAction);
		broadCastIntent.addCategory(Intent.CATEGORY_DEFAULT);
       if(status){
    		broadCastIntent.putExtra(RESPONSE_TAG,
    				WysConstants.RECEIVED_USER_TOPICS);
       }
       else {
    		broadCastIntent.putExtra(RESPONSE_TAG,
    				WysConstants.NOT_RECEIVED_USER_TOPICS);
       }
	
		sendBroadcast(broadCastIntent);

	}
}
