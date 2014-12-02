package wys.Background;

import wys.Api.SessionManager;
import wys.Api.WysUserApi;
import wys.BroadcastReceivers.WysBroadcastConstants;
import wys.Business.UserBo;
import wys.Fragments.CurrentTopicFragment;
import wys.Fragments.PastTopicFragment;
import wys.Fragments.UpcomingTopicFragment;
import wys.Helpers.WysConstants;
import wys.Users.Topics.Fragments.UserMyTopicFragment;
import wys.Users.Topics.Fragments.UserPastTopicFragment;
import wys.Users.Topics.Fragments.UserUpcomingTopicFragment;
import android.app.IntentService;
import android.content.Intent;

public class OrgTopicGetService extends IntentService {

	private int _catId = -1;
	private WysUserApi _wyUserApi;
	public static String RESPONSE_TAG = "response";

	public OrgTopicGetService() {
		super("OrgTopicGetService");

	}

	@Override
	protected void onHandleIntent(Intent intent) {
		_catId = intent.getIntExtra("catid", -1);
		UserBo user = (UserBo) SessionManager.getUserBo();
		int userId = user.get_userId();
		_wyUserApi = new WysUserApi();
		boolean status = false;

		UpcomingTopicFragment.orgUpcomingList = _wyUserApi
				.GetOrgUpcomingTopics(userId, _catId);
		if (UpcomingTopicFragment.orgUpcomingList != null) {
			CurrentTopicFragment.orgCurrentTopics = _wyUserApi
					.GetOrgCurrentTopics(userId, _catId);

		}
		if (CurrentTopicFragment.orgCurrentTopics != null) {
			PastTopicFragment.orgPastTopics = _wyUserApi.GetOrgClosedTopics(
					userId, _catId);
		}
		if (!UpcomingTopicFragment.orgUpcomingList.equals(null)
				&& !CurrentTopicFragment.orgCurrentTopics.equals(null)
				&& !PastTopicFragment.orgPastTopics.equals(null)) {
			status = true;
		}
		sendBroadcast(WysBroadcastConstants.ORG_GET_TOPIC_INTENT_ACTION,
				status);
	}

	private void sendBroadcast(String IntentAction, boolean status) {
		Intent broadCastIntent = new Intent();
		broadCastIntent.setAction(IntentAction);
		broadCastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		if (status) {
			broadCastIntent.putExtra(RESPONSE_TAG,
					WysConstants.RECEIVED_USER_TOPICS);
			broadCastIntent.putExtra("catid", _catId);
		} else {
			broadCastIntent.putExtra(RESPONSE_TAG,
					WysConstants.NOT_RECEIVED_USER_TOPICS);
			broadCastIntent.putExtra("catid", _catId);
		}

		sendBroadcast(broadCastIntent);

	}
}
