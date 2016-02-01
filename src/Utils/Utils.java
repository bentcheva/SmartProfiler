package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import smartprofiler.presenter.ProfileData;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class Utils {

	
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

	
	public static void exportDatabse(String databaseName, Context context)
	   {
	     try {
	        File sd = Environment.getExternalStorageDirectory();
	        File data = Environment.getDataDirectory();

	        if (sd.canWrite()) {
	        	Log.d("Utils", "can write on SD");
	        	File myDB = context.getDatabasePath(databaseName); 
	        	
	        	Log.d("Utils", myDB.getCanonicalPath());
	            String currentDBPath = "//data//"+context.getPackageName()+"//databases//"+databaseName+".db";
	            String backupDBPath = "backupname.db";
	            File currentDB = new File(data, myDB.getAbsolutePath());
	            Log.d("UTILS", currentDBPath + " exist is " + currentDB.exists());
	            File backupDB = new File(sd, backupDBPath);

	            if (currentDB.exists()) {
	            	Log.d("Utils", "current dB exists");
	                FileChannel src = new FileInputStream(currentDB).getChannel();
	                FileChannel dst = new FileOutputStream(backupDB).getChannel();
	                dst.transferFrom(src, 0, src.size());
	                src.close();
	                dst.close();
	            }
	            else
	            	Log.d("Utils", "The dB file doesn't exist");
	        }
	    } catch (Exception e) {

	    }
	}
}
