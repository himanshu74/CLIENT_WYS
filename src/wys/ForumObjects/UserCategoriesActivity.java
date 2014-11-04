package wys.ForumObjects;



import com.wys.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import wys.Base.BaseActivity;

public class UserCategoriesActivity extends BaseActivity implements OnClickListener {

	
	private Button btn_categories;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_list);
	}
	
	

	@Override
	public void onClick(View v) {
		if(v.getId() == btn_categories.getId()){
			
		}
	}
	
	

}
