package smartprofiler.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import Utils.Utils;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.util.Log;


public class AlarmReceiver extends BroadcastReceiver{
	
	
	//TAG for debuging
		 private final static String TAG = 
			        AlarmReceiver.class.getName();

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		int mode = intent.getExtras().getInt("value");
		boolean wifiOff = intent.getExtras().getBoolean("wifi");
		switch(mode)
		{
		case 1:
			Utils.showToast(context, "Sleep alarm went off");
			Log.d(TAG, "sleepAlarm fired at: " + String.valueOf(System.currentTimeMillis()));
			SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
			String str=sdf.format(System.currentTimeMillis());
			Log.d(TAG, "sleepAlarm fired at: " + str);
			toggleWiFi(wifiOff, context);
			volumeControl(true, context);
			break;
		case 2:	
			Utils.showToast(null, "Wake alarm went off");
			Log.d(TAG, "wakeAlarm firred at: " + String.valueOf(System.currentTimeMillis()));
			SimpleDateFormat sdfw= new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
			String strw=sdfw.format(System.currentTimeMillis());
			Log.d(TAG, "wakeAlarm fired at: " + strw);
			volumeControl(false, context);
			break;
		}
		
	}
	
	// turns off/on the volume
	
	public void volumeControl(boolean onOff, Context context){
		
		AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		// switches to Silent mode
		if(onOff == true && (audio.getRingerMode() != AudioManager.RINGER_MODE_SILENT))
			audio.setRingerMode(AudioManager.RINGER_MODE_SILENT);
		else
		{
			if(onOff == false && (audio.getRingerMode() == AudioManager.RINGER_MODE_SILENT))
				audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
				
		}
				
	}
	
	
	// turns off the Wifi if selected  when going to sleep 
	public void toggleWiFi(boolean status, Context context) {
		
	WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
	Log.d(TAG, "Wifi status " + String.valueOf(wifiManager.getWifiState()));
	
	if(status == true && wifiManager.isWifiEnabled())
	{
		wifiManager.setWifiEnabled(false);
		Log.d(TAG, "WifiOff " + String.valueOf(wifiManager.getWifiState()));
	}
		
	}

}
