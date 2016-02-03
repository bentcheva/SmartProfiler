package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import smartprofiler.presenter.ProfileData;
import smartprofiler.views.AddProfileActivity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class Utils {

	
	//TAG for debuging
	 private final static String TAG = 
		        Utils.class.getName();
	
	public static void showToast(Context context, String msg){
		Toast.makeText(context,
                msg,
                Toast.LENGTH_SHORT).show();
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
	 * @return array of millis, where the element in 0 index is start time and the element in 1st index is the stop time
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
}
