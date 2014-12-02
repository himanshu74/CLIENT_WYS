package wys.Api;

import java.util.ArrayList;

import wys.Business.BaseBusiness;
import wys.Business.TopicBo;
import wys.Business.UserBo;

public class SessionManager {

	private static BaseBusiness userBo;
	private static ArrayList<TopicBo> userUpcomingTopics;
	private static ArrayList<TopicBo> userCurrentTopics;
	private static ArrayList<TopicBo> userPastTopics;

	public static BaseBusiness getUserBo() {
		return userBo;
	}

	public static void setUserBo(BaseBusiness baseBusiness) {
		SessionManager.userBo = baseBusiness;
	}
	
	public static void setUserUpComingTopics(ArrayList<TopicBo> Utopics)
	{
		SessionManager.userUpcomingTopics = Utopics;
	}
	public static ArrayList<TopicBo> getUserUpComingTopics()
	{
		return userUpcomingTopics;
	}
	
	public static void setUserCurrentTopics(ArrayList<TopicBo> Ctopics){
		SessionManager.userCurrentTopics= Ctopics;
	}
	
	public static ArrayList<TopicBo> getUserCurrentTopics(){
		return userCurrentTopics;
	}
	
	public static void setUserPastTopics(ArrayList<TopicBo> pastTopics){
		SessionManager.userPastTopics = pastTopics;
		
	}
	
	public static ArrayList<TopicBo> getuserPastTopics(){
		return userPastTopics;
	}
}
