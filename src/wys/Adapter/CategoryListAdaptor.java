package wys.Adapter;

import java.util.ArrayList;

import com.wys.R;

import wys.Business.CategoryBo;
import wys.Business.TopicBo;
import wys.Fragments.MyCategoriesFragment;
import wys.Fragments.OtherCategoriesFragment;
import wys.Helpers.FontHelper;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryListAdaptor extends ArrayAdapter<CategoryBo> {

	
	private ArrayList<CategoryBo> _cats;
	public static boolean IsEditEnabled;
	public static boolean IsDeletePressed;
	

	private Context _ctx;
	
	
	public CategoryListAdaptor(Context context,ArrayList<CategoryBo> cats) {
		super(context, R.layout.category_list);
		
		this._ctx =context;
		this._cats = cats;
		if(IsEditEnabled){
			OtherCategoriesFragment.CheckBoxState = new boolean[_cats.size()];

		}
		if(IsDeletePressed){
			MyCategoriesFragment.CheckBoxState = new boolean[_cats.size()];	
		}
		
	}
	
	
	@Override
	public boolean isEnabled(int position) {
		if (IsEditEnabled || IsDeletePressed) {
			return false;
		}
		return super.isEnabled(position);
	}
	@Override
	public int getCount() {

		return _cats.size();
	}

	@Override
	public CategoryBo getItem(int position) {
		// TODO Auto-generated method stub
		return _cats.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(final int position, View convertView,
			ViewGroup parent) {
		ViewHolderCategory viewHolder;
		LayoutInflater inflator = LayoutInflater.from(_ctx);

		if (convertView == null) {
			viewHolder = new ViewHolderCategory();
			convertView = inflator.inflate(R.layout.category_list, null);

			viewHolder.tv_categoryName = (TextView) convertView
					.findViewById(R.id.tv_cat_name);

			viewHolder.iv_forwardArrow = (ImageView) convertView
					.findViewById(R.id.iv_arrow);
			viewHolder.cb_DeleteCheckbx = (CheckBox) convertView
					.findViewById(R.id.chckBx);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolderCategory) convertView.getTag();
		}

		viewHolder.tv_categoryName.setText(_cats.get(position)
				.get_categoryName());

		/*viewHolder.tv_categoryName
				.setTypeface(GetTypeFace(FontHelper.CATEGORY_NAME_FONTSTYLE));
*/
		viewHolder.iv_forwardArrow.setBackgroundResource(R.drawable.arrow);

		if (IsEditEnabled || IsDeletePressed) {
			viewHolder.iv_forwardArrow.setVisibility(View.GONE);
			viewHolder.cb_DeleteCheckbx.setVisibility(View.VISIBLE);
			if(IsEditEnabled){
				viewHolder.cb_DeleteCheckbx.setChecked(OtherCategoriesFragment.CheckBoxState[position]);
			}
			else if(IsDeletePressed){
				viewHolder.cb_DeleteCheckbx.setChecked(MyCategoriesFragment.CheckBoxState[position]);

			}
			viewHolder.cb_DeleteCheckbx
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							if(IsEditEnabled){
								OtherCategoriesFragment.IsCheckBoxChecked = true;
							}else if(IsDeletePressed){
								MyCategoriesFragment.IsCheckBoxChecked = true;
							}
							
							if (((CheckBox) v).isChecked()) {
								if(IsEditEnabled){
									OtherCategoriesFragment.CheckBoxState[position] = true;

								}
								else if(IsDeletePressed){
									MyCategoriesFragment.CheckBoxState[position] = true;

								}
							} else {
								if(IsEditEnabled){
									OtherCategoriesFragment.CheckBoxState[position] = false;

								}
								else if(IsDeletePressed){
									MyCategoriesFragment.CheckBoxState[position] = false;

								}
							}
						}
					});
		}

		return convertView;
		// return super.getView(position, convertView, parent);
	}

}

class ViewHolderCategory {

	ImageView iv_CategoryIcon;
	TextView tv_categoryName;
	ImageView iv_forwardArrow;
	CheckBox cb_DeleteCheckbx;
}

