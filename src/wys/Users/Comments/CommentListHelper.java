package wys.Users.Comments;

import java.util.ArrayList;

import android.content.Context;
import android.widget.Toast;

import wys.Business.CommentBo;
import wys.DatabaseHelpers.DBAdapter;
import wys.Helpers.CommentHelper;

public class CommentListHelper {

	private DBAdapter _dbAdapter;

	public CommentListHelper(DBAdapter dbAdapter) {
		this._dbAdapter = dbAdapter;
	}

	private ArrayList<CommentBo> getComments(int topicId) {

		return CommentHelper.getComments(_dbAdapter, topicId);
	}

	public CommentHolderAdapter buildCommentHolderAdapter(Context context,
			int resourceId, int topicId) {
		if(topicId == -1){
			Toast.makeText(context, "Topic Id Not Received", Toast.LENGTH_LONG).show();
			return null;
		}else {ArrayList<CommentBo> list = getComments(topicId);
		CommentHolderAdapter comAdapter = new CommentHolderAdapter(context,
				resourceId);
		comAdapter.addAll(list);
		return comAdapter;
		}
		
		
	}
}
