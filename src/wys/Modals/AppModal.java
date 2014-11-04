package wys.Modals;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AppModal implements IAppModal {

	public static final String TABLE_NAME="";
	public static final int DeleteValue=1; 
	
	protected static Cursor list(SQLiteDatabase database, String tables, String[] columns,
			String selection, String[] selectionArgs, String groupBy,
			String having, String orderBy, String limit) {
		return database.query(tables, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
	}
	
	public String getTableName() {
		return TABLE_NAME;
	}
}
