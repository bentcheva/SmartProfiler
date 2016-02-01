package smartprofiler.presenter;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class ProfilesContract {
	
	
	 public static final String CONTENT_AUTHORITY =
		        "com.example.smartprofiler";
	 
	
	 public static final Uri BASE_CONTENT_URI =
		        Uri.parse("content://"
		                  + CONTENT_AUTHORITY);
	 
	 public static final String PATH_PROFILES =
		        ProfileEntry.TABLE_NAME;
	 
	 /**
	     * Inner class that defines the contents of the Profiles table.
	     */
	    public static final class ProfileEntry implements BaseColumns {
	        /**
	         * Use BASE_CONTENT_URI to create the unique URI for Profiles
	         * Table that apps will use to contact the content provider.
	         */
	        public static final Uri CONTENT_URI = 
	            BASE_CONTENT_URI.buildUpon()
	            .appendPath(PATH_PROFILES).build();

	        /**
	         * When the Cursor returned for a given URI by the
	         * ContentProvider contains 0..x items.
	         */
	        public static final String CONTENT_ITEMS_TYPE =
	            "vnd.android.cursor.dir/"
	            + CONTENT_AUTHORITY 
	            + "/" 
	            + PATH_PROFILES;

	        /**
	         * When the Cursor returned for a given URI by the
	         * ContentProvider contains 1 item.
	         */
	        public static final String CONTENT_ITEM_TYPE =
	            "vnd.android.cursor.item/"
	            + CONTENT_AUTHORITY 
	            + "/" 
	            + PATH_PROFILES;

	        /**
	         * Name of the database table.
	         */
	        public static final String TABLE_NAME =
	            "profiles_table";

	        /**
	         * Columns to store Data of each Profile.
	         */
	        public static final String COLUMN_NAME = "name";
	        public static final String COLUMN_STATUS= "status";
	        public static final String COLUMN_WIFI = "wifi";
	        public static final String COLUMN_SOUND = "sound";
	        public static final String COLUMN_MOBILE_DATA = "mobile";
	        public static final String COLUMN_VIBRATION = "vibration";
	        public static final String COLUMN_START = "start";
	        public static final String COLUMN_STOP = "stop";
	        

	        /**
	         * Return a Uri that points to the row containing a given id.
	         * 
	         * @param id
	         * @return Uri
	         */
	        public static Uri buildProfileUri(Long id) {
	            return ContentUris.withAppendedId(CONTENT_URI,
	                                              id);
	        }
	    }

}
