package wys.Business;

import java.io.Serializable;

import android.support.v4.os.ParcelableCompat;

public class BaseBusiness implements Serializable {

	

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


	
}
