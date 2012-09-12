package client.ranking;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Rank extends SQLiteOpenHelper {

	static final String dbName = "Rank";

	// Users table
	static final String userTable = "Users";
	static final String colID = "UserID";
	static final String colName = "Name";

	public Rank(Context context) {
		// TODO Auto-generated constructor stub
		super(context, dbName, null, 1);
	}

	public Rank(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + userTable);
		db.execSQL("CREATE TABLE " + userTable + " (" + colID
				+ " INTEGER PRIMARY KEY , " + colName + " TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + userTable);

		onCreate(db);
	}

	public boolean insert(String name) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(colID, 1);
		cv.put(colName, name);
		db.insert(userTable, colID, cv);

		db.close();
		
		return true;
	}

}
