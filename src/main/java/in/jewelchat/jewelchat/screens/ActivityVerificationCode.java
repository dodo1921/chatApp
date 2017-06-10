package in.jewelchat.jewelchat.screens;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.google.firebase.crash.FirebaseCrash;

import org.json.JSONException;
import org.json.JSONObject;

import in.jewelchat.jewelchat.BaseNetworkActivity;
import in.jewelchat.jewelchat.JewelChatApp;
import in.jewelchat.jewelchat.JewelChatPrefs;
import in.jewelchat.jewelchat.JewelChatURLS;
import in.jewelchat.jewelchat.R;
import in.jewelchat.jewelchat.network.JewelChatRequest;

/**
 * Created by mayukhchakraborty on 29/02/16.
 */
public class ActivityVerificationCode extends BaseNetworkActivity implements TextView.OnEditorActionListener,  Response.Listener<JSONObject>{

	private EditText editText;
	private String verificationCode;
	private String requestTag = "verification";
	private Button submit;
	private TextView resend;
	int resend_count = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verification);
		className = getClass().getSimpleName();
		rootLayout = (LinearLayout) findViewById(R.id.verify_root);
		submit = (Button) rootLayout.findViewById(R.id.verify_button);
		submit.setOnClickListener(this);
		editText = (EditText) rootLayout.findViewById(R.id.verification_code);
		editText.setOnEditorActionListener(this);
		resend = (TextView) rootLayout.findViewById(R.id.resend_code);
		resend.setOnClickListener(this);

	}

	@Override
	protected void setUpAppbar() {

	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.verify_button) {
			action();
			return;
		}
		if(v.getId() == R.id.resend_code){
			resendCode();
			return;
		}


	}

	@Override
	public void onResponse(JSONObject response) {
		JewelChatApp.appLog(className + ":onResponse");

		try {
			Boolean error = response.getBoolean("error");
			if(error){
				String err_msg = response.getString("message");
				throw new Exception(err_msg);
			}
			String request = response.getString("request");
			if(request.equals("verifyCode")){

				SharedPreferences.Editor editor = JewelChatApp.getSharedPref().edit();
				editor.putBoolean(JewelChatPrefs.IS_LOGGED,true);
				editor.commit();


				hideKeyBoard();
				dismissDialog();

				if(!JewelChatApp.getSharedPref().getBoolean(JewelChatPrefs.ACTIVE, false)){
					Intent intent = new Intent(getApplicationContext(), ActivityInitialDetails.class);
					startActivity(intent);
					finish();
				}else{
					//Intent intent = new Intent(getApplicationContext(), JewelChat.class);
					//startActivity(intent);
					//finish();
				}





			}else if(request.equals("resendVcode")){
				dismissDialog();
				if(resend_count>=3){
					return;
				}else if(resend_count==1){
					resend_count++;
					resend.setText("Resend one more time");
				}else if(resend_count==2){
					resend_count++;
					resend.setText("");
					resend.setClickable(false);
				}



			}


		} catch (JSONException e) {
			FirebaseCrash.report(e);
			e.printStackTrace();
			hideKeyBoard();
			dismissDialog();
			makeToast("Error. Please try again...");
		}catch ( Exception e) {
			FirebaseCrash.report(e);
			e.printStackTrace();
			hideKeyBoard();
			dismissDialog();
			makeToast("Error. Please try again...");
		}


	}



	private boolean action() {

		JewelChatApp.appLog(className + ":action");
		verificationCode = this.editText.getText().toString().trim();

		if(verificationCode.length() == 0){
			makeToast("Please enter verification code");
			return false;
		}if(verificationCode.length() <6){
			return false;
		}else if(verificationCode.length() == 6){

			createDialog(getString(R.string.please_wait));

			JSONObject jsonParams = new JSONObject();
			try {

				jsonParams.put("userId", JewelChatApp.getSharedPref().getInt(JewelChatPrefs.MY_ID,0));
				jsonParams.put("verificationCode", verificationCode);


			} catch (JSONException e) {
				e.printStackTrace();
				FirebaseCrash.report(e);
			}


			JewelChatRequest request = new JewelChatRequest(Request.Method.POST,
					JewelChatURLS.VERIFICATIONCODE_URL, jsonParams, this, this);

			if (addRequest(request)) {
				return true;
			}else
				return false;

		}

		return false;

	}

	private void resendCode() {


		createDialog(getString(R.string.please_wait));

		JSONObject jsonParams = new JSONObject();
		try {

			jsonParams.put("userId", JewelChatApp.getSharedPref().getInt(JewelChatPrefs.MY_ID, 0));


		} catch (JSONException e) {
			FirebaseCrash.report(e);
			e.printStackTrace();
		}


		JewelChatRequest request = new JewelChatRequest(Request.Method.POST,
				JewelChatURLS.RESENDVCODE_URL, jsonParams, this, this);

		addRequest(request);

	}

	private void hideKeyBoard() {
		try {
			InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			manager.hideSoftInputFromWindow(editText.getWindowToken(), 0);

		} catch (Exception e) {
			JewelChatApp.appLog(getClass().getSimpleName() + ":" + e.toString());
		}
	}

	@Override
	public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
		return ((actionId == EditorInfo.IME_ACTION_DONE) && action());
	}
}
