package wys.Modals;

import wys.Business.TopicBo;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TopicModal extends AppModal {

	public static final String TABLE_NAME = "WYSTopic";
	public static final String TOPIC_USER_TABLE = "topic_User";
	public static final String CLASS_TAG = "TopicModal";
	public static final String COL_TOPIC_Id = "topic_id";
	public static final String COL_SERVER_ID = "server_id";
	public static final String COL_CAT_ID = "cat_id";
	public static final String COL_USER_ID = "user_id";
	public static final String COL_IS_SERVED = "isServed";
	public static final String COL_TOPIC_NAME = "topic_name";
	public static final String COL_CONCLUSION = "conclusion";
	public static final String COL_DATEADDED = "date_added";
	public static final String COL_BEGIN_DATE = "begin_date";
	public static final String COL_TOPIC_USER_ID = "id";
	public static final String COL_IS_ACTIVE = "is_active";

	public static final String CREATE_TABLE = "create table if not exists "
			+ TABLE_NAME + "( " + COL_TOPIC_Id
			+ " integer primary key autoincrement," + COL_SERVER_ID
			+ " integer," + COL_CAT_ID + " integer," + COL_USER_ID
			+ " integer," + COL_TOPIC_NAME + " varchar(20)," + COL_CONCLUSION
			+ " varchar(20)," + COL_BEGIN_DATE + " varchar,"
			+ COL_IS_ACTIVE + " integer," + COL_IS_SERVED + " integer,"
			+ COL_DATEADDED + " varchar(20))";

	public static final String CREATE_TOPIC_USER_TABLE = "create table if not exists "
			+ TOPIC_USER_TABLE
			+ "( "
			+ COL_TOPIC_USER_ID
			+ " integer primary key autoincrement,"
			+ COL_SERVER_ID
			+ " integer,"
			+ COL_TOPIC_Id
			+ " integer,"
			+ COL_USER_ID
			+ " integer)";

	public static boolean saveTopic(SQLiteDatabase db, TopicBo topic) {
		if (topic.equals(null)) {
			return FAILURE;
		} else {
			try {
				ContentValues contentValues = new ContentValues();
				contentValues.put(COL_SERVER_ID, topic.get_serverId());
				contentValues.put(COL_CAT_ID, topic.get_domainId());
				contentValues.put(COL_TOPIC_NAME, topic.get_name());
				contentValues.put(COL_USER_ID, topic.get_userId());
				contentValues.put(COL_IS_ACTIVE, topic.get_isActive());
				contentValues.put(COL_IS_SERVED, topic.get_isServed());
				if (topic.get_conclusion() != null) {
					contentValues.put(COL_CONCLUSION, topic.get_conclusion());
				}
				if (topic.get_dateAdded() != null) {
					contentValues.put(COL_DATEADDED,
							getFormatedDate(topic.get_dateAdded()));
				}
                if(topic.get_beginDateString() !=null){
                	contentValues.put(COL_BEGIN_DATE, topic.get_beginDateString());
                }
				

				if (db.insertOrThrow(TABLE_NAME, "", contentValues) == SQL_INSERT_ERROR_CODE) {
					return FAILURE;
				} else {

					return SUCCESS;
				}
			} catch (Exception exception) {
				Log.d(CLASS_TAG,
						"Error Saving Topic, Enteres in Exception with Messg"
								+ exception.getMessage());
				return FAILURE;
			}
		}

	}

	public static boolean saveTopicUser(SQLiteDatabase db, int topicId,
			int userId) {
		if (topicId == -1 || userId == -1) {
			return FAILURE;

		} else {
			try {
				ContentValues contentValues = new ContentValues();
				contentValues.put(COL_TOPIC_Id, topicId);
				contentValues.put(COL_USER_ID, userId);

				if (db.insertOrThrow(TOPIC_USER_TABLE, "", contentValues) == SQL_INSERT_ERROR_CODE) {
					return FAILURE;
				} else {

					return SUCCESS;
				}
			} catch (Exception exception) {
				Log.d(CLASS_TAG,
						"Error Saving TOPIC_USER, Enteres in Exception with Messg"
								+ exception.getMessage());
				return FAILURE;
			}

		}
	}

}
