package wys.ForumObjects;

import java.util.ArrayList;
import java.util.List;

import com.wys.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import wys.Api.SessionManager;
import wys.AsyncTask.Topictask;
import wys.Base.BaseActivity;
import wys.Business.BaseBusiness;
import wys.Business.CategoryBo;
import wys.CustomInterfaces.OnGetTopicsListener;
import wys.FrontLayer.MainActivity;
import wys.Helpers.FontHelper;

public class CategoryListActivity extends BaseActivity implements
		OnClickListener, OnGetTopicsListener, OnItemClickListener {

	private ListView categoryList;
	private ImageView iv_back, iv_DeleteCat, iv_logout, iv_confirm, iv_cancel;
	private TextView tv_Category_title;
	private ArrayList<CategoryBo> listCats;
	private Context _ctx = CategoryListActivity.this;
	private String catName;
	private int catId;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent i = getIntent();
		listCats = (ArrayList<CategoryBo>) i.getSerializableExtra("list");
		setContentView(R.layout.category);
		initControls();
		setListview();

	}

	private void initControls() {
		categoryList = (ListView) findViewById(R.id.lv_categories);
		categoryList.setOnItemClickListener(this);
		tv_Category_title = (TextView) findViewById(R.id.tv_Category_title);
		tv_Category_title
				.setTypeface(GetTypeFace(FontHelper.CATEGORY_TITLE_FONTSTYLE));
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(this);
		iv_logout = (ImageView) findViewById(R.id.iv_logout);
		iv_logout.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == iv_back.getId()) {
			CategoryListActivity.this.finish();
		} else if (v.getId() == iv_logout.getId()) {
			SessionManager.setUserBo(null);
			Intent i = new Intent(_ctx, MainActivity.class);
			startActivity(i);
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		CategoryBo category = (CategoryBo) parent.getItemAtPosition(position);
		catName = category.get_categoryName();
		catId = category.get_categoryId();

		getTopics(catId);

	}

	private void getTopics(int catId) {
		new Topictask(_ctx, CategoryListActivity.this).executeGetTopics(catId);
	}

	private void setListview() {
		CustomCategoryList customCategoryListAdapter = new CustomCategoryList(
				CategoryListActivity.this, listCats);
		categoryList.setAdapter(customCategoryListAdapter);
	}

	private class CustomCategoryList extends ArrayAdapter<CategoryBo> {

		private ArrayList<CategoryBo> categoryList;
		private Context context;

		public CustomCategoryList(Context context,
				ArrayList<CategoryBo> categories) {
			super(context, R.layout.category_list);
			this.context = context;
			this.categoryList = categories;

		}

		@Override
		public int getCount() {
			return listCats.size();
		}

		@Override
		public CategoryBo getItem(int position) {
			return listCats.get(position);
		}

		@Override
		public long getItemId(int position) {
			return super.getItemId(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			LayoutInflater inflator = LayoutInflater.from(context);
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = inflator.inflate(R.layout.category_list, null);
				viewHolder.tv_categoryName = (TextView) convertView
						.findViewById(R.id.tv_cat_name);
				viewHolder.iv_forwardArrow = (ImageView) convertView
						.findViewById(R.id.iv_arrow);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();

			}
			viewHolder.tv_categoryName.setText(categoryList.get(position)
					.get_categoryName());
			viewHolder.tv_categoryName
					.setTypeface(GetTypeFace(FontHelper.CATEGORY_NAME_FONTSTYLE));
			viewHolder.iv_forwardArrow.setBackgroundResource(R.drawable.arrow);
			return convertView;
			// return super.getView(position, convertView, parent);
		}

	}

	static class ViewHolder {

		ImageView iv_CategoryIcon;
		TextView tv_categoryName;
		ImageView iv_forwardArrow;
	}

	@Override
	public void onTopicsReceived(ArrayList<BaseBusiness> list) {
		Intent i = new Intent(_ctx, CategoryActivity.class);
		i.putExtra("catName", catName);
		i.putExtra("catId", catId);
		i.putExtra("list", list);
		startActivity(i);

	}

	@Override
	public void onTopicsNotReceived() {
		// TODO Auto-generated method stub

	}

}
