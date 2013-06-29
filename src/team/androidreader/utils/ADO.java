package team.androidreader.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ADO {

	private ADOHelper adoHelper;
	private SQLiteDatabase db;

	public ADO(Context context) {
		adoHelper = new ADOHelper(context);
	}

	public boolean addUserMail(String mail) {
		db = adoHelper.getWritableDatabase();
		db.beginTransaction();
		try {
			db.execSQL("INSERT INTO usermail VALUES(NULL,?)",
					new Object[] { mail });
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			return false;
		} finally {
			db.endTransaction();
			db.close();
			db = null;
		}
		return true;
	}

	public boolean deleteUserMail(String mail) {
		db = adoHelper.getWritableDatabase();
		db.beginTransaction();
		try {
			db.execSQL("DELETE FROM usermail WHERE mail=?",
					new Object[] { mail });
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			return false;
		} finally {
			db.endTransaction();
			db.close();
			db = null;
		}
		return true;
	}

	public List<String> queryUserMail() {
		db = adoHelper.getWritableDatabase();
		List<String> mails = new ArrayList<String>();
		Cursor cursor = db.rawQuery("SELECT * FROM usermail WHERE mail != ?",
				new String[] { "" });
		while (cursor.moveToNext()) {
			String mail = cursor.getString(cursor.getColumnIndex("mail"));
			mails.add(mail);
		}
		cursor.close();
		db.close();
		cursor = null;
		db = null;
		return mails;
	}

	public boolean addRecipientMail(String mail) {
		db = adoHelper.getWritableDatabase();
		db.beginTransaction();
		try {
			db.execSQL("INSERT INTO recipientmail VALUES(NULL,?)",
					new Object[] { mail });
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			return false;
		} finally {
			db.endTransaction();
			db.close();
			db = null;
		}
		return true;
	}

	public boolean deleteRecipientMail(String mail) {
		db = adoHelper.getWritableDatabase();
		db.beginTransaction();
		try {
			db.execSQL("DELETE FROM recipientmail WHERE mail=?",
					new Object[] { mail });
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			return false;
		} finally {
			db.endTransaction();
			db.close();
			db = null;
		}
		return true;
	}

	public List<String> queryRecipientMail() {
		db = adoHelper.getWritableDatabase();
		List<String> mails = new ArrayList<String>();
		Cursor cursor = db.rawQuery(
				"SELECT * FROM recipientmail WHERE mail != ?",
				new String[] { "" });
		while (cursor.moveToNext()) {
			String mail = cursor.getString(cursor.getColumnIndex("mail"));
			mails.add(mail);
		}
		cursor.close();
		db.close();
		cursor = null;
		db = null;
		return mails;
	}

}
