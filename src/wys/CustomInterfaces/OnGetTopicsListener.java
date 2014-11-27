package wys.CustomInterfaces;

import java.util.ArrayList;

import wys.Business.BaseBusiness;

public interface OnGetTopicsListener {

	
	void onTopicsReceived();
	void onTopicsNotReceived();
	void onEmptyServerRecord();
}
