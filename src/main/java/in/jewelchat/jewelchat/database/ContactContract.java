package in.jewelchat.jewelchat.database;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by mayukhchakraborty on 04/03/16.
 */

public class ContactContract implements BaseColumns {

	public static final String KEY_ROWID = BaseColumns._ID;
	public static final String JEWELCHAT_ID = "jewelChatId"; // backend Id
	public static final String CONTACT_NUMBER = "contactNumber";
	public static final String CONTACT_NAME = "contactName";
	public static final String PHONEBOOK_CONTACT_NAME = "PhoneBookContactName";
	public static final String IS_GROUP = "isGroup";
	public static final String IMAGE_PHONEBOOK = "imagePhoneBook";
	public static final String IMAGE_BLOB = "imageBlob";
	public static final String STATUS_MSG = "statusMsg";
	public static final String IS_REGIS = "isRegis";
	public static final String IS_GROUP_ADMIN = "isGroupAdmin";
	public static final String IS_INVITED = "isInvited";
	public static final String IS_BLOCKED = "isBlocked";
	public static final String IS_PHONEBOOK_CONTACT = "isPhonebookContact";



	public static final String SQLITE_TABLE_NAME = "Contact";

	private static final String DATABASE_CREATE =
			"CREATE TABLE if not exists " + SQLITE_TABLE_NAME + " (" +
					KEY_ROWID + " integer PRIMARY KEY autoincrement," +
					JEWELCHAT_ID + "  INTEGER" +  ", " +
					CONTACT_NUMBER + "  INTEGER" +  ", " +
					CONTACT_NAME + "  TEXT" +  ", " +
					PHONEBOOK_CONTACT_NAME + "  TEXT" +  ", " +
					IS_GROUP + "  INTEGER" +  ", " +
					STATUS_MSG + "  TEXT" +  ", " +
					IS_REGIS + "  INTEGER" +  ", " +
					IS_GROUP_ADMIN + "  INTEGER" +  ", " +
					IS_INVITED + "  INTEGER" +  ", " +
					IS_BLOCKED + "  INTEGER" +  ", " +
					IS_PHONEBOOK_CONTACT + "  INTEGER" +  ", " +
					IMAGE_PHONEBOOK + "  TEXT" +  ", " +
					IMAGE_BLOB + "  BLOB)";

	public static void onCreate(SQLiteDatabase db) {
		Log.i("Contact", "OnCreate");
		db.execSQL(DATABASE_CREATE);
		/*
		String insertquery1 =  "INSERT INTO Contact (contactId,contactnumber,contactname,messageChannel,statusChannel,isGroup,isActive)"+
					" values('001','xxxxxxxx','CityTalk','citytalk_msg','citytalk_status','0','1' )";
		db.execSQL(insertquery1);

		String insertquery2 =  "INSERT INTO Contact (contactId,contactnumber,contactname,messageChannel,statusChannel,isGroup,isActive)"+
				" values('002','+919005835705','Santanu','mayukh_msg','mayukh_status','0','1')";
		db.execSQL(insertquery2);

		String insertquery3 =  "INSERT INTO Contact (contactId,contactnumber,contactname,messageChannel,statusChannel,isGroup, isActive)"+
				" values('003','+919005835706','Veeru','veeru_msg','veeru_status','0','1')";
		db.execSQL(insertquery3);

		String insertquery4 =  "INSERT INTO Contact (contactId,contactname,isGroup,isActive,isGroupAdmin)" +
				" values('004','Arnium','1','1','1')";
		db.execSQL(insertquery4);
		*/
	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion,
	                             int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE_NAME);
		onCreate(db);
	}



}
