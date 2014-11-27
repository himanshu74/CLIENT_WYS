package wys.Business;

import java.io.Serializable;

import android.support.v4.os.ParcelableCompat;

public class BaseBusiness implements Serializable {

	public static final int ADMIN_ROLE_ID = 0;
	public static final int USER_ROLE_ID = 1;
	public static final int ORG_ROLE_ID = 2;
	public static final int CURRENT_TOPICS=1;
	public static final int UPCOMING_TOPICS =0;
	public static final int PAST_TOPICS=2;

	private int status;
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	private int _serverId;

	public int get_serverId() {
		return _serverId;
	}

	public void set_serverId(int _serverId) {
		this._serverId = _serverId;
	}

}
