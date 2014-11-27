package wys.Base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import wys.DatabaseHelpers.DBAdapter;
import wys.Helpers.PreferenceHelper;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;

public class BaseDbActivity extends Activity {

	public DBAdapter dbAdapter;
	protected Cursor cursor = null;
	public static final int MENU_ITEM_BACKUP = 14;
	public static final int MENU_ITEM_MENU = 17;
	public static final int DEFAULT_GROUP_ID = 0;
	public static final int DEFAULT_ORDER = 0;

	public static final File baseDirectory = new File(
			Environment.getExternalStorageDirectory(), "WYS");

	private PreferenceHelper _prefHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (dbAdapter == null) {
			dbAdapter = new DBAdapter(BaseDbActivity.this);
			dbAdapter.open(true);
		}

	}

	public PreferenceHelper getWYSPreferences() {
		if (_prefHelper == null) {
			_prefHelper = new PreferenceHelper(BaseDbActivity.this);
		}
		return _prefHelper;
	}

	public Typeface GetTypeFace(String Path) {

		Typeface tf = Typeface.createFromAsset(getAssets(), Path);
		return tf;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!super.onCreateOptionsMenu(menu))
			return false;

		menu.add(DEFAULT_GROUP_ID, MENU_ITEM_BACKUP, DEFAULT_ORDER, "Backup");

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (super.onOptionsItemSelected(item))
			return true;
		if (item.getItemId() == MENU_ITEM_BACKUP) {
			TakeBackup(BaseDbActivity.this);
			return true;
		}

		return false;
	}

	@SuppressWarnings("resource")
	public static void TakeBackup(Context ctx) {
		try {

			String state = Environment.getExternalStorageState();
			if (Environment.MEDIA_MOUNTED.equals(state)) {

				File sd = Environment.getExternalStorageDirectory();
				File data = Environment.getDataDirectory();

				if (sd.canWrite()) {
					String currentDBPath = data.getAbsolutePath() + "/"
							+ ctx.getPackageName() + "/" + "databases/"
							+ DBAdapter.DATABASE_NAME;
					baseDirectory.mkdir();
					File currentDB = new File(data, currentDBPath);
					File backupDB = new File(baseDirectory,
							DBAdapter.DATABASE_NAME);

					FileChannel src = new FileInputStream(currentDB)
							.getChannel();
					FileChannel dst = new FileOutputStream(backupDB)
							.getChannel();
					dst.transferFrom(src, 0, src.size());
					src.close();
					dst.close();

				}
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

}