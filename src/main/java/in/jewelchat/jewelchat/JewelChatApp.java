package in.jewelchat.jewelchat;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;

/**
 * Created by mayukhchakraborty on 06/06/17.
 */

public class JewelChatApp extends Application {

	private FirebaseAnalytics mFirebaseAnalytics;
	private static JewelChatApp mInstance;
	private static RequestQueue mRequestQueue;
	private static SharedPreferences sharedPref;
	private static String mCookie;
	private static Picasso mPicasso;
	//private static JewelChatSocket jcSocket;
	private static final Bus BUS = new Bus();


	public static final int CONNECTION_TIMEOUT = 10000;

	public static JewelChatApp getInstance() {
		return mInstance;
	}

	public static void appLog(@NonNull String message) {
		FirebaseCrash.log(Thread.currentThread().getName() + ":" + message);
	}

	public static SharedPreferences getSharedPref() {
		if (sharedPref == null) {
			setSharedPref();
		}
		return sharedPref;
	}

	private static void setSharedPref() {
		sharedPref = mInstance.getSharedPreferences("in.mayukh.jewelchat", Context.MODE_PRIVATE);
	}

	public static RequestQueue getRequestQueue() {
		if (mRequestQueue == null)
			setRequestQueue();
		return mRequestQueue;
	}

	public static void saveCookie(String cookie) {
		// TODO Auto-generated method stub
		if (cookie == null) {
			//the server did not return a cookie so we wont have anything to save
			return;
		}
		// Save in the preferences
		SharedPreferences.Editor editor = getSharedPref().edit();
		editor.putString("cookie", cookie);
		editor.commit();
	}

	public static String getCookie() {

		String cookie = getSharedPref().getString("cookie", "");
		if (cookie.contains("expires")) {
			/** you might need to make sure that your cookie returns expires when its expired. I also noted that cokephp returns deleted */
			removeCookie();
			return "";
		}
		return cookie;
	}

	public static void removeCookie() {

		SharedPreferences.Editor editor = getSharedPref().edit();
		editor.remove("cookie");
		editor.commit();

	}

	private static void setRequestQueue() {
		mRequestQueue = Volley.newRequestQueue(mInstance);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
		mInstance = this;
	}

	@Override
	public void onTerminate() {
		super.onTerminate();

	}
}
