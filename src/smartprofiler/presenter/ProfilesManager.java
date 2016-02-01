package smartprofiler.presenter;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * This class provides DataBase operations.
 * Inserts, deletes, queries records from the db table. 
 * Each record represents a profile. The record's fields represent the 
 * profile's properties.
 * @author Bobi
 *
 */

public class ProfilesManager {
	
	private SQLiteDatabase mProfilesDB;
	private ProfileSQLHelper mProfilesSQLHelper;
	private String[] mColumns = {ProfilesContract.ProfileEntry._ID,
								 ProfilesContract.ProfileEntry.COLUMN_NAME, 
							     ProfilesContract.ProfileEntry.COLUMN_STATUS, 
							     ProfilesContract.ProfileEntry.COLUMN_WIFI,
							     ProfilesContract.ProfileEntry.COLUMN_SOUND,
							     ProfilesContract.ProfileEntry.COLUMN_MOBILE_DATA,
							     ProfilesContract.ProfileEntry.COLUMN_VIBRATION,
							     ProfilesContract.ProfileEntry.COLUMN_START,
							     ProfilesContract.ProfileEntry.COLUMN_STOP};
	
	
	/**
     * Used for Android debugging.
     */
    private final static String TAG = 
        ProfilesManager.class.getName();
	
	
	public ProfilesManager(Context context){
		mProfilesSQLHelper = new ProfileSQLHelper(context);
	}
	/**
	 * This method opens the db table in write mode.
	 */
	public void open(){
		mProfilesDB = mProfilesSQLHelper.getWritableDatabase();
		Log.d(TAG, "open db ");
	}
	/**
	 * This method closes the db table.
	 */
	public void close(){
		mProfilesSQLHelper.close();
		Log.d(TAG, "close db ");
	}
	
	/**
	 * this method deletes the table
	 */
	public void delete()
	{
		Log.d(TAG, "delete db ");
		mProfilesSQLHelper.deleteTable(mProfilesSQLHelper.getWritableDatabase());
		
	}
	
	/**
	 * This method checks if the db table is empty
	 * @return
	 */
	public boolean isTableEmpty(){
		mProfilesDB = mProfilesSQLHelper.getReadableDatabase();
		boolean empty = true;
				Cursor cur = mProfilesDB.rawQuery("SELECT COUNT(*) FROM " + ProfilesContract.ProfileEntry.TABLE_NAME, null);
				if (cur != null && cur.moveToFirst()) {
				    empty = (cur.getInt (0) == 0);
				}
				cur.close();

				return empty;
	}
	
	/**
	 * This method checks if the db table already exists.
	 * @return true if the db table exists and false if not.
	 */
	public boolean doesTableExist() {
		mProfilesDB = mProfilesSQLHelper.getReadableDatabase();
	    Cursor cursor = mProfilesDB.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + ProfilesContract.ProfileEntry.TABLE_NAME + "'", null);

	    if (cursor != null) {
	        if (cursor.getCount() > 1) {
	        	cursor.close();
	            return true;
	        }
	        cursor.close();
	    } 
	    mProfilesDB.close();
	    return false;
	}
	
	/**
	 * This method inserts record in the db table
	 * @return -1 if the operation was unsuccesful 
	 * @return #N the number of the inserted row/record.
	 */
	public long createProfile(ProfileData profile){
		
		ContentValues values = new ContentValues();
	    values.put(ProfilesContract.ProfileEntry.COLUMN_NAME, profile.getProfileName());
	    values.put(ProfilesContract.ProfileEntry.COLUMN_STATUS,  profile.getProfileStatus());
	    values.put(ProfilesContract.ProfileEntry.COLUMN_WIFI, profile.getProfileMobileData());
	    values.put(ProfilesContract.ProfileEntry.COLUMN_SOUND, profile.getProfileSound());
	    values.put(ProfilesContract.ProfileEntry.COLUMN_MOBILE_DATA, profile.getProfileMobileData());
	    values.put(ProfilesContract.ProfileEntry.COLUMN_VIBRATION, profile.getProfileVibration());
	    values.put(ProfilesContract.ProfileEntry.COLUMN_START, profile.getProfileStartTime());
	    values.put(ProfilesContract.ProfileEntry.COLUMN_STOP, profile.getProfileStopTime());
	    
	    return mProfilesDB.insert(ProfilesContract.ProfileEntry.TABLE_NAME, null, values);
	  /*   */
	   
	    
	}
	/**
	 * This method deletes record/row from the db table
	 */
	public boolean deleteProfile(ProfileData profile) {
		if(profile != null){
		    long id = profile.getProfileId();
		    if(id > -1){
			    Log.d(TAG,"Comment deleted with id: " + id);
			    mProfilesDB.delete(ProfilesContract.ProfileEntry.TABLE_NAME, BaseColumns._ID
			        + " = " + id, null);
			    return true;
		    }
		    else{
		    	Log.d(TAG," Invalid Profile's id: " + id);
		    	return false;
		    }
		}
		else 
			return false;
		
	  }
	
	/**
	 * This method deletes record/row from the db table by id number 
	 */
	public boolean deleteProfile(int id) {
		
		    if(id > -1){
			    Log.d(TAG,"Comment deleted with id: " + id);
			    mProfilesDB.delete(ProfilesContract.ProfileEntry.TABLE_NAME, BaseColumns._ID
			        + " = " + id, null);
			    return true;
		    }
		    else{
		    	Log.d(TAG," Invalid Profile's id: " + id);
		    	return false;
		    }
		
		
	  }
	
	/**
	 * This method transforms the cursor into ProfileSchema object
	 * @param cursor
	 * @return
	 */
	private ProfileData cursorToProfileData(Cursor cursor) {
		if(cursor!= null){
			Log.d(TAG, "cursorToProfileData");
		    ProfileData profile = new ProfileData();
		    profile.setProfileId(cursor.getLong(0));
		    profile.setProfileName(cursor.getString(1));
		    profile.setProfileStatus(cursor.getInt(2));
		    profile.setProfileWiFi(cursor.getInt(3));
		    profile.setProfileSound(cursor.getInt(4));
		    profile.setProfileMobileData(cursor.getInt(5));
		    profile.setProfileVibration(cursor.getInt(6));
		    profile.setProfileStartTime(cursor.getInt(7));
		    profile.setProfileStopTime(cursor.getInt(8));
		    return profile;
		}
		else 
			return null;
		    	
		  }
	/**
	 * 
	 * @param id Id of the db's record/row we want to retrieve
	 * @return returns the ProfileSchema object containing the Profile's properties.
	 */
	public ProfileData getProfile(long id){
		Cursor cursor = getRecord(id);
		if(cursor!=null)
			return cursorToProfileData(cursor);
		else
			return null;
		
	}
	/**
	 * This method returns all records/profiles from the db table.
	 * @return List of ProfileData
	 */
	public List<ProfileData> getAll(){
		List<ProfileData> allProfiles = new ArrayList<ProfileData>();
		Cursor cursor = mProfilesDB.query(ProfilesContract.ProfileEntry.TABLE_NAME,
		        mColumns, null, null, null, null, null);

		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		      ProfileData profile = cursorToProfileData(cursor);
		      allProfiles.add(profile);
		      cursor.moveToNext();
		    }
		    cursor.close();
		    return allProfiles;
	}
	
	/**
	 * 
	 * @param id id of the db's record/row to be retireved
	 * @return cursor containing the record
	 */
	public Cursor getRecord(long id){
		 Cursor cursor = mProfilesDB.query(ProfilesContract.ProfileEntry.TABLE_NAME,
			        mColumns, BaseColumns._ID + " = " + id, null,
			        null, null, null);
		 return cursor;
	}
	/**
	 * updates record/profile in the data base
	 * @param data
	 * @return true if more than 0 records have been updated
	 * and false if none has been updated
	 */
	
	public boolean updateProfile(ProfileData data){
		long id = data.getProfileId();
		ContentValues values = new ContentValues();
		if(data.getProfileName()!= null)
			values.put(ProfilesContract.ProfileEntry.COLUMN_NAME, data.getProfileName());
		
		values.put(ProfilesContract.ProfileEntry.COLUMN_STATUS, data.getProfileStatus());
		values.put(ProfilesContract.ProfileEntry.COLUMN_WIFI, data.getProfileWiFi());
		values.put(ProfilesContract.ProfileEntry.COLUMN_SOUND, data.getProfileSound());
		values.put(ProfilesContract.ProfileEntry.COLUMN_MOBILE_DATA, data.getProfileMobileData());
		values.put(ProfilesContract.ProfileEntry.COLUMN_VIBRATION, data.getProfileVibration());
		values.put(ProfilesContract.ProfileEntry.COLUMN_START, data.getProfileStartTime());
		values.put(ProfilesContract.ProfileEntry.COLUMN_STOP, data.getProfileStopTime());
		//updates the changed fields/properties  of the record/profile
		//rows is the number of the updated  rows
		int rows = mProfilesDB.update(ProfilesContract.ProfileEntry.TABLE_NAME, values, 
				BaseColumns._ID + "=" + id, null);
		
		if(rows > 0)
			return true;
		else
			return false;
	}
	
	
	
	 
	 
}
