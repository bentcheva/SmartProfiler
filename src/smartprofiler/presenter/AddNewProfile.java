package smartprofiler.presenter;

import java.lang.ref.WeakReference;

import smartprofiler.views.ProfilesActivity;
import smartprofiler.views.ProfilesAdapter;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * This class extends AsyncTask. It is purpose is to add the new Profile to the db table with profiles.
 * In it's postExecute method calls another AsyncTask which reads the profiles from the dB table
 * and displays them on the UI thread
 * @author Bobi
 *
 */

public class AddNewProfile extends AsyncTask<ProfileData, Void, Void>{
	
	private ProfilesManager mProfilesManager;
	private ProfileData mProfileData;
    private ProfilesActivity mActivity;
    
    /**
     * Used for Android debugging.
     */
    private final static String TAG = 
        AddNewProfile.class.getName();
	
	/**
	 * Public constructor 
	 * @param manager SQL DB Manager
	 * @param data Profile data to be add to the dB table with profiles
	 */
	
	public AddNewProfile(ProfilesManager manager, ProfileData data, ProfilesActivity activity){
		
		mProfileData = data;
		mProfilesManager = manager;
		mActivity = activity;
	
		
	}

	@Override
	protected Void doInBackground(ProfileData... params) {
		// TODO Auto-generated method stub
		Log.d(TAG, "do in background");
		mProfilesManager.open();
		mProfilesManager.createProfile(mProfileData);
		mProfilesManager.close();
		
		
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		Log.d(TAG, "on Post execute");
		//reads the newly updated dB table and displays the profiles in the UI thread
		new ProfilesLoader(mProfilesManager, mActivity).execute();
	
		
	}

	

	
	
	

	

}
