package wys.Modals;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AppModal implements IAppModal {

	public static final String TABLE_NAME = "";
	public static final int DeleteValue = 1;
	public static final boolean SUCCESS = true;
	public static final boolean FAILURE = false;

	protected static Cursor list(SQLiteDatabase database, String tables,
			String[] columns, String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy, String limit) {
		return database.query(tables, columns, selection, selectionArgs,
				groupBy, having, orderBy, limit);
	}

	public String getTableName() {
		return TABLE_NAME;
	}

	@SuppressLint("SimpleDateFormat")
	public static String getFormatedDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
}
