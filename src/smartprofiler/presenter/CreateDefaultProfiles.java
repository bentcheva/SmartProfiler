package smartprofiler.presenter;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

public class CreateDefaultProfiles extends AsyncTask<Void, Void, List<ProfileData>>{
	
	public static final int DEFAULT_START = 5000;
	public static final int DEFAULT_STOP = 5000;
	
	/**
     * Used for Android debugging.
     */
    private final static String TAG = 
    		CreateDefaultProfiles.class.getName();

	ProfilesManager mProfilesManager;
	
	public CreateDefaultProfiles(ProfilesManager manager) {
		// TODO Auto-generated constructor stub
		Log.d(TAG, "creating dB table with default profiles.");
		mProfilesManager = manager;
		
	}

	
	
	@Override
	protected List<ProfileData> doInBackground(Void... params) {
		// TODO Auto-generated method stub
		Log.d(TAG, "doInBackground");
		List<ProfileData> listProfiles = new ArrayList<ProfileData>();
		mProfilesManager.open();
		ProfileData noProfile = new ProfileData("No profile", 1, 0, 0, 0, 0 , DEFAULT_START, DEFAULT_STOP, null);
		ProfileData meetingProfile = new ProfileData("Meeting", 0, 2, 0, 1, 2 , DEFAULT_START, DEFAULT_STOP, null);
		ProfileData dailyProfile = new ProfileData("Daily", 0, 2, 2, 2, 2 , 6, 22, null);
		mProfilesManager.createProfile(noProfile);
		listProfiles.add(noProfile);
		mProfilesManager.createProfile(meetingProfile);
		listProfiles.add(meetingProfile);
		mProfilesManager.createProfile(dailyProfile);
		listProfiles.add(dailyProfile);
		return listProfiles;
	}

	@Override
	protected void onPostExecute(List<ProfileData> result) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onPostExecute");
		Log.d(TAG, result.get(0).getProfileName());
		Log.d(TAG, result.get(1).getProfileName());
		Log.d(TAG, result.get(2).getProfileName());
		mProfilesManager.close();
		super.onPostExecute(result);
	}


}
