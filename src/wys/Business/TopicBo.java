package wys.Business;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TopicBo extends BaseBusiness implements Serializable {

	private int _topicId;
	private int _domainId;
	private int _userId;
	private Date _dateAdded;
	private String _beginDate;
	private String _keyword;
	private int _isActive;
	private int _isServed;
	private Date _beginDateUTC;
	private long _bgeindateUnixUTC;
	private String _beginDateString;
	public String get_beginDateString() {
		return _beginDateString;
	}

	public void set_beginDateString(String _beginDateString) {
		this._beginDateString = _beginDateString;
	}

	public String get_endDateString() {
		return _endDateString;
	}

	public void set_endDateString(String _endDateString) {
		this._endDateString = _endDateString;
	}

	private String _endDateString;

	public long get_bgeindateUnixUTC() {
		return _bgeindateUnixUTC;
	}

	public void set_bgeindateUnixUTC(long _bgeindateUnixUTC) {
		this._bgeindateUnixUTC = _bgeindateUnixUTC;
	}

	public Date get_beginDateUTC() {
		return _beginDateUTC;
	}

	public void set_beginDateUTC(Date _beginDateUTC) {
		this._beginDateUTC = _beginDateUTC;
	}

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

	public String get_beginDate() {
		return _beginDate;
	}

	public void set_beginDate(String _beginDate) {
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

	public String get_keyword() {
		return _keyword;
	}

	public void set_keyword(String _keyword) {
		this._keyword = _keyword;
	}

	private List<UserBo> users;

	public List<UserBo> getUsers() {
		return users;
	}

	public void setUsers(List<UserBo> users) {
		this.users = users;
	}

	public int get_isActive() {
		return _isActive;
	}

	public void set_isActive(int _isActive) {
		this._isActive = _isActive;
	}

	public int get_isServed() {
		return _isServed;
	}

	public void set_isServed(int _isServed) {
		this._isServed = _isServed;
	}

}
