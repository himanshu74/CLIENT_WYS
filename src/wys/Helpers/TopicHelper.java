package wys.Helpers;

import java.util.ArrayList;

import android.database.Cursor;
import wys.Base.BaseDbActivity;
import wys.Business.BaseBusiness;
import wys.Business.TopicBo;
import wys.DatabaseHelpers.DBAdapter;
import wys.Modals.TopicModal;

public class TopicHelper {

	public static ArrayList<TopicBo> getCurrentTopicsbyCatId(
			DBAdapter dbAdapter, int categoryId) {
		ArrayList<TopicBo> topics = new ArrayList<TopicBo>();
		Cursor cursor = dbAdapter.getCursor(TopicModal.TABLE_NAME,
				new String[] { TopicModal.COL_SERVER_ID,
						TopicModal.COL_TOPIC_Id, TopicModal.COL_TOPIC_NAME,
						TopicModal.COL_CAT_ID, TopicModal.COL_CONCLUSION,
						TopicModal.COL_BEGIN_DATE }, TopicModal.COL_IS_ACTIVE
						+ " = 0 and "+ TopicModal.COL_CAT_ID + "=? ",
				new String[] { categoryId + "" }, null, null, null);

		if (cursor.moveToNext()) {
			while (cursor.isAfterLast() == false) {

				TopicBo topic = new TopicBo();
				topic.set_topicId(cursor.getInt(cursor
						.getColumnIndex(TopicModal.COL_TOPIC_Id)));
				topic.set_serverId(cursor
						.getColumnIndex(TopicModal.COL_SERVER_ID));
				topic.set_name(cursor.getString(cursor
						.getColumnIndex(TopicModal.COL_TOPIC_NAME)));
				topic.set_conclusion(cursor.getString(cursor
						.getColumnIndex(TopicModal.COL_CONCLUSION)));
				topic.set_beginDate(cursor.getString(cursor
						.getColumnIndex(TopicModal.COL_BEGIN_DATE)));
				topics.add(topic);
				cursor.moveToNext();
			}
		}

		return topics;
	}

	public static ArrayList<TopicBo> getPastTopicsByCatId(DBAdapter dbAdapter,
			int categoryId) {
		ArrayList<TopicBo> topics = new ArrayList<TopicBo>();
		Cursor cursor = dbAdapter.getCursor(TopicModal.TABLE_NAME,
				new String[] { TopicModal.COL_SERVER_ID,
						TopicModal.COL_TOPIC_Id, TopicModal.COL_TOPIC_NAME,
						TopicModal.COL_CAT_ID, TopicModal.COL_CONCLUSION,
						TopicModal.COL_BEGIN_DATE }, TopicModal.COL_IS_ACTIVE
						+ " = 0 and "
						+ TopicModal.COL_CAT_ID + " =? ",
				new String[] { categoryId + "" }, null, null, null);

		if (cursor.moveToNext()) {
			while (cursor.isAfterLast() == false) {

				TopicBo topic = new TopicBo();
				topic.set_topicId(cursor.getInt(cursor
						.getColumnIndex(TopicModal.COL_TOPIC_Id)));
				topic.set_serverId(cursor
						.getColumnIndex(TopicModal.COL_SERVER_ID));
				topic.set_name(cursor.getString(cursor
						.getColumnIndex(TopicModal.COL_TOPIC_NAME)));
				topic.set_conclusion(cursor.getString(cursor
						.getColumnIndex(TopicModal.COL_CONCLUSION)));
				topic.set_beginDate(cursor.getString(cursor
						.getColumnIndex(TopicModal.COL_BEGIN_DATE)));
				topics.add(topic);
				cursor.moveToNext();
			}
		}

		return topics;

	}

	public static ArrayList<TopicBo> getUpComingTopicsByCatId(
			DBAdapter dbAdapter, int categoryId) {
		ArrayList<TopicBo> topics = new ArrayList<TopicBo>();
		Cursor cursor = dbAdapter.getCursor(TopicModal.TABLE_NAME,
				new String[] { TopicModal.COL_SERVER_ID,
						TopicModal.COL_TOPIC_Id, TopicModal.COL_TOPIC_NAME,
						TopicModal.COL_CAT_ID, TopicModal.COL_CONCLUSION,
						TopicModal.COL_BEGIN_DATE }, TopicModal.COL_IS_ACTIVE
						+ " =" + BaseBusiness.UPCOMING_TOPICS + " and "
						+ TopicModal.COL_BEGIN_DATE
						+ "> datetime('now','utc') and "
						+ TopicModal.COL_CAT_ID + " =? ",
				new String[] { categoryId + "" }, null, null, null);

		if (cursor.moveToNext()) {
			while (cursor.isAfterLast() == false) {

				TopicBo topic = new TopicBo();
				topic.set_topicId(cursor.getInt(cursor
						.getColumnIndex(TopicModal.COL_TOPIC_Id)));

				topic.set_serverId(cursor.getInt(cursor
						.getColumnIndex(TopicModal.COL_SERVER_ID)));

				topic.set_name(cursor.getString(cursor
						.getColumnIndex(TopicModal.COL_TOPIC_NAME)));
				topic.set_conclusion(cursor.getString(cursor
						.getColumnIndex(TopicModal.COL_CONCLUSION)));
				topic.set_beginDate(cursor.getString(cursor
						.getColumnIndex(TopicModal.COL_BEGIN_DATE)));
				topics.add(topic);
				cursor.moveToNext();
			}
		}

		return topics;

	}

	// ////// USER SPECIFIC TOPICS

	public static ArrayList<TopicBo> getUserUpcomingTopicsByCatId(
			DBAdapter dbAdapter, int categoryId) {

		ArrayList<TopicBo> topics = new ArrayList<TopicBo>();

		String query = "select t." + TopicModal.COL_TOPIC_Id + ",t."
				+ TopicModal.COL_SERVER_ID + ",t." + TopicModal.COL_TOPIC_NAME
				+ ",t." + TopicModal.COL_CAT_ID + ",t."
				+ TopicModal.COL_CONCLUSION + ",t." + TopicModal.COL_DATEADDED
				+ ",t." + TopicModal.COL_IS_ACTIVE + ",t."
				+ TopicModal.COL_BEGIN_DATE + " from " + TopicModal.TABLE_NAME
				+ " t where t." + TopicModal.COL_SERVER_ID
				+ " not in( select tu." + TopicModal.COL_TOPIC_Id + " from "
				+ TopicModal.TOPIC_USER_TABLE
				+ " tu) and datetime('now','utc') < t."
				+ TopicModal.COL_BEGIN_DATE;

		Cursor cursor = dbAdapter.getDb().rawQuery(query, null);
		if (cursor.moveToNext()) {
			while (cursor.isAfterLast() == false) {

				TopicBo topic = new TopicBo();
				topic.set_topicId(cursor.getInt(cursor
						.getColumnIndex(TopicModal.COL_TOPIC_Id)));

				topic.set_serverId(cursor.getInt(cursor
						.getColumnIndex(TopicModal.COL_SERVER_ID)));

				topic.set_name(cursor.getString(cursor
						.getColumnIndex(TopicModal.COL_TOPIC_NAME)));
				topic.set_conclusion(cursor.getString(cursor
						.getColumnIndex(TopicModal.COL_CONCLUSION)));
				topic.set_beginDate(cursor.getString(cursor
						.getColumnIndex(TopicModal.COL_BEGIN_DATE)));
				topics.add(topic);
				cursor.moveToNext();
			}
		}
		cursor.close();
		return topics;
	}

	// // Used in User's Current Topics Fragment
	public static ArrayList<TopicBo> getUserSpecificCurrentTopics(
			DBAdapter dbAdapter, int userId) {
		ArrayList<TopicBo> topics = new ArrayList<TopicBo>();
		String query = "Select t." + TopicModal.COL_TOPIC_Id + ",t."
				+ TopicModal.COL_SERVER_ID + ",t." + TopicModal.COL_TOPIC_NAME
				+ ",t." + TopicModal.COL_CONCLUSION + ",t."
				+ TopicModal.COL_IS_ACTIVE + ",t." + TopicModal.COL_CAT_ID
				+ ",t." + TopicModal.COL_DATEADDED + " from "
				+ TopicModal.TABLE_NAME + " t where t."
				+ TopicModal.COL_SERVER_ID + " in (select tu."
				+ TopicModal.COL_TOPIC_Id + " from "
				+ TopicModal.TOPIC_USER_TABLE + " tu where tu."
				+ TopicModal.COL_USER_ID + "=" + userId + ") and  t."
				+ TopicModal.COL_IS_ACTIVE
				+ " =0 and datetime('now','utc') >= t."
				+ TopicModal.COL_BEGIN_DATE + " and datetime('now','utc') < t."
				+ TopicModal.COL_END_DATE;

		Cursor cursor = dbAdapter.getDb().rawQuery(query, null);
		if (cursor.moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				TopicBo topic = new TopicBo();
				topic.set_topicId(cursor.getInt(cursor
						.getColumnIndex(TopicModal.COL_TOPIC_Id)));

				topic.set_serverId(cursor.getInt(cursor
						.getColumnIndex(TopicModal.COL_SERVER_ID)));

				topic.set_name(cursor.getString(cursor
						.getColumnIndex(TopicModal.COL_TOPIC_NAME)));

				topic.set_conclusion(cursor.getString(cursor
						.getColumnIndex(TopicModal.COL_CONCLUSION)));
				topic.set_isActive(cursor.getInt(cursor
						.getColumnIndex(TopicModal.COL_CAT_ID)));
				// topic.set_dateAdded(cursor.getString(cursor.getColumnIndex(TopicModal.COL_DATEADDED)));
				topics.add(topic);
				cursor.moveToNext();

			}
		}
		cursor.close();
		return topics;
	}

	// // Used in User's CLosed Topics Fragment
	public static ArrayList<TopicBo> getUserSpecificPastTopics(
			DBAdapter dbAdapter, int userId) {
		ArrayList<TopicBo> topics = new ArrayList<TopicBo>();
		String query = "Select t." + TopicModal.COL_TOPIC_Id + ",t."
				+ TopicModal.COL_SERVER_ID + ",t." + TopicModal.COL_TOPIC_NAME
				+ ",t." + TopicModal.COL_CONCLUSION + ",t."
				+ TopicModal.COL_IS_ACTIVE + ",t." + TopicModal.COL_CAT_ID
				+ ",t." + TopicModal.COL_DATEADDED + " from "
				+ TopicModal.TABLE_NAME + " t where t."
				+ TopicModal.COL_SERVER_ID + " in (select tu."
				+ TopicModal.COL_TOPIC_Id + " from "
				+ TopicModal.TOPIC_USER_TABLE + " tu where tu."
				+ TopicModal.COL_USER_ID + "=" + userId + ") and t."
				+ TopicModal.COL_IS_ACTIVE
				+ " =0 and datetime('now','utc') > t."
				+ TopicModal.COL_END_DATE;

		Cursor cursor = dbAdapter.getDb().rawQuery(query, null);
		if (cursor.moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				TopicBo topic = new TopicBo();
				topic.set_topicId(cursor.getInt(cursor
						.getColumnIndex(TopicModal.COL_TOPIC_Id)));
				topic.set_serverId(cursor.getInt(cursor
						.getColumnIndex(TopicModal.COL_SERVER_ID)));
				topic.set_name(cursor.getString(cursor
						.getColumnIndex(TopicModal.COL_TOPIC_NAME)));
				topic.set_conclusion(cursor.getString(cursor
						.getColumnIndex(TopicModal.COL_CONCLUSION)));
				topic.set_isActive(cursor.getInt(cursor
						.getColumnIndex(TopicModal.COL_CAT_ID)));
				// topic.set_dateAdded(cursor.getString(cursor.getColumnIndex(TopicModal.COL_DATEADDED)));
				cursor.moveToNext();
				topics.add(topic);

			}
		}
		cursor.close();
		return topics;
	}

}
