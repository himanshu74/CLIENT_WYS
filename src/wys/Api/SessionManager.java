package wys.Api;

import wys.Business.BaseBusiness;
import wys.Business.UserBo;

public class SessionManager {

	private static BaseBusiness userBo;

	public static BaseBusiness getUserBo() {
		return userBo;
	}

	public static void setUserBo(BaseBusiness baseBusiness) {
		SessionManager.userBo = baseBusiness;
	}
	
	
	
	
}
