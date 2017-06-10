package in.jewelchat.jewelchat.database;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by mayukhchakraborty on 04/03/16.
 */
public class GroupMessageContract  implements BaseColumns {

	public static final String KEY_ROWID = BaseColumns._ID;
	public static final String GROUP_ID = "groupId";
	public static final String CHAT_MESSAGE_ID = "chatMessageId";
	public static final String JEWELCHAT_ID = "jewelChatId";
	public static final String CONTACT_NUMBER = "contactNumber";
	public static final String CONTACT_NAME = "contactName";
	public static final String IS_READ = "isRead";
	public static final String TIME_READ = "timeRead";
	public static final String IS_DELIVERED = "isDelivered";
	public static final String TIME_DELIVERED = "timeDelivered";
	public static final String IS_SUBMITTED = "isSubmitted";
	public static final String TIME_SUBMITTED = "timeSubmitted";

	public static final String SQLITE_TABLE_NAME = "GroupMessage";

	private static final String DATABASE_CREATE = "CREATE TABLE if not exists "
			+ SQLITE_TABLE_NAME + " (" +
			KEY_ROWID + " integer PRIMARY KEY autoincrement," +
			GROUP_ID + "  INTEGER" + ", " +
			CHAT_MESSAGE_ID + "  INTEGER" + ", " +
			JEWELCHAT_ID + "  INTEGER" + ", " +
			CONTACT_NUMBER + "  INTEGER" + ", " +
			CONTACT_NAME + "  TEXT" + ", " +
			IS_READ + "  INTEGER" + ", " +
			TIME_READ + "  INTEGER" + ", " +
			IS_DELIVERED + "  INTEGER" + ", " +
			TIME_DELIVERED + "  INTEGER" + ", " +
			IS_SUBMITTED + "  INTEGER" + ", " +
			TIME_SUBMITTED + "  INTEGER )";


	public static void onCreate(SQLiteDatabase db) {
		Log.i("GroupMessage", "OnCreate");
		db.execSQL(DATABASE_CREATE);
			/*
			String insertquery1 =  "INSERT INTO GroupMessage (groupId,chatMessageId,contactId,contactnumber,contactname,isRead,isDelivered,isSubmitted)"+
					" values('4','2','1','+919005835705','Santanu','0','0','0')";
			db.execSQL(insertquery1);

			String insertquery2 =  "INSERT INTO GroupMessage (groupId,chatMessageId,contactId,contactnumber,contactname,isRead,isDelivered,isSubmitted)"+
					" values('4','2','2','+919005835706','Veeru','0','0','0')";
			db.execSQL(insertquery2);
			*/
	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion,
	                             int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE_NAME);
		onCreate(db);
	}

}
