package wys.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import wys.Api.SessionManager;
import wys.Api.WysApi;
import wys.Business.BaseBusiness;
import wys.Business.CategoryBo;
import wys.Business.UserBo;
import wys.CustomInterfaces.OnGetCategoriesListener;
import wys.CustomInterfaces.OnGetRemainCatListener;
import wys.CustomInterfaces.OnGetUserCategoryListener;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class CategoryTask extends BaseAsyncTaskManager {

	private OnGetCategoriesListener _onCategoriesListener;
	private Context _ctx;
	private ProgressDialog progressDialog;
	private OnGetUserCategoryListener onGetUserCategoryListener;
	public OnGetUserCategoryListener getOnGetUserCategoryListener() {
		return onGetUserCategoryListener;
	}

	public void setOnGetUserCategoryListener(
			OnGetUserCategoryListener onGetUserCategoryListener) {
		this.onGetUserCategoryListener = onGetUserCategoryListener;
	}

	public OnGetRemainCatListener getOnGetRemainCatListener() {
		return onGetRemainCatListener;
	}

	public void setOnGetRemainCatListener(
			OnGetRemainCatListener onGetRemainCatListener) {
		this.onGetRemainCatListener = onGetRemainCatListener;
	}

	private OnGetRemainCatListener onGetRemainCatListener;

	public CategoryTask(OnGetCategoriesListener oncCategoriesListener,
			Context context) {
		this._onCategoriesListener = oncCategoriesListener;
		this._ctx = context;
	}

	public void ExecuteGetCategories() {
		new CategoryAsync().execute();
	}

	public void executeGetUsercats(){
		UserBo user = (UserBo)SessionManager.getUserBo();
		
		new UserCatAsync().execute(user.get_userId());
	}
	
	public void executeGetRemainCats()
	{
		UserBo user = (UserBo)SessionManager.getUserBo();
        new UserRemainCatAsync().execute(user.get_userId());
	}
	
	
	private class CategoryAsync extends
			AsyncTask<Void, Void, ArrayList<BaseBusiness>> {

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(_ctx);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage("Fetching Categories...");
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(false);
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected ArrayList<BaseBusiness> doInBackground(Void... params) {
			ArrayList<BaseBusiness> ArrayList = new WysApi().GetCategories();

			return ArrayList;
		}

		@Override
		protected void onPostExecute(ArrayList<BaseBusiness> result) {
			progressDialog.dismiss();
			if (result != null) {
				if (_onCategoriesListener != null) {
					_onCategoriesListener.OnCategoriesReceived(result);
				}
			}

			super.onPostExecute(result);
		}

	}

	private class UserCatAsync extends
			AsyncTask<Integer, Void, ArrayList<CategoryBo>> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected ArrayList<CategoryBo> doInBackground(Integer... params) {

			ArrayList<CategoryBo> list = new WysApi()
					.doGetUserCategories(params[0]);

			return list;
		}

		@Override
		protected void onPostExecute(ArrayList<CategoryBo> result) {
			if (result != null) {
               if(getOnGetUserCategoryListener() !=null)
               {
            	   getOnGetUserCategoryListener().onUserCategoryReceived(); 
               }
               else {
            	   getOnGetUserCategoryListener().onUserCategoryNotReceived(); 
               }
			}
			super.onPostExecute(result);
		}

	}

	private class UserRemainCatAsync extends
			AsyncTask<Integer, Void, ArrayList<CategoryBo>> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected ArrayList<CategoryBo> doInBackground(Integer... params) {
			ArrayList<CategoryBo> list = new WysApi()
					.doGetUserRemainCategories(params[0]);

			return list;

		}

		@Override
		protected void onPostExecute(ArrayList<CategoryBo> result) {
			
			if(result !=null)
			{
				if(getOnGetRemainCatListener() !=null)
				{
					getOnGetRemainCatListener().onRemainCatReceived();
				}
			}
			else {

				if(getOnGetRemainCatListener() !=null)
				{
					getOnGetRemainCatListener().onRemainCatNotReceived();
				}
			}
			
			super.onPostExecute(result);
		}

	}

}
