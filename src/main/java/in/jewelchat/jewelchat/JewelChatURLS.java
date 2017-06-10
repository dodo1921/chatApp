package in.jewelchat.jewelchat;

/**
 * Created by mayukhchakraborty on 24/02/16.
 */
public class JewelChatURLS {

	private static final String baseURL = "https://jgamebackend.herokuapp.com";

	public static final String CLOUDPATH = " ";

	public static final String REGISTRATION_URL = baseURL + "/registerPhoneNumber";
	public static final String VERIFICATIONCODE_URL = baseURL + "/verifyCode";
	public static final String RESENDVCODE_URL = baseURL + "/resendVcode";
	public static final String INITIAL_DETAILS = baseURL + "/initialDetails";
	public static final String UPDATE_GCM_TOKEN = baseURL + "/updateGcmToken";

	public static final String GETCONTACTBYPHONENUMBERLIST = baseURL + "/getContactByPhoneNumberList";
	public static final String GETGAMESTATE = baseURL + "/getGameState";

	public static final String GETGROUPLIST = baseURL + "/getGroupList";

}
