package wys.Business;

import java.util.ArrayList;
import java.util.List;

public class UserBo extends BaseBusiness {


	// Properties
	private int _userId;
	private String _username;
	private String _password;
	private String _email;
	private int _domainId;
	private int _roleId;
	private String verificationCode;
	private int _isVerified;
	private String _token;
	private ArrayList<CategoryBo> userCategories;
	private ArrayList<TopicBo> userTopics;

	
	public ArrayList<TopicBo> getUserTopics() {
		return userTopics;
	}
	public void setUserTopics(ArrayList<TopicBo> userTopics) {
		this.userTopics = userTopics;
	}
	public String get_username() {
		return _username;
	}
	public void set_username(String _username) {
		this._username = _username;
	}
	public String get_password() {
		return _password;
	}
	public void set_password(String _password) {
		this._password = _password;
	}
	public String get_email() {
		return _email;
	}
	public void set_email(String _email) {
		this._email = _email;
	}
	public int get_domainId() {
		return _domainId;
	}
	public void set_domainId(int _domainId) {
		this._domainId = _domainId;
	}
	public int get_roleId() {
		return _roleId;
	}
	public void set_roleId(int _roleId) {
		this._roleId = _roleId;
	}
	
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	public int get_userId() {
		return _userId;
	}
	public void set_userId(int _userId) {
		this._userId = _userId;
	}
	
	public int get_isVerified() {
		return _isVerified;
	}
	public void set_isVerified(int _isVerified) {
		this._isVerified = _isVerified;
	}
	

	public String get_token() {
		return _token;
	}
	public void set_token(String _token) {
		this._token = _token;
	}
	

	public ArrayList<CategoryBo> getUserCategories() {
		return userCategories;
	}
	public void setUserCategories(ArrayList<CategoryBo> userCategories) {
		this.userCategories = userCategories;
	}
	
}
