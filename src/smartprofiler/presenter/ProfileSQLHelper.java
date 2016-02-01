package smartprofiler.presenter;

import smartprofiler.presenter.ProfilesContract.ProfileEntry;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ProfileSQLHelper extends SQLiteOpenHelper {
	
	/**
     * Used for Android debugging.
     */
    private final static String TAG = 
        ProfileSQLHelper.class.getName();
	

	  private static final String DATABASE_NAME = "profiles.db";
	  private static final int DATABASE_VERSION = 1;

	  // Database creation sql statement
	  final String SQL_CREATE_PROFILES_TABLE =
	            "CREATE TABLE "
	            + ProfileEntry.TABLE_NAME + " (" 
	            + ProfileEntry._ID + " INTEGER PRIMARY KEY, " 
	            + ProfileEntry.COLUMN_NAME + " TEXT NOT NULL, " 
	            + ProfileEntry.COLUMN_STATUS + " INTEGER NOT NULL, " 
	            + ProfileEntry.COLUMN_WIFI + " INTEGER NOT NULL, " 
	            + ProfileEntry.COLUMN_SOUND + " INTEGER NOT NULL, "
	            + ProfileEntry.COLUMN_MOBILE_DATA + " INTEGER NOT NULL, "
	            + ProfileEntry.COLUMN_VIBRATION + " INTEGER NOT NULL, "
	            + ProfileEntry.COLUMN_START + " INTEGER NOT NULL, "
	            + ProfileEntry.COLUMN_STOP + " INTEGER NOT NULL "
	            
	            + " );";

	/*
	 * Constructor
	 */
	public ProfileSQLHelper(Context context){
		//super(context, DATABASE_NAME, null, DATABASE_VERSION);
		super(context, 
	              DATABASE_NAME,
	              null, 
	              DATABASE_VERSION);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(SQL_CREATE_PROFILES_TABLE);
		Log.d(TAG, "onCreate() ");
		
	}
	
	public void deleteTable(SQLiteDatabase db){
		  db.execSQL("DROP TABLE IF EXISTS " + ProfileEntry.TABLE_NAME);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onUpdate() ");
		 Log.w(ProfileSQLHelper.class.getName(),
			        "Upgrading database from version " + oldVersion + " to "
			            + newVersion + ", which will destroy all old data");
			    db.execSQL("DROP TABLE IF EXISTS " + ProfileEntry.TABLE_NAME);
			    onCreate(db);
		
	}}
