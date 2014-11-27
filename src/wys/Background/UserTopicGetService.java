package wys.Background;

import java.util.ArrayList;

import wys.Api.SessionManager;
import wys.Api.WysUserApi;
import wys.BroadcastReceivers.WysBroadcastConstants;
import wys.Business.TopicBo;
import wys.Business.UserBo;
import wys.DatabaseHelpers.DBAdapter;
import wys.Fragments.MyCategoriesFragment;
import wys.Helpers.WysConstants;
import wys.Modals.TopicModal;
import android.app.IntentService;
import android.content.Intent;

public class UserTopicGetService extends IntentService {

	private UserBo user;
	private WysUserApi _wyUserApi;
	private String mesg1 = "Received";
	private String mesg2 = "Not Received";
	public static String RESPONSE_TAG = "response";
	public static DBAdapter _dbAdapter;
	private int _catId;

	public UserTopicGetService() {
		super("UserTopicGetService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		boolean status;
		
		user = (UserBo) SessionManager.getUserBo();
     //   this._dbAdapter = (DBAdapter)intent.getSerializableExtra(MyCategoriesFragment.TOPIC_RECEIVER_FLAG);
		this._catId = intent.getExtras().getInt("catid");
        
        _wyUserApi = new WysUserApi();
		ArrayList<TopicBo> topics = _wyUserApi.doGetUserTopics(user
				.get_userId());
		
		if(topics !=null){
			for(TopicBo topic:topics){
				if(_dbAdapter !=null){
					
					status = TopicModal.saveTopic(_dbAdapter.getDb(), topic);
					status =TopicModal.saveTopicUser(_dbAdapter.getDb(), topic.get_serverId(), user.get_userId());
                 _dbAdapter=null;
				}

			}
		}
		
		Intent broadCastIntent = new Intent();
		broadCastIntent.setAction(WysBroadcastConstants.USER_GET_TOPIC_INTENT_ACTION);
		broadCastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		if (topics != null) {
			broadCastIntent.putExtra(RESPONSE_TAG, WysConstants.RECEIVED_USER_TOPICS);
			broadCastIntent.putExtra("catid", _catId);

		} else {
			broadCastIntent.putExtra(RESPONSE_TAG, WysConstants.NOT_RECEIVED_USER_TOPICS);
			broadCastIntent.putExtra("catid", _catId);
		}
		sendBroadcast(broadCastIntent);
	}

}
