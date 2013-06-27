package team.androidreader.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ADOHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "mail.db";
	private static final int VERSION = 1;

	public ADOHelper(Context context) {
		super(context, DATABASE_NAME, null, VERSION);
	}

	public ADOHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS usermail (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "mail VARCHAR)");
		db.execSQL("CREATE TABLE IF NOT EXISTS recipientmail (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "mail VARCHAR)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
