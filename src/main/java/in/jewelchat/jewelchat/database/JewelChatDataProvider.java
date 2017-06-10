package in.jewelchat.jewelchat.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import in.jewelchat.jewelchat.JewelChatApp;
import in.jewelchat.jewelchat.JewelChatPrefs;

/**
 * Created by mayukhchakraborty on 04/03/16.
 */
public class JewelChatDataProvider extends ContentProvider {

	// The URI scheme used for content URIs
	public static final String SCHEME = "content";

	// The provider's authority
	public static final String AUTHORITY = "in.jewelchat.jewelchat";

	/**
	 * The DataProvider content URI
	 */
	public static final Uri CONTENT_URI = Uri.parse(SCHEME + "://" + AUTHORITY);

	private static final int CHAT_MESSAGE = 1;
	private static final int CONTACT = 2;
	private static final int CHAT_LIST = 3;
	private static final int GROUP = 4;
	private static final int GROUP_MESSAGE = 5;
	private static final int CHAT_MESSAGE_MAX = 6;
	private static final int NON_SUBMITTED = 7;
	private static final int NON_READ = 8;
	private static final int JEWL_STORE = 9;


	private JewelChatDatabaseHelper dbHelper;
	private static final UriMatcher uriMatcher;

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, ChatMessageContract.SQLITE_TABLE_NAME , CHAT_MESSAGE );
		uriMatcher.addURI(AUTHORITY, "nonsubmittedmsgs" , NON_SUBMITTED );
		uriMatcher.addURI(AUTHORITY, "nonreadmsgs" , NON_READ );
		uriMatcher.addURI(AUTHORITY, ContactContract.SQLITE_TABLE_NAME , CONTACT );
		uriMatcher.addURI(AUTHORITY, "chatlist" , CHAT_LIST );  //chatlist is join of contact and msg table
		uriMatcher.addURI(AUTHORITY, GroupMemberContract.SQLITE_TABLE_NAME , GROUP );
		uriMatcher.addURI(AUTHORITY, GroupMessageContract.SQLITE_TABLE_NAME , GROUP_MESSAGE );
		uriMatcher.addURI(AUTHORITY, ChatMessageContract.SQLITE_TABLE_NAME+"_MAX_TIME" , CHAT_MESSAGE_MAX );
		uriMatcher.addURI(AUTHORITY, JewelStoreContract.SQLITE_TABLE_NAME , JEWL_STORE );


	}



	@Override
	public boolean onCreate() {
		dbHelper = JewelChatDatabaseHelper.getHelper(getContext());
		return true;
	}

	@Nullable
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		switch (uriMatcher.match(uri)) {

			case CHAT_MESSAGE:{
				Cursor returnCursor = db.query(
						ChatMessageContract.SQLITE_TABLE_NAME,
						projection,
						selection, selectionArgs, null, null, sortOrder);
				Log.i(">>", "Here");
				// Sets the ContentResolver to watch this content URI for data changes
				//MergeCursor mcursor = new MergeCursor(new Cursor[]{returnCursor});
				returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
				//mcursor.setNotificationUri(getContext().getContentResolver(), uri);
				//return mcursor;
				return returnCursor;

			}

			case CONTACT:{
				Cursor returnCursor = db.query(
						ContactContract.SQLITE_TABLE_NAME,
						projection,
						selection, selectionArgs, null, null, sortOrder);

				// Sets the ContentResolver to watch this content URI for data changes
				returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
				return returnCursor;

			}
			case CHAT_LIST:{
				String query = "SELECT "
						+ ContactContract.SQLITE_TABLE_NAME+"."+ContactContract.JEWELCHAT_ID + ", "
						+ ContactContract.SQLITE_TABLE_NAME+"."+ContactContract.CONTACT_NAME + ", "
						+ ContactContract.SQLITE_TABLE_NAME+"."+ContactContract.CONTACT_NUMBER + ", "
						+ ContactContract.SQLITE_TABLE_NAME+"."+ContactContract.IMAGE_BLOB + ", "
						+ ContactContract.SQLITE_TABLE_NAME+"."+ContactContract.IS_REGIS + ", "
						+ ContactContract.SQLITE_TABLE_NAME+"."+ContactContract.IS_GROUP + ", "
						+ ContactContract.SQLITE_TABLE_NAME+"."+ContactContract.IS_GROUP_ADMIN + ", "
						+ ContactContract.SQLITE_TABLE_NAME+"."+ContactContract.IS_INVITED+ ", "
						+ ContactContract.SQLITE_TABLE_NAME+"."+ContactContract.STATUS_MSG+ ", "
						+ ChatMessageContract.SQLITE_TABLE_NAME+"."+ChatMessageContract.MSG_TYPE + ", "
						+ ChatMessageContract.SQLITE_TABLE_NAME+"."+ChatMessageContract.MSG_TEXT + ", "
						+ ChatMessageContract.SQLITE_TABLE_NAME+"."+ChatMessageContract.CREATED_TIME + ", "
						+ ChatMessageContract.SQLITE_TABLE_NAME+"."+ChatMessageContract.IS_READ + ", "
						+ ContactContract.SQLITE_TABLE_NAME+"."+ContactContract.KEY_ROWID + ", "
						+ ChatMessageContract.SQLITE_TABLE_NAME+"."+ChatMessageContract.CREATOR_ID + ", "
						+ ChatMessageContract.SQLITE_TABLE_NAME+"."+ChatMessageContract.CHAT_ROOM + ", "
						+ "MAX("+ChatMessageContract.SQLITE_TABLE_NAME+"."+ChatMessageContract.KEY_ROWID+")"
						+ " FROM "
						+ ContactContract.SQLITE_TABLE_NAME + ", "+ ChatMessageContract.SQLITE_TABLE_NAME
						+ " WHERE "+ ContactContract.SQLITE_TABLE_NAME+"."+ContactContract.JEWELCHAT_ID+" = "+ ChatMessageContract.SQLITE_TABLE_NAME+"."+ChatMessageContract.CHAT_ROOM
						+ " GROUP BY "+ ChatMessageContract.SQLITE_TABLE_NAME+"."+ChatMessageContract.CHAT_ROOM;
				Log.i(">>", query);
				Cursor returnCursor = db.rawQuery(query, null);
				//Log.i(">>", returnCursor.);
				// Sets the ContentResolver to watch this content URI for data changes
				returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
				return returnCursor;

			}
			case NON_SUBMITTED:{
				String query = "SELECT " + ContactContract.SQLITE_TABLE_NAME+"."+ContactContract.CONTACT_NAME + ", "
						+ ContactContract.SQLITE_TABLE_NAME+"."+ContactContract.JEWELCHAT_ID + ", "
						+ ChatMessageContract.SQLITE_TABLE_NAME+"."+ChatMessageContract.MSG_TYPE + ", "
						+ ChatMessageContract.SQLITE_TABLE_NAME+"."+ChatMessageContract.MSG_TEXT + ", "
						+ ChatMessageContract.SQLITE_TABLE_NAME+"."+ChatMessageContract.IS_IMAGE_UPLOADED + ", "
						+ ChatMessageContract.SQLITE_TABLE_NAME+"."+ChatMessageContract.IMAGE_PATH_CLOUD + ", "
						+ ChatMessageContract.SQLITE_TABLE_NAME+"."+ChatMessageContract.IMAGE_PATH_LOCAL + ", "
						+ ChatMessageContract.SQLITE_TABLE_NAME+"."+ChatMessageContract.IS_VIDEO_UPLOADED + ", "
						+ ChatMessageContract.SQLITE_TABLE_NAME+"."+ChatMessageContract.VIDEO_PATH_CLOUD + ", "
						+ ChatMessageContract.SQLITE_TABLE_NAME+"."+ChatMessageContract.VIDEO_PATH_LOCAL + ", "
						+ ChatMessageContract.SQLITE_TABLE_NAME+"."+ChatMessageContract.CREATED_TIME + ", "
						+ ChatMessageContract.SQLITE_TABLE_NAME+"."+ChatMessageContract.IS_SUBMITTED + ", "
						+ ChatMessageContract.SQLITE_TABLE_NAME+"."+ChatMessageContract.CREATOR_ID + ", "
						+ ChatMessageContract.SQLITE_TABLE_NAME+"."+ChatMessageContract.CHAT_ROOM + ", "
						+ ChatMessageContract.SQLITE_TABLE_NAME+"."+ChatMessageContract.KEY_ROWID
						+ " FROM "
						+  ContactContract.SQLITE_TABLE_NAME+" , "+ChatMessageContract.SQLITE_TABLE_NAME
						+ " WHERE "+ ChatMessageContract.SQLITE_TABLE_NAME+"."+ChatMessageContract.CREATOR_ID + " = '"+ JewelChatApp.getSharedPref().getString(JewelChatPrefs.MY_ID, "")+"'"
						+ " AND " + ContactContract.SQLITE_TABLE_NAME+"."+ContactContract.JEWELCHAT_ID + " = " + ChatMessageContract.SQLITE_TABLE_NAME+"."+ChatMessageContract.CHAT_ROOM
						+ " AND " + ChatMessageContract.IS_SUBMITTED + "= 0 ";

				Log.i(">>NON Submitted",query);
				Cursor returnCursor = db.rawQuery(query, null);
				//Log.i(">>", returnCursor.);
				// Sets the ContentResolver to watch this content URI for data changes
				return returnCursor;

			}
			case NON_READ:{
				Cursor returnCursor = db.query(
						ChatMessageContract.SQLITE_TABLE_NAME,
						new String[]{ChatMessageContract.KEY_ROWID, ChatMessageContract.RECEIVED_MSG_ID},
						selection, selectionArgs, null, null, sortOrder);


				return returnCursor;
			}

			case GROUP:{
				Cursor returnCursor = db.query(
						GroupMemberContract.SQLITE_TABLE_NAME,
						projection,
						selection, selectionArgs, null, null, sortOrder);

				// Sets the ContentResolver to watch this content URI for data changes
				returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
				return returnCursor;
			}
			case GROUP_MESSAGE:{
				Cursor returnCursor = db.query(
						GroupMessageContract.SQLITE_TABLE_NAME,
						projection,
						selection, selectionArgs, null, null, sortOrder);

				// Sets the ContentResolver to watch this content URI for data changes
				returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
				return returnCursor;
			}
			case JEWL_STORE:{
				Cursor returnCursor = db.query(
						JewelStoreContract.SQLITE_TABLE_NAME,
						projection,
						selection, selectionArgs, null, null, sortOrder);

				// Sets the ContentResolver to watch this content URI for data changes
				returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
				return returnCursor;
			}
			default: {
				throw new IllegalArgumentException("Query -- Invalid URI:" + uri);
			}
		}


	}

	@Nullable
	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Nullable
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long id;
		switch (uriMatcher.match(uri)) {
			case CHAT_MESSAGE:{

				try{
					id = db.insertOrThrow(ChatMessageContract.SQLITE_TABLE_NAME, null, values);
					getContext().getContentResolver().notifyChange(uri, null);
					getContext().getContentResolver().notifyChange(Uri.parse(CONTENT_URI + "/chatlist"), null);
					String creatorid = values.getAsString(ChatMessageContract.CREATOR_ID);
					String userid = JewelChatApp.getSharedPref().getString(JewelChatPrefs.MY_ID, "");
					if(!creatorid.equals(userid))
						getContext().getContentResolver().notifyChange(Uri.parse(CONTENT_URI + "/"+creatorid), null);
				}catch(SQLiteConstraintException e){
					Log.i("ConstraintException", "Duplicate row insertion");
					id=-1;
				}catch(SQLException e){
					id=-1;
				}catch(Exception e){
					id=-1;
				}

				break;
			}
			case CONTACT:{
				id = db.insertOrThrow(ContactContract.SQLITE_TABLE_NAME, null, values);
				Log.i("ID>>>>>>>>",""+id);
				getContext().getContentResolver().notifyChange(uri, null);
				getContext().getContentResolver().notifyChange(Uri.parse(CONTENT_URI + "/chatlist"), null);
				break;
			}
			case GROUP:{
				id = db.insertOrThrow(GroupMemberContract.SQLITE_TABLE_NAME, null, values);
				getContext().getContentResolver().notifyChange(uri, null);
				break;
			}
			case GROUP_MESSAGE:{
				id = db.insertOrThrow(GroupMessageContract.SQLITE_TABLE_NAME, null, values);
				getContext().getContentResolver().notifyChange(uri , null);
				break;
			}
			case JEWL_STORE:{
				id = db.insertOrThrow(JewelStoreContract.SQLITE_TABLE_NAME, null, values);
				getContext().getContentResolver().notifyChange(uri , null);
				break;
			}
			default:
				throw new IllegalArgumentException("Unsupported URI: " + uri);
		}

		return Uri.parse(uri + "/#" + id);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		int count;
		switch(uriMatcher.match(uri)) {
			case CHAT_MESSAGE:
				count = db.delete(ChatMessageContract.SQLITE_TABLE_NAME,  selection, selectionArgs);
				getContext().getContentResolver().notifyChange(uri, null);
				getContext().getContentResolver().notifyChange(Uri.parse(CONTENT_URI + "/chatlist"), null);
				break;
			case CONTACT:
				count = db.delete(ContactContract.SQLITE_TABLE_NAME, selection, selectionArgs);
				getContext().getContentResolver().notifyChange(uri, null);
				getContext().getContentResolver().notifyChange(Uri.parse(CONTENT_URI + "/chatlist" ), null);
				break;
			case GROUP:
				count = db.delete(GroupMemberContract.SQLITE_TABLE_NAME, selection, selectionArgs);
				getContext().getContentResolver().notifyChange(uri , null);
				break;
			case GROUP_MESSAGE:
				count = db.delete(GroupMessageContract.SQLITE_TABLE_NAME, selection, selectionArgs);
				getContext().getContentResolver().notifyChange(uri , null);
				break;
			case JEWL_STORE:
				count = db.delete(JewelStoreContract.SQLITE_TABLE_NAME, selection, selectionArgs);
				getContext().getContentResolver().notifyChange(uri , null);
				break;
			default:
				throw new IllegalArgumentException("Unsupported URI: " + uri);
		}


		return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		int count;
		switch(uriMatcher.match(uri)) {
			case CHAT_MESSAGE:
				count = db.update(ChatMessageContract.SQLITE_TABLE_NAME, values, selection, selectionArgs);
				getContext().getContentResolver().notifyChange(uri, null);
				getContext().getContentResolver().notifyChange(Uri.parse(CONTENT_URI + "/chatlist"), null);
				break;
			case CONTACT:
				count = db.update(ContactContract.SQLITE_TABLE_NAME, values, selection, selectionArgs);
				//Log.i(">>Update>>>>>"," "+count);
				getContext().getContentResolver().notifyChange(uri, null);
				getContext().getContentResolver().notifyChange(Uri.parse(CONTENT_URI + "/chatlist" ), null);
				break;
			case GROUP:
				count = db.update(GroupMemberContract.SQLITE_TABLE_NAME, values, selection, selectionArgs);
				getContext().getContentResolver().notifyChange(uri , null);
				break;
			case GROUP_MESSAGE:
				count = db.update(GroupMessageContract.SQLITE_TABLE_NAME, values, selection, selectionArgs);
				getContext().getContentResolver().notifyChange(uri , null);
				break;
			case JEWL_STORE:
				count = db.update(JewelStoreContract.SQLITE_TABLE_NAME, values, selection, selectionArgs);
				getContext().getContentResolver().notifyChange(uri , null);
				break;
			default:
				throw new IllegalArgumentException("Unsupported URI: " + uri);
		}


		return count;
	}

	public void close() {
		dbHelper.close();
	}
}
