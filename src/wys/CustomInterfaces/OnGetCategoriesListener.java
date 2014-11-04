package wys.CustomInterfaces;

import java.util.ArrayList;
import java.util.List;

import wys.Business.BaseBusiness;

public interface OnGetCategoriesListener {

	void OnCategoriesReceived(ArrayList<BaseBusiness> list);
    void OnCategoriesNotReceived();	
	
	
}
