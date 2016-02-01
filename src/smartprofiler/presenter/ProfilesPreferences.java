package smartprofiler.presenter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class ProfilesPreferences {

	public static final String PROFILE_PREFERENCES = "SmartProfilerPrefs";
	private WeakReference<Context> mContext;
	private SharedPreferences mMyPreference;
	
	public static final String PROFILE1 = "Profile1";
	public static final String PROFILE2 = "Profile2";
	public static final String PROFILE3 = "Profile3";
	public static final String PROFILE4 = "Profile4";
	
	/**
     * Used for Android debugging.
     */
    private final static String TAG = 
        ProfilesPreferences.class.getName();
	
	/*
	 * Constructor
	 */
	public ProfilesPreferences(Context context){
		mContext = new WeakReference<Context>(context);
		mMyPreference = mContext.get()
				.getSharedPreferences(PROFILE_PREFERENCES, Context.MODE_PRIVATE);
	}
	
	
	/*
	 * Returns true if the shared preferences file contains the first profile
	 * and false if it doesn't 
	 */
	public boolean isFirstProfile(){
		
		String firstProfile = mMyPreference.getString(PROFILE1, null);
		// checking if this is the Shared Preferences file exist
		// which will indicate if this is the first time running the application
		if (firstProfile == null) 
			return true;
		else 
			return false;
	}
	
	public ArrayList<String> loadPreferences(CountDownLatch mutex){
		
		ArrayList<String> profilesList = new ArrayList<String>();
		profilesList.add(mMyPreference.getString(PROFILE1, null));
		Log.d(TAG, profilesList.get(0));
		profilesList.add(mMyPreference.getString(PROFILE2, null));
		Log.d(TAG, profilesList.get(1));
		mutex.countDown();
		return profilesList;
		
	}
	
	
	public Runnable initialPreferences(){
		
		return  new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Editor editor = mMyPreference.edit();
				editor.putString(PROFILE1, mContext.get().getResources().getString(0x7f040002));
				editor.putString(PROFILE2, mContext.get().getResources().getString(0x7f040003));
				editor.commit();
			}
		};
		
		
	}
}
