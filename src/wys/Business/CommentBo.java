package wys.Business;

public class CommentBo extends BaseBusiness {

	private int commentId;
	private String _timeAdded;
	private String _username;
	private String _comment;
	private int _userId;
	private int _topicId;


	public String get_timeAdded() {
		return _timeAdded;
	}

	public void set_timeAdded(String _timeAdded) {
		this._timeAdded = _timeAdded;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String get_comment() {
		return _comment;
	}

	public void set_comment(String _comment) {
		this._comment = _comment;
	}

	public int get_userId() {
		return _userId;
	}

	public void set_userId(int _userId) {
		this._userId = _userId;
	}


	public int get_topicId() {
		return _topicId;
	}

	public void set_topicId(int _topicId) {
		this._topicId = _topicId;
	}

	public String get_username() {
		return _username;
	}

	public void set_username(String _username) {
		this._username = _username;
	}



}
