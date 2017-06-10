package in.jewelchat.jewelchat.database;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by mayukhchakraborty on 04/03/16.
 */
public class GroupMemberContract implements BaseColumns {

	public static final String KEY_ROWID = BaseColumns._ID;
	public static final String GROUP_ID = "groupId";// foreign key of the group/contact
	public static final String JEWELCHAT_ID = "jewelChatId";
	public static final String IMAGE = "currentImageNumber";
	public static final String CONTACT_NUMBER = "contactNumber";
	public static final String CONTACT_NAME = "contactName";
	public static final String ISADMIN = "isAdmin";



	public static final String SQLITE_TABLE_NAME = "JewelChatGroup";

	private static final String DATABASE_CREATE =
			"CREATE TABLE if not exists " + SQLITE_TABLE_NAME + " ( " +
					KEY_ROWID + " integer PRIMARY KEY autoincrement," +
					GROUP_ID + "  INTEGER" + ", " +
					JEWELCHAT_ID + "  INTEGER" + ", " +
					IMAGE + "  TEXT" +  ", " +
					CONTACT_NUMBER + "  INTEGER" + ", " +
					ISADMIN + "  INTEGER" + ", " +
					CONTACT_NAME + "  TEXT" + " )";


	public static void onCreate(SQLiteDatabase db) {
		Log.i("Group", "OnCreate");
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

