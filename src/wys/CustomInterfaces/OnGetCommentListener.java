package wys.CustomInterfaces;

import java.util.ArrayList;

import wys.Business.CommentBo;
import wys.Business.TopicBo;

public interface OnGetCommentListener {

	void onCommentsReceived();
	void onCommentNotReceived();
	void onCLosedCommentsReceived(ArrayList<CommentBo> comments);
	
}
