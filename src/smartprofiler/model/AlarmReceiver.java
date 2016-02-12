package smartprofiler.model;

import smartprofiler.presenter.ProfileData;
import smartprofiler.views.AddProfileActivity;
import Utils.Utils;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;


public class AlarmReceiver extends BroadcastReceiver{
	
	
	protected final static int STATUS_OFF = 0;
	protected final static int STATUS_ON = 1;
	protected final static int STATUS_NO_CHANGE = 2;
	public static final String BUNDLE_CODE = "receiver bundle";
	
	 /**
     * Used for Android debugging.
     */
	protected final static String TAG = 
			        AlarmReceiver.class.getName();
	
		 //Callback method called upon firing of the Alarm
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		  Log.d(TAG, "onReceive()");	
		  if(intent != null){
			  Log.d(TAG, "intent != null");
			  ProfileData alarmProfile = Utils.getBundledData(intent, BUNDLE_CODE, ProfileAlarmManager.ALARM_DATA);
			  Log.d(TAG, alarmProfile.getProfileName());
			  int mode = intent.getIntExtra(ProfileAlarmManager.ALARM_STATUS, 0);
			  Log.d(TAG, String.valueOf(mode));
					  
		switch(mode){
		case ProfileAlarmManager.START_ALARM: Log.d(TAG, "StartAlarm");
		
		 if(alarmProfile == null)
	          	Log.d(TAG, "intent data extracted are null");
	          else{
	          	Log.d(TAG, alarmProfile.getProfileName());
	          
	          	switch(alarmProfile.getProfileWiFi()){
	          	case 0: Log.d(TAG, "Switch off WiFi");
	          			Utils.toggleWiFi(STATUS_OFF, context);
	          		break;
	          	case 1: Log.d(TAG,"Switch off WiFi");
	          	        Utils.toggleWiFi(STATUS_ON, context);
	          		break;
	          	case 2: Log.d(TAG," No change WiFi");
	          		break;
	          		default: Log.d(TAG," Wrong data for WiFi");
	          	}
	          	
	          	switch(alarmProfile.getProfileSound()){
	          	case 0: Log.d(TAG, "Switch off Sound");
	          			Utils.volumeControl(STATUS_OFF, context);
	          		break;
	          	case 1: Log.d(TAG,"Switch off Sound");
	          			Utils.volumeControl(STATUS_ON, context);
	          		break;
	          	case 2: Log.d(TAG," No change Sound");
	          		break;
	          		default: Log.d(TAG," Wrong data for Sound");
	          	}
	          	
	          	switch(alarmProfile.getProfileVibration()){
	          	case 0: Log.d(TAG, "Switch off Vibration");
	          			Utils.toggleVibration(STATUS_OFF, context);
	          		break;
	          	case 1: Log.d(TAG,"Switch off Vibration");
	          			Utils.toggleVibration(STATUS_ON, context);
	          		break;
	          	case 2: Log.d(TAG," No change Vibration");
	          		break;
	          		default: Log.d(TAG," Wrong data for Vibration");
	          	}
	         
	          }
			break;
		case ProfileAlarmManager.STOP_ALARM: Log.d(TAG, "StopAlarm");
			break;
			default: Log.d(TAG, "Wrong mode");
		} }
		  else
			  Log.d(TAG, "intent == null");		
         
		 
	  }

}
