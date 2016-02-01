package smartprofiler.presenter;

import smartprofiler.views.ProfilesActivity;
import android.os.AsyncTask;
import android.util.Log;

public class DeleteProfileTask extends AsyncTask<ProfileData, Void, Boolean> {
	
	

	private ProfilesManager mProfilesManager;
	private ProfileData mProfileData;
    private ProfilesActivity mActivity;
    
    /**
     * Used for Android debugging.
     */
    private final static String TAG = 
        DeleteProfileTask.class.getName();
	
	
	/**
	 * Public constructor
	 */
	public DeleteProfileTask(ProfilesManager manager, ProfilesActivity activity){
		
		
		mProfilesManager = manager;
		mActivity = activity;
	}

	@Override
	protected Boolean doInBackground(ProfileData... params) {
		// TODO Auto-generated method stub
		Log.d(TAG, "doInBackground");
		mProfilesManager.open();
		Boolean success = mProfilesManager.deleteProfile(params[0]);
		Log.d(TAG, "deleting row is " + success);
		mProfilesManager.close();
		return success;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		Log.d(TAG, "onPostExecute");
		//reads the newly updated dB table and displays the profiles in the UI thread
		if(result)
			new ProfilesLoader(mProfilesManager, mActivity).execute();
		else
			Log.d(TAG, "Unsuccessfully deletion");
	
	}
	

}
