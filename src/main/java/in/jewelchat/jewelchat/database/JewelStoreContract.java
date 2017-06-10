package in.jewelchat.jewelchat.database;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by mayukhchakraborty on 09/06/17.
 */

public class JewelStoreContract implements BaseColumns {

	public static final String KEY_ROWID = BaseColumns._ID;
	public static final String JEWELTYPE_ID = "jewelTypeId";
	public static final String COUNT = "count";
	public static final String TOTAL_COUNT = "total_count";




	public static final String SQLITE_TABLE_NAME = "JewelStore";

	private static final String DATABASE_CREATE =
			"CREATE TABLE if not exists " + SQLITE_TABLE_NAME + " ( " +
					KEY_ROWID + " integer PRIMARY KEY autoincrement," +
					JEWELTYPE_ID + "  INTEGER" + ", " +
					COUNT + "  INTEGER" + ", " +
					TOTAL_COUNT + "  INTEGER )";




	public static void onCreate(SQLiteDatabase db) {
		Log.i("JewelStore", "OnCreate");
		db.execSQL(DATABASE_CREATE);
		/*
		String insertquery1 =  "INSERT INTO Group (groupId,contactnumber,contactname,groupMessageChannel)"+
				" values('4','+919005835705','Santanu','shantanuGroupChannel')";
		db.execSQL(insertquery1);

		String insertquery2 =  "INSERT INTO Group (groupId,contactnumber,contactname,groupMessageChannel)"+
				" values('4','+919005835706','Veeru','veeruGroupChannel')";
		db.execSQL(insertquery2);
		*/
	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion,
	                             int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE_NAME);
		onCreate(db);
	}

}

