package wys.Helpers;

import java.util.ArrayList;

import wys.Business.CommentBo;
import wys.DatabaseHelpers.DBAdapter;
import wys.Modals.CommentModal;
import android.database.Cursor;

public class CommentHelper {

	public static ArrayList<CommentBo> getComments(DBAdapter dbAdapter,
			int topicId) {

		ArrayList<CommentBo> comList = new ArrayList<CommentBo>();
		Cursor cursor =

		dbAdapter.getCursor(CommentModal.TABLE_NAME, new String[] {
				CommentModal.COL_COMMENT_ID, CommentModal.COL_SERVER_ID,
				CommentModal.COL_COMMENT, CommentModal.COL_TOPIC_Id,
				CommentModal.COL_USER_ID, CommentModal.COL_USERNAME,
				CommentModal.COL_TIME_ADDED }, CommentModal.COL_TOPIC_Id
				+ " =?", new String[] { topicId + "" }, null, null,
				CommentModal.COL_SERVER_ID + " desc");

		if (cursor.moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				CommentBo commentBo = new CommentBo();
				commentBo.setCommentId(cursor.getInt(cursor
						.getColumnIndex(CommentModal.COL_COMMENT_ID)));
				commentBo.set_serverId(cursor.getInt(cursor
						.getColumnIndex(CommentModal.COL_SERVER_ID)));
				commentBo.set_comment(cursor.getString(cursor
						.getColumnIndex(CommentModal.COL_COMMENT)));
				commentBo.set_userId(cursor.getInt(cursor
						.getColumnIndex(CommentModal.COL_USER_ID)));
				commentBo.set_username(cursor.getString(cursor
						.getColumnIndex(CommentModal.COL_USERNAME)));
				commentBo.set_timeAdded(cursor.getString(cursor
						.getColumnIndex(CommentModal.COL_TIME_ADDED)));

				comList.add(commentBo);
				cursor.moveToNext();

			}
		}
		return comList;
	}

}
