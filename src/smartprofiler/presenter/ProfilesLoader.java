package smartprofiler.presenter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import smartprofiler.views.ProfilesActivity;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

public class ProfilesLoader extends AsyncTask<Void, Void, List<ProfileData>> {

	
	

	/**
     * Used for Android debugging.
     */
    private final static String TAG = 
    		ProfilesLoader.class.getName();

	ProfilesManager mProfilesManager;
	
	/**
     * Used to enable garbage collection.
     */
    private WeakReference<ProfilesActivity> mActivity;
	
	
	public ProfilesLoader(ProfilesManager manager, ProfilesActivity activity){
		mProfilesManager = manager;
		mActivity = new WeakReference<ProfilesActivity>(activity);
		
	}
	
	@Override
	protected List<ProfileData> doInBackground(Void... params) {
		// TODO Auto-generated method stub
		Log.d(TAG, "doInBackground");
		List<ProfileData> profilesList = new ArrayList<ProfileData>();
		mProfilesManager.open();
		profilesList = mProfilesManager.getAll();
		return profilesList = mProfilesManager.getAll();
	}
	
	@Override
	protected void onPostExecute(List<ProfileData> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		Log.d(TAG, "onPostExecute");
		Activity activity = mActivity.get();
		if (activity != null) {
		   // do your stuff with activity here
			//displayProfiles(result);
			mActivity.get().displayProfiles(result);
		}
		Log.d(TAG, "Loader");
		for(int i = 0; i < result.size(); i++)
			Log.d(TAG, result.get(i).getProfileName());
		
		
	}

	
}
