package smartprofiler.model;

import java.util.concurrent.TimeUnit;
import smartprofiler.views.AddProfileActivity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ProfileAlarmManager  {
	
	private Context mContext;
	private PendingIntent mPendingStart;
	private PendingIntent mPendingStop;
	private Intent mIntent;
	private AlarmManager mAlarmManager;
	
	
    public static final String ALARM_DATA = "alarm profile";
	
	public static final int START_PENDING_INTENT_NUMBER = 1000;
	public static final int STOP_PENDING_INTENT_NUMBER = 2000;
	
	public static final int START_ALARM = 100;
	public static final int STOP_ALARM = 101;
	public static final String ALARM_STATUS = "alarm_status";
	
	/**
     * Used for Android debugging.
     */
    private final static String TAG = 
        AddProfileActivity.class.getName();
	

	/**
	 * Constructor 
	 */
		public ProfileAlarmManager(Context context, Intent intent){
		
			mContext = context;
			mIntent = intent;
			mPendingStart = makePendingIntent(mIntent.putExtra(ALARM_STATUS, START_ALARM), mContext, START_PENDING_INTENT_NUMBER);
			mPendingStop = makePendingIntent(mIntent.putExtra(ALARM_STATUS, STOP_ALARM), mContext, STOP_PENDING_INTENT_NUMBER);
			mAlarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
			
	}

		/**
		 * Factory method creating Pending Intent for the Alarm manager
		 * @param intent 
		 * @param context
		 * @param number
		 * @return PendingIntent object
		 */
		private PendingIntent makePendingIntent(Intent intent, Context context, int number){
			return PendingIntent.getBroadcast(context, number, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		}
		
		
	
	/**
	 * This method gets the ProfileData object from the Intent. Extracts the start and stop time, 
	 * converts them in millis format and starts the start and stop Alarms.
	 */
	
	public void activateAlarm(){
				//getting start and stop time from the intent
		if (mIntent != null)
		{
			Log.d(TAG, "Activating the Alarm");
			int startTime = Utils.Utils.getBundledData(mIntent, AlarmReceiver.BUNDLE_CODE, ALARM_DATA).getProfileStartTime();
			int stopTime = Utils.Utils.getBundledData(mIntent, AlarmReceiver.BUNDLE_CODE, ALARM_DATA).getProfileStopTime();
			Log.d(TAG, "startTime = " + String.valueOf(startTime));
			Log.d(TAG, "stopTime = " + String.valueOf(stopTime));
			//converting back the time in minutes back to hours and minutes
			int[] convertedStartTime = Utils.Utils.minutesToTime(startTime);
			int[] convertedStopTime = Utils.Utils.minutesToTime(stopTime);
			//converting the H:M format time into millis
			long[] millisTimes = Utils.Utils.setTimeInMillis(convertedStartTime[0], convertedStopTime[0], 
															 convertedStartTime[1], convertedStopTime[1]);
			
		
			// setting the repeating alarms for sleep and wake with period of repetition 24 hours
			mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP,millisTimes[0],TimeUnit.HOURS.toMillis(24),mPendingStart);
			mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP,millisTimes[1], TimeUnit.HOURS.toMillis(24), mPendingStop);
			
			
		}
	}
	
	/**
	 * Cancels the alarms
	 */
	public void  deactivateAlarm(){
		mAlarmManager.cancel(mPendingStart);
		mAlarmManager.cancel(mPendingStop);
		Log.d(TAG, "Alarms canceled");
	}
	
	
	
	

}
