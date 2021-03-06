/* HISTORY
 * CATEGORY 		:- CONSTANTS
 * DEVELOPER		:- VIKALP PATEL
 * AIM			    :- APPLICATION CONSTANTS
 * DESCRIPTION 		:- USE APPLICATION CONSTANTS
 * 
 * S - START E- END  C- COMMENTED  U -EDITED A -ADDED
 * --------------------------------------------------------------------------------------------------------------------
 * INDEX       DEVELOPER		DATE			FUNCTION		DESCRIPTION
 * --------------------------------------------------------------------------------------------------------------------
 * 10001      VIKALP PATEL    16/05/2014                        CREATED
 * --------------------------------------------------------------------------------------------------------------------
 */
package com.netdoers.zname;

import android.os.Environment;

public class AppConstants {

	///////////////////////////////////////////
	// WEBSERVICES-URL
	//////////////////////////////////////////
	
	public interface URLS{
/*
 * TESTING SERVER URL
 */
		//API V2
		public static final String BASE_URL 		= "http://myzname.netdoers.com/api/v1/znames";
		public static final String UPDATE_URL 		= "http://myzname.netdoers.com/api/v1/znames/";
		public static final String AVAILABLE_URL 	= "http://myzname.netdoers.com/api/v1/znames/username/";
		public static final String MEDIA_BASE_URL 	= "http://myzname.netdoers.com/api/v1/znames/";
		public static final String CALL_URL 		= "http://myzname.netdoers.com/api/v1/znames/";
		public static final String SEARCH_URL 		= "http://myzname.netdoers.com/api/v1/znames/";
		public static final String SYNC_URL 		= "http://myzname.netdoers.com/api/v1/znames/";
		public static final String SIGN_IN_URL      = "http://myzname.netdoers.com/api/v1/znames/activation";
		public static final String VERIFY_URL       = "http://myzname.netdoers.com/api/v1/znames/verification";
		public static final String PENDING_URL      = "http://myzname.netdoers.com/api/v1/znames/";
		public static final String REQUEST_URL      = "http://myzname.netdoers.com/api/v1/znames/";
		public static final String APPROVE_URL      = "http://myzname.netdoers.com/api/v1/znames/";
		public static final String GCM_URL          = "http://myzname.netdoers.com/api/v1/znames/";
		public static final String CALL_SYNC_URL    = "http://myzname.netdoers.com/api/v1/znames/";
		public static final String STATUS_URL       = "http://myzname.netdoers.com/api/v1/znames/";
		public static final String GET_STATUS_URL   = "http://myzname.netdoers.com/api/v1/znames/";
		
//		public static final String MEDIA_BASE_URL 	= "http://myzname.netdoers.com/api/v1/znames/ca8e181ea0ac582564de322da694f8280e293764/profilepic";
//		public static final String CALL_URL 		= "http://myzname.netdoers.com/api/v1/znames/ca8e181ea0ac582564de322da694f8280e293764/callstatus/1"; //1 : BUSY , 0 : AVAILABLE		
//		public static final String SEARCH_URL 		= "http://myzname.netdoers.com/api/v1/znames/ca8e181ea0ac582564de322da694f8280e293764/users/abc";
//		public static final String SYNC_URL 		= "http://myzname.netdoers.com/api/v1/znames/ca8e181ea0ac582564de322da694f8280e293764/contactlist";
//		public static final String PENDING_URL      = "http://myzname.netdoers.com/api/v1/znames/ca8e181ea0ac582564de322da694f8280e293764/pendingrequests";
//		public static final String REQUEST_URL      = "http://myzname.netdoers.com/api/v1/znames/ca8e181ea0ac582564de322da694f8280e293764/addrequest";
//		public static final String APPROVE_URL      = "http://myzname.netdoers.com/api/v1/znames/ca8e181ea0ac582564de322da694f8280e293764/approverequests";
//		public static final String GCM_URL          = "http://myzname.netdoers.com/api/v1/znames/ca8e181ea0ac582564de322da694f8280e293764/appregistraion";
//		public static final String CALL_SYNC_URL    = "http://myzname.netdoers.com/api/v1/znames/ca8e181ea0ac582564de322da694f8280e293764/users/callstatus";
//		public static final String STATUS_URL       = "http://myzname.netdoers.com/api/v1/znames/ca8e181ea0ac582564de322da694f8280e293764/status";
//		public static final String GET_STATUS_URL   = "http://myzname.netdoers.com/api/v1/znames/ca8e181ea0ac582564de322da694f8280e293764/users/status";
	}

	/////////////////////////////////////
	// VIEW TAGS CONSTANTS
	////////////////////////////////////
	public interface TAGS{
		public interface INTENT{
			public static final String TAG_NAME ="name";
			public static final String TAG_ID ="_id";
			public static final String TAG_PHOTO ="photo";
			public static final String TAG_NUMBER ="number";
			public static final String TAG_ZNAME ="zname";
			public static final String TAG_CALL_STATUS ="call_status";
		}
	}
	
	public interface HEADERS{
		public static final String USER 	= "netdoersadmin";
		public static final String PASSWORD = "538f25fc32727";
	}
	
	public interface RESPONSES{
		public interface RegistrationResponse{
			public static String STATUS = "status";
			public static String ZNAME = "zname";
			public static String API_KEY="api_key";
			public static String ERROR = "errors";
		}
	}
	
	public static final String NETWORK_NOT_AVAILABLE = "Network not available";
	public static final String IMAGE_DIRECTORY_PATH = Environment.getExternalStorageDirectory().getPath()+"/Zname";
	public static final String IMAGE_DIRECTORY_PATH_DATA = Zname.getApplication().getApplicationContext().getFilesDir().getAbsolutePath();
	public static final String EXTENSION = ".jpg";
	public static final String VIDEO_EXTENSION = ".mp4";
	public static final String GCM_SENDER_ID = "133307685455";
	
	//////////////////////////////////////////
	// APPLICATION FONT STYLE
	//////////////////////////////////////////
	
//	public static final String fontStyle = "fonts/RobotoCondensedBold.ttf";
	public static final String fontStyle = "fonts/Georgia.ttf";
	
	public static final boolean DEBUG = false;
}

