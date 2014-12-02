package wys.Modals;

import wys.Business.CommentBo;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CommentModal extends AppModal {

	public static final String TABLE_NAME = "WYScomment";
	public static final String COL_COMMENT_ID = "comment_id";
	public static final String COL_COMMENT = "comment";
	public static final String COL_USER_ID = "user_id";
	public static final String COL_USERNAME = "username";
	public static final String COL_TOPIC_Id = "topic_id";
	public static final String COL_SERVER_ID = "server_id";
	public static final String COL_TIME_ADDED = "time_added";
	private static final String CLASS_TAG = "CommentModal";

	public static final String CREATE_TABLE = "create table if not exists "
			+ TABLE_NAME + " (" + COL_COMMENT_ID
			+ " integer primary key autoincrement," + COL_SERVER_ID
			+ " integer not null," + COL_TOPIC_Id + " integer not null,"
			+ COL_USER_ID + " integer not null," + COL_COMMENT + " varchar,"
			+ COL_USERNAME + " varhcar," + COL_TIME_ADDED + ")";

	public static boolean saveComment(SQLiteDatabase db, CommentBo commentBo) {
		if (commentBo.equals(null)) {
			return FAILURE;
		} else {
			try {
				ContentValues contentValues = new ContentValues();
				contentValues.put(COL_SERVER_ID, commentBo.get_serverId());
				contentValues.put(COL_TOPIC_Id, commentBo.get_topicId());
				contentValues.put(COL_USER_ID, commentBo.get_userId());
				contentValues.put(COL_COMMENT, commentBo.get_comment());
				contentValues.put(COL_USERNAME, commentBo.get_username());
				contentValues.put(COL_TIME_ADDED, commentBo.get_timeAdded());
				if (db.insertOrThrow(TABLE_NAME, "", contentValues) == SQL_INSERT_ERROR_CODE) {
					return FAILURE;
				} else {
					return SUCCESS;
				}
			} catch (Exception exception) {
				Log.d(CLASS_TAG,
						"Error Saving COMMENTS, Enteres in Exception with Messg"
								+ exception.getMessage());
				return FAILURE;
			}
		}
	}
}
