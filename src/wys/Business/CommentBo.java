package wys.Business;

public class CommentBo extends BaseBusiness {
	
	
	private int commentId;
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
	public int getTopic_id() {
		return topic_id;
	}
	public void setTopic_id(int topic_id) {
		this.topic_id = topic_id;
	}
	private String _comment;
	private int _userId;
	private int topic_id;
	 
	

}
