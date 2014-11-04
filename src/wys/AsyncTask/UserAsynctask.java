package wys.AsyncTask;

import java.util.ArrayList;

import wys.Api.WysApi;
import wys.Business.CategoryBo;
import wys.Business.UserBo;
import android.os.AsyncTask;

public class UserAsynctask extends BaseAsyncTaskManager {

	public void executePostUserCategories()
	{
		CategoryBo cat1 = new CategoryBo();
		cat1.set_categoryId(1);
		CategoryBo cat2 = new CategoryBo();
		cat2.set_categoryId(2);
		 
		ArrayList<CategoryBo> categories = new ArrayList<CategoryBo>();
		categories.add(cat1);
		
		categories.add(cat2);
		
		UserBo user = new UserBo();
		user.setUserCategories(categories);
		user.set_userId(1);
		new PostCategories().execute(user);
	}

	private class PostCategories extends AsyncTask<UserBo, Void, Integer> {

		@Override
		protected Integer doInBackground(UserBo... params) {
			int status = new WysApi().postUserCategories(params[0]);

			return status;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (result == SUCCESS) {

			}
			super.onPostExecute(result);
		}
	}

}
