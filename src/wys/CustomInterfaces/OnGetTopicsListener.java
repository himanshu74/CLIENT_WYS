package wys.CustomInterfaces;

import java.util.ArrayList;

import wys.Business.BaseBusiness;
import wys.Business.TopicBo;

public interface OnGetTopicsListener {

	
	void onTopicsReceived(ArrayList<TopicBo> list);
	void onTopicsNotReceived();
	void onEmptyServerRecord();
}
