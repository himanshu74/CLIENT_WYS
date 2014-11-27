package wys.Modals;

import java.text.SimpleDateFormat;
import java.util.Date;

import wys.Business.CategoryBo;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CategoryModal extends AppModal {

	// Table Name
	public static final String TABLE_NAME = "WYSCATEGORY";
	public static final String TABLE_NAME_CAT_USER = "cat_user";
	public static final String COL_SERVER_Id = "server_id";
	public static final String COL_CATEGORY_NAME = "category_name";
	public static final String COL_DATE_ADDED = "date_added";
	public static final String COL_DATE_MODIFIED = "date_modified";
	public static final String COL_DATE_DELETED = "date_deleted";
	public static final String COL_IS_DELETED = "is_deleted";
	public static final String COL_CAT_USER_ID = "cat_user_id";
	public static final String COL_CAT_ID = "cat_id";
	public static final String COL_USER_ID = "user_id";
	

	// Private Members
	private static final String CLASS_TAG = "CategoryModal";

	// Create Category Table
	public static final String CREATE_TABLE = "create table if not exists "
			+ TABLE_NAME + " ( " + COL_CAT_ID
			+ " integer primary key autoincrement," + COL_SERVER_Id
			+ " integer," + COL_CATEGORY_NAME + " varchar,"
			+ COL_DATE_ADDED + " varchar," + COL_DATE_DELETED
			+ " varchar," + COL_DATE_MODIFIED + " varchar,"
			+ COL_IS_DELETED + " integer default 0)";

	public static final String CREATE_CAT_USER_TABLE = "create table if not exists "
			+ TABLE_NAME_CAT_USER
			+ "( "
			+ COL_CAT_USER_ID
			+ " integer primary key autoincrement,"
			+ COL_CAT_ID
			+ " integer not null,"
			+ COL_USER_ID + " integer not null)";

	// Save Category
	public static boolean saveCategory(SQLiteDatabase db, CategoryBo categoryBo) {

		if (categoryBo.equals(null)) {
			return FAILURE;
		} else {

			try {
				ContentValues contentValues = new ContentValues();
				contentValues.put(COL_SERVER_Id, categoryBo.get_serverId());
				contentValues.put(COL_CATEGORY_NAME,
						categoryBo.get_categoryName());
				contentValues.put(COL_DATE_ADDED,
						getFormatedDate(categoryBo.get_dateAdded()));
				contentValues.put(COL_DATE_MODIFIED,
						getFormatedDate(categoryBo.get_dateModified()));
				contentValues.put(COL_DATE_DELETED,
						getFormatedDate(categoryBo.get_dateDeleted()));
				contentValues.put(COL_IS_DELETED, categoryBo.get_isDeleted());
				if (db.insertOrThrow(TABLE_NAME, "", contentValues) == SQL_INSERT_ERROR_CODE) {
					return FAILURE;
				} else {
					return SUCCESS;
				}
			} catch (Exception exception) {
				Log.d(CLASS_TAG,
						"Error Saving Category, Enteres in Exception with Messg"
								+ exception.getMessage());
				return FAILURE;
			}

		}

	}

	public static boolean saveCatUser(SQLiteDatabase db,
			int catId, int userId) {
		if (catId == -1 || userId == -1) {
			return FAILURE;
		} else {
			try {
				ContentValues contentValues = new ContentValues();
				contentValues.put(COL_CAT_ID, catId);
				contentValues.put(COL_USER_ID, userId);
				if (db.insertOrThrow(TABLE_NAME_CAT_USER, "", contentValues) == SQL_INSERT_ERROR_CODE) {
					return FAILURE;
				} else {
					return SUCCESS;
				}

			} catch (Exception exception) {
				Log.d(CLASS_TAG,
						"Error Saving USER_CATEGORIES, Enteres in Exception with Messg"
								+ exception.getMessage());
				return FAILURE;
			}
		}
	}

	public static boolean DeleteUserCats(SQLiteDatabase db, int catId, int userId ){
		if (catId == -1 || userId == -1) {
			return FAILURE;
		}
		else {
			try {
				
				if (db.delete(TABLE_NAME_CAT_USER, COL_CAT_ID+"=? and "+COL_USER_ID+" =?", new String[]{catId+"",userId+""}) <= 0) {
					return FAILURE;
				} else {
					return SUCCESS;
				}

			} catch (Exception exception) {
				Log.d(CLASS_TAG,
						"Error Deleting USER_CATEGORIES, Enteres in Exception with Messg"
								+ exception.getMessage());
				return FAILURE;
			}
		}
	}
	
}
