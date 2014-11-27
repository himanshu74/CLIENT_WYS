package wys.DatabaseHelpers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import wys.Modals.CategoryModal;
import wys.Modals.CommentModal;
import wys.Modals.TopicModal;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter implements Serializable {

	public static final String DATABASE_NAME = "wys2.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TAG = "DBAdapter";
	private final Context context;
	private DatabaseHelper dBHelper;
	private SQLiteDatabase db;

	public SQLiteDatabase getDb() {
		return db;
	}

	public DBAdapter(Context ctx) {
		this.context = ctx;
		dBHelper = new DatabaseHelper(context);

	}

	private static class DatabaseHelper  extends SQLiteOpenHelper implements Serializable {

		// private String
		// DatabasePath="/data/data/"+ctx.getPackageName()+"/databases/"+DATABASE_NAME;

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);

			Log.d(TAG, "DATABASE_VERSION:" + DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.v(TAG, "onCreate db");
			// db.execSQL(UserModal.CREATE_TABLE);
			// db.execSQL(CategoryModal.CREATE_TABLE);
			// db.execSQL(CategoryItemModal.CREATE_TABLE);
			db.execSQL(CategoryModal.CREATE_TABLE);
			db.execSQL(CategoryModal.CREATE_CAT_USER_TABLE);
			db.execSQL(TopicModal.CREATE_TABLE);
			db.execSQL(TopicModal.CREATE_TOPIC_USER_TABLE);
			db.execSQL(CommentModal.CREATE_TABLE);
			int i = 0;
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.v(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");

		}

	}

	private void copyDataBase() {
		ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
		String DB_PATH = cw.getDatabasePath(DATABASE_NAME).getAbsolutePath();

		Log.i("Database", "New database is being copied to device!");
		byte[] buffer = new byte[9024];
		OutputStream myOutput = null;
		int length;
		// Open your local db as the input stream
		InputStream myInput = null;
		try {
			if (!cw.getDatabasePath(DATABASE_NAME).getParentFile().exists()) {
				cw.getDatabasePath(DATABASE_NAME).getParentFile().mkdir();
			}

			myInput = context.getAssets().open(DATABASE_NAME);
			// transfer bytes from the inputfile to the
			// outputfile
			myOutput = new FileOutputStream(DB_PATH);
			while ((length = myInput.read(buffer)) > 0) {
				myOutput.write(buffer, 0, length);
			}
			myOutput.close();
			myOutput.flush();
			myInput.close();
			Log.i("Database", "New database has been copied to device!");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ---opens the database---

	public DBAdapter open(boolean readOnly) throws SQLException {
		// ContextWrapper wr=new ContextWrapper(this.context);
		// File dbFile = context.getDatabasePath(DATABASE_NAME);
		// if(!dbFile.exists())
		// {
		// copyDataBase();
		// }
		//

		try {
			if (readOnly)
				db = dBHelper.getReadableDatabase();
			else
				db = dBHelper.getWritableDatabase();
			db.setLockingEnabled(false);
		} catch (Exception ex) {
             Log.e("Dbhelper", ex.getMessage(), ex);
			/*
			 * boolean trytoOpen = true; while (trytoOpen) { try { if (readOnly)
			 * db = dBHelper.getReadableDatabase(); else db =
			 * dBHelper.getWritableDatabase(); db.setLockingEnabled(false);
			 * trytoOpen = false; } catch (Exception e) {
			 * 
			 * }
			 */
		}
		return this;
	}

	public boolean isOpen() {
		return db.isOpen();
	}

	// ---closes the database---
	public void close() {
		dBHelper.close();
	}

	// THIS IS A Connected Approach to DATABASE..
	public Cursor getCursor(String table, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		return getCursor(table, columns, selection, selectionArgs, groupBy,
				having, orderBy, null);

	}

	public Cursor getCursor(String table, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy, String limit) {
		return db.query(table, columns, selection, selectionArgs, groupBy,
				having, orderBy, limit);
	}
}
