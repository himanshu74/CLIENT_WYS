package wys.Business;

import java.io.Serializable;
import java.util.Date;

public class CategoryBo extends BaseBusiness implements Serializable {

	private int _categoryId;
	private String _categoryName;
	private Date _dateAdded;
	private Date _dateModified;
	private Date _dateDeleted;
	private int _isDeleted;
	private int userId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int get_categoryId() {
		return _categoryId;
	}

	public void set_categoryId(int _categoryId) {
		this._categoryId = _categoryId;
	}

	public String get_categoryName() {
		return _categoryName;
	}

	public void set_categoryName(String _categoryName) {
		this._categoryName = _categoryName;
	}

	public Date get_dateAdded() {
		return _dateAdded;
	}

	public void set_dateAdded(Date _dateAdded) {
		this._dateAdded = _dateAdded;
	}

	public Date get_dateModified() {
		return _dateModified;
	}

	public void set_dateModified(Date _dateModified) {
		this._dateModified = _dateModified;
	}

	public Date get_dateDeleted() {
		return _dateDeleted;
	}

	public void set_dateDeleted(Date _dateDeleted) {
		this._dateDeleted = _dateDeleted;
	}

	public int get_isDeleted() {
		return _isDeleted;
	}

	public void set_isDeleted(int _isDeleted) {
		this._isDeleted = _isDeleted;
	}

}
