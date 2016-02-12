package Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import smartprofiler.presenter.ProfileData;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

/**
 * Class Utils consists of static methods for performing different operations like - 
 * time conversion in minutes, convertion of time into millis format, switching on/off the Sound, 
 * WiFi, vibration
 * 
 * @author Bobi
 *
 */

public class Utils {

	
	//TAG for debuging
	 private final static String TAG = 
		        Utils.class.getName();
	
	public static void showToast(Context context, String msg){
		Toast.makeText(context,
                msg,
                Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * This method transforms the time format h:m into minutes
	 * @param hour Hour in 24 hour format
	 * @param min Minutes 
	 * @return the Time in minutes
	 */
	public static int timeInMinutes(int hour, int min){
		return (hour*60 + min);
	}
	
	/**
	 * This method converts the time in minutes into hours and minutes
	 * @param min Time in minutes
	 * @return Array of two elements. the 0 index element represents the Hours and the 1 index element the Minutes
	 */
	public static int[] minutesToTime(int min){
		int[] time = new int[2];
		time[0] =(int) min/60;
		time[1] = min - time[0]*60;
		return time;
	}
	public static List<String> namesToString(List<ProfileData> data){
		List<String> names = new ArrayList<String>();
		if(data!= null)
		{
			for(ProfileData profile: data)
				names.add(profile.getProfileName());
		}
		return names;
	}
	
	public static int[] statusToString(List<ProfileData> data){
		int[] statuses = null;
		if(data!= null)
		{
			for(int i=0; i < data.size(); i++)
				statuses[i] = data.get(i).getProfileStatus();
		}
		return statuses;
	}
	
	/* Checks if external storage is available for read and write */
	public static boolean isExternalStorageWritable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        return true;
	    }
	    return false;
	}

	 
	/**
	 * This method returns the start and stop Time for the profile in millis format - milliseconds since the Epoch 
	 * Checks if the stop time is smaller than the start time, which will mean that the stop time is in the next day
	 * 
	 *
	 * @param sleepH Start time's hour as integer in 24 hour format
	 * @param wakeH Stop time's hour as integer in 24 hour format
	 * @param sleepM Start time's minutes as integer 
	 * @param wakeM Stop time's minutes as integer
	 * @return Array of millis, where the element in 0 index is start time and the element in 1st index is the stop time
	 */
	
public static long[] setTimeInMillis(int sleepH, int wakeH, int sleepM, int wakeM){
		
		
 		Calendar[] alarmDate = new Calendar[2];
 		alarmDate[0] = Calendar.getInstance();
		alarmDate[1] = Calendar.getInstance();
 		long[] millsDate = new long[2];
 		
		if(wakeH < sleepH){
			
			alarmDate[1].set(Calendar.DAY_OF_YEAR, alarmDate[1].get(Calendar.DAY_OF_YEAR) + 1);
			Log.d(TAG, "day of year increased " + alarmDate[1].get(Calendar.DAY_OF_YEAR));
		}else{
			if(wakeH == sleepH)
			{
				if(wakeM <= sleepM)
				{
					alarmDate[1].set(Calendar.DAY_OF_YEAR, alarmDate[1].get(Calendar.DAY_OF_YEAR) + 1);
					Log.d(TAG, "day of year increased " + alarmDate[1].get(Calendar.DAY_OF_YEAR));
				}
			}
			else{
			if(sleepH != 0 && sleepM !=0)
			
			{
				alarmDate[0].set(Calendar.DAY_OF_YEAR, alarmDate[0].get(Calendar.DAY_OF_YEAR));
				alarmDate[1].set(Calendar.DAY_OF_YEAR, alarmDate[0].get(Calendar.DAY_OF_YEAR));
				Log.d(TAG, "inside one of the ifs");
			}
			else{
				if(sleepH==0 && sleepM == 0)
				alarmDate[1].set(Calendar.DAY_OF_YEAR, alarmDate[1].get(Calendar.DAY_OF_YEAR) + 1);
				alarmDate[0].set(Calendar.DAY_OF_YEAR, alarmDate[0].get(Calendar.DAY_OF_YEAR) + 1);
			}
		  }
		}
		
		
		alarmDate[0].set(Calendar.DAY_OF_YEAR, alarmDate[0].get(Calendar.DAY_OF_YEAR));
		alarmDate[0].set(Calendar.HOUR_OF_DAY, sleepH);
		alarmDate[0].set(Calendar.MINUTE, sleepM);
		alarmDate[0].set(Calendar.MILLISECOND, 0);
		
		
		int setHour = alarmDate[0].get(Calendar.HOUR_OF_DAY);
		Log.d(TAG, "setHour: " + String.valueOf(setHour));
		
		int setMinute = alarmDate[0].get(Calendar.MINUTE);
		Log.d(TAG, "setMinutes: " + String.valueOf(setMinute));
		
		int setDay = alarmDate[0].get(Calendar.DAY_OF_YEAR);
		Log.d(TAG, "sleepDay: " + String.valueOf(setDay));
		
		// sleep time in milliseconds
		/*long sleep = alarmDate[0].getTimeInMillis();
		Log.d(TAG, "sleep in Millis: " + String.valueOf(sleep));*/
		
		// sleep time in mills
		millsDate[0] =  alarmDate[0].getTimeInMillis();
		Log.d(TAG, "wake in Millis: " + String.valueOf(millsDate[0]));
		
		SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
		String str=sdf.format(millsDate[0]);
		Log.d(TAG, "sleepAlarm timeMillis to time format: " + str);
		
		
		alarmDate[1].set(Calendar.DAY_OF_YEAR, alarmDate[1].get(Calendar.DAY_OF_YEAR));
		alarmDate[1].set(Calendar.HOUR_OF_DAY, wakeH);
		alarmDate[1].set(Calendar.MINUTE, wakeM);
		alarmDate[1].set(Calendar.MILLISECOND, 0);
		
		int wHour = alarmDate[1].get(Calendar.HOUR_OF_DAY);
		
		Log.d(TAG, "wakeHour: " + String.valueOf(wHour));
		
		
		
		int wMinute = alarmDate[1].get(Calendar.MINUTE);
		Log.d(TAG, "wakeMinutes: " + String.valueOf(wMinute));
		
		int wDay = alarmDate[1].get(Calendar.DAY_OF_YEAR);
		Log.d(TAG, "wakeDay: " + String.valueOf(wDay));
		
		// wake time in milliseconds
		/*long wake = alarmDate[1].getTimeInMillis();
		Log.d(TAG, "wake in Millis: " + String.valueOf(wake));*/
		
		//wake time in mills
		millsDate[1] =  alarmDate[1].getTimeInMillis();
		Log.d(TAG, "wake in Millis: " + String.valueOf(millsDate[1]));
		
		SimpleDateFormat sdfw= new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
		String strw=sdf.format(millsDate[1]);
		Log.d(TAG, "Wake Time timeMillis to time format: " + strw);
		
		//current time in milliseconds
		long current = System.currentTimeMillis();
		Log.d(TAG, "current in Millis: " + String.valueOf(current));
		SimpleDateFormat sdfc= new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
		String strc=sdfc.format(current);
		Log.d(TAG, "Current Time timeMillis to time format: " + strc);
		
		return millsDate;
		
	}


/**
 * Toggles the Sound depending on the profile's WiFi preference
 * @param status WiFi preference 
 * @param context Application context
 */

public static void volumeControl(int onOff, Context context){
	
	AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
	// switches to Silent mode
	if(onOff ==0 && (audio.getRingerMode() != AudioManager.RINGER_MODE_SILENT))
		audio.setRingerMode(AudioManager.RINGER_MODE_SILENT);
	else
	{
		if(onOff == 0 && (audio.getRingerMode() == AudioManager.RINGER_MODE_SILENT))
			audio.setRingerMode(AudioManager.RINGER_MODE_SILENT);
			
	}
			
}


/**
 * Toggles the WiFi depending on the profile's WiFi preference
 * @param status WiFi preference 
 * @param context Application context
 */
public static void toggleWiFi(int status, Context context) {
	
WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
Log.d(TAG, "Wifi status " + String.valueOf(wifiManager.getWifiState()));

if(status == 0 && wifiManager.isWifiEnabled())
{
	wifiManager.setWifiEnabled(false);
	Log.d(TAG, "WifiOff " + String.valueOf(wifiManager.getWifiState()));
}
	
}

/**
 * Toggles the WiFi depending on the profile's WiFi preference
 * @param status WiFi preference 
 * @param context Application context
 */
public static void toggleVibration(int status, Context context) {
	
AudioManager audio =  (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
Log.d(TAG, "Wifi status " + String.valueOf(audio.getRingerMode()));

if(status == 0 && audio.getRingerMode() == AudioManager.RINGER_MODE_SILENT)
{
	audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
	Log.d(TAG, "WifiOff " + String.valueOf(audio.getRingerMode()));
}
	
}


/**
 * This method sets the WiFi, Sound and Vibration according to the selected Profile's preferences
 * @param profile ProfileData object of the selected profile
 */
 public static void setResources(ProfileData profile, Context context){
	toggleWiFi(profile.getProfileWiFi(), context);
	volumeControl(profile.getProfileSound(), context);
	toggleVibration(profile.getProfileVibration(), context);
}
 /**
  *  Extracts the ProfileData object from an intent
  * @param intent Intent
  * @param intentCode String assosiated with the object set as extras in the Intent
  * @return ProfileData extracted from the intent
  */
 public static ProfileData getIntentData(Intent intent, String intentCode){
	 ProfileData newProfile = intent.getParcelableExtra(intentCode);
     if(newProfile == null)
     	Log.d(TAG, "intent data extracted are null");
     else
     	Log.d(TAG, newProfile.getProfileName());
     return newProfile;
}
 
 public static ProfileData getBundledData(Intent intent, String bundleCode, String dataCode){
	 
	 if(intent != null){
		  Bundle bundle = intent.getBundleExtra(bundleCode);
		  ProfileData alarmData = bundle.getParcelable(dataCode);
		  return alarmData;
	 } return null;
 }
}
