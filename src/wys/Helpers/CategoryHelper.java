package wys.Helpers;

import java.util.ArrayList;

import android.database.Cursor;
import wys.Business.CategoryBo;
import wys.DatabaseHelpers.DBAdapter;
import wys.Modals.CategoryModal;

public class CategoryHelper {

	public static ArrayList<CategoryBo> getCategories(DBAdapter dbAdapter) {
		ArrayList<CategoryBo> cats = new ArrayList<CategoryBo>();
		Cursor cursor = dbAdapter.getCursor(CategoryModal.TABLE_NAME,
				new String[] { CategoryModal.COL_CAT_ID,
						CategoryModal.COL_CATEGORY_NAME,
						CategoryModal.COL_SERVER_Id },
				CategoryModal.COL_IS_DELETED + "=0", null, null, null, null);
		if (cursor.moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				CategoryBo category = new CategoryBo();
				category.set_categoryId(cursor.getInt(cursor
						.getColumnIndex(CategoryModal.COL_CAT_ID)));
				category.set_serverId(cursor.getInt(cursor
						.getColumnIndex(CategoryModal.COL_SERVER_Id)));
				category.set_categoryName(cursor.getString(cursor
						.getColumnIndex(CategoryModal.COL_CATEGORY_NAME)));
		
				
				cats.add(category);
				cursor.moveToNext();
			}
		}
		return cats;
	}

	public static ArrayList<CategoryBo> getUserCategories(DBAdapter dbAdapter, int userId) {
		ArrayList<CategoryBo> cats = new ArrayList<CategoryBo>();
		Cursor cursor = dbAdapter.getCursor(CategoryModal.TABLE_NAME+" c join "
				+ CategoryModal.TABLE_NAME_CAT_USER +" cu on c." + CategoryModal.SERVER_ID +
				" = cu." + CategoryModal.COL_CAT_ID,
				new String[] { "c."+CategoryModal.COL_CAT_ID,
				"c."+CategoryModal.COL_CATEGORY_NAME,
				"c."+CategoryModal.COL_SERVER_Id },
				"c."+CategoryModal.COL_IS_DELETED + "=0 and "
						+ "cu."+CategoryModal.COL_USER_ID +"=? ",new String[]{userId+""}, null, null, null);
		if (cursor.moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				CategoryBo category = new CategoryBo();
				category.set_categoryId(cursor.getInt(cursor
						.getColumnIndex(CategoryModal.COL_CAT_ID)));
				category.set_serverId(cursor.getInt(cursor
						.getColumnIndex(CategoryModal.COL_SERVER_Id)));
				category.set_categoryName(cursor.getString(cursor
						.getColumnIndex(CategoryModal.COL_CATEGORY_NAME)));
				cats.add(category);
				cursor.moveToNext();
			}
		}
		return cats;
	}
	
	public static ArrayList<CategoryBo> getRemainingCats(DBAdapter dbAdapter, int userId){
		ArrayList<CategoryBo> cats = new ArrayList<CategoryBo>();
		
		String query = "select c."+CategoryModal.COL_CAT_ID+",c."+CategoryModal.COL_CATEGORY_NAME+",c."
		+CategoryModal.COL_SERVER_Id+" from "+CategoryModal.TABLE_NAME+" c where c."+CategoryModal.COL_CAT_ID+
		" not in (select j."+CategoryModal.COL_CAT_ID+" from "+CategoryModal.TABLE_NAME_CAT_USER+" j where j."
		+CategoryModal.COL_USER_ID+"= "+userId+")";
		
		/*Cursor cursor = dbAdapter.getCursor(CategoryModal.TABLE_NAME+" c join "
		+ CategoryModal.TABLE_NAME_CAT_USER +" j on c."+ CategoryModal.SERVER_ID +
		" = j." + CategoryModal.COL_CAT_ID,	 
		new String[] { "c."+CategoryModal.COL_CAT_ID,
				"c."+CategoryModal.COL_CATEGORY_NAME,
				"c."+CategoryModal.COL_SERVER_Id } ,
				"c."+CategoryModal.COL_IS_DELETED + "=0 and "
						+ "j."+CategoryModal.COL_USER_ID + "!=? ", new String[]{userId +""}, 
						null, null, null);*/
		Cursor cursor = dbAdapter.getDb().rawQuery(query, null);
		if (cursor.moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				CategoryBo category = new CategoryBo();
				category.set_categoryId(cursor.getInt(cursor
						.getColumnIndex(CategoryModal.COL_CAT_ID)));
				category.set_serverId(cursor.getInt(cursor
						.getColumnIndex(CategoryModal.COL_SERVER_Id)));
				category.set_categoryName(cursor.getString(cursor
						.getColumnIndex(CategoryModal.COL_CATEGORY_NAME)));
				cats.add(category);
				cursor.moveToNext();
			}
		}
		return cats;
		
	}

}
