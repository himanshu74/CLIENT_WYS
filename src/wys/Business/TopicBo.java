package wys.Business;

import java.io.Serializable;
import java.util.Date;

public class TopicBo extends BaseBusiness implements Serializable {

	private int _topicId;

	public int get_topicId() {
		return _topicId;
	}

	public void set_topicId(int _topicId) {
		this._topicId = _topicId;
	}

	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public int get_domainId() {
		return _domainId;
	}

	public void set_domainId(int _domainId) {
		this._domainId = _domainId;
	}

	public int get_userId() {
		return _userId;
	}

	public void set_userId(int _userId) {
		this._userId = _userId;
	}

	public Date get_dateAdded() {
		return _dateAdded;
	}

	public void set_dateAdded(Date _dateAdded) {
		this._dateAdded = _dateAdded;
	}

	public Date get_beginDate() {
		return _beginDate;
	}

	public void set_beginDate(Date _beginDate) {
		this._beginDate = _beginDate;
	}

	private String _name;
	private String _conclusion;

	public String get_conclusion() {
		return _conclusion;
	}

	public void set_conclusion(String _conclusion) {
		this._conclusion = _conclusion;
	}

	private int _domainId;
	private int _userId;
	private Date _dateAdded;
	private Date _beginDate;

}
