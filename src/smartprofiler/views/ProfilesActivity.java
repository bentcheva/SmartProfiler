package smartprofiler.views;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

//import com.example.smartprofiler.R;



























import com.example.smartprofiler.R;

import smartprofiler.common.GenericActivity;
import smartprofiler.common.LifecycleLoggingActivity;
import smartprofiler.model.AlarmReceiver;
import smartprofiler.model.ProfileAlarmManager;
import smartprofiler.presenter.AddNewProfile;
import smartprofiler.presenter.CreateDefaultProfiles;
import smartprofiler.presenter.DeleteProfileTask;
import smartprofiler.presenter.ProfileData;
import smartprofiler.presenter.ProfilesContract;
import smartprofiler.presenter.ProfilesLoader;
import smartprofiler.presenter.ProfilesManager;
import smartprofiler.presenter.ProfilesPreferences;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
/**
 * ProfilesActivity provides the main User Interface for the app
 * When first started it loads default profiles.
 * There are options to delete/ edit/add profiles
 * @author Bobi
 *
 */
public class ProfilesActivity extends LifecycleLoggingActivity {

	protected ListView mProfilesList;
	protected ProfilesAdapter mMyAdapter;
	private ProfilesManager mManager;
	private String mNewName = null ;
	private List<ProfileData> mProfiles;
	private PendingIntent mStartPendingIntent, mStopPendingIntent; 
	private ProfileAlarmManager mAlarmManager;
	
	
	static final String ACTION_ADD_PROFILE = 
			"com.example.smartprofiler.action.ACTION_ADD_PROFILE";
	
	static final int NEW_PROFILE_RESULT = 1;  // The request code
	
	
	/**
     * Used for Android debugging.
     */
    private final static String TAG = 
        ProfilesActivity.class.getName();
    
   
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profiles);
		
		//setting the Action bar
	
		Toolbar myToolbar = (Toolbar) findViewById(R.id.app_bar);
	    setSupportActionBar(myToolbar);
	    //setting the ListView
		mProfilesList = (ListView) findViewById(R.id.list);
		setProfiles(new ArrayList<ProfileData>());
		mMyAdapter = new ProfilesAdapter(this);
		mProfilesList.setAdapter(mMyAdapter);
		initDataBase();
		registerForContextMenu(mProfilesList);
		
		//Deactivates the current profile and activates the selected profile 
		mProfilesList.setOnItemClickListener(new OnItemClickListener( ) {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.d(TAG, mProfiles.get(position).getProfileName() + " profile selected.");
				Log.d(TAG, mProfiles.get(position).getProfileWiFi() + " WiFi.");
				Log.d(TAG, mProfiles.get(position).getProfileSound() + " Sound.");
				Log.d(TAG, mProfiles.get(position).getProfileVibration() + " Vibration.");
				if(mAlarmManager != null){
					//stop the Alarms
					mAlarmManager.deactivateAlarm();
					// freeing the old Alarm for  GC 
					mAlarmManager = null;
				}
				if(mProfiles.get(position).getProfileStartTime() == CreateDefaultProfiles.DEFAULT_START 
						|| mProfiles.get(position).getProfileStopTime() == CreateDefaultProfiles.DEFAULT_STOP){
					// the profile is not time window dependant
					Log.d(TAG, "setting Resources");
					Utils.Utils.setResources(mProfiles.get(position), ProfilesActivity.this);
					// TODO to deactivate any active alarms
				}
				else{
					mAlarmManager = new ProfileAlarmManager(getActivityContext(), makeIntent(mProfiles.get(position), 
																								getActivityContext(), AlarmReceiver.BUNDLE_CODE));
					mAlarmManager.activateAlarm();
					
				}
			}
		});
	}
	
	/**
	 * Factory method returning Intent
	 * @param profile ProfileData object bundled as Extras in the Intent
	 * @param context Activity's context
	 * @return Intent object or null
	 */
	private Intent makeIntent(ProfileData profile, Context context, String bundleCode){
		
		if(profile != null){
			Intent intent = new Intent(context, AlarmReceiver.class);
			Bundle mBundle = new Bundle(); 
			mBundle.putParcelable(ProfileAlarmManager.ALARM_DATA, profile); 
			return intent.putExtra(bundleCode, mBundle);
			
		}else
			return null;
	}

	
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d(TAG, "Profiles size " + String.valueOf(mProfiles.size()));
	}



	// if the db table doesn't exists, which is equivalent to first time running the app 
	//the method creates new table and loads 3 default profiles
	//if the table exists uses the records of the table to display the profiles in the ListView
	private void initDataBase()
	{
		mManager = new ProfilesManager(getApplicationContext());
		//mManager.delete();
		
		boolean tableExists = mManager.doesTableExist();
		boolean tableEmpty = mManager.isTableEmpty();
		Log.d(TAG, String.valueOf(tableExists));
		if(tableEmpty){
			Log.d(TAG, "dB table doesnt't exist.");
			new CreateDefaultProfiles(mManager).execute();
		}
		Log.d(TAG, "dB table does exist.");
		 
		new ProfilesLoader(mManager, this).execute();  
		
	}
	
	
	
	//called from a background thread
	@SuppressWarnings("unchecked")
	public void displayProfiles(List<ProfileData> data){
		 Log.d(TAG, "data = " + data);
	        if (data == null 
	            || data.size() == 0){
	        	Log.d(TAG, "data == null");
	        		Utils.Utils.showToast(getApplicationContext(), "error");
	        }
	        else {
	            Log.d(TAG,
	                  "displayProfiles() with number of profiles = "
	                  + data.size());

	            // Add the results to the Adapter and notify changes.
	            mMyAdapter.clear();
	            mMyAdapter.addAll(data);
	            List<String> names = Utils.Utils.namesToString(data);
	            Log.d(TAG, "names = " + names);
	            Log.d(TAG, "count = " + String.valueOf(mMyAdapter.getCount()));
	            mMyAdapter.notifyDataSetChanged();
	}
	}

	//Creates context menu when ListView item is long pressed
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
	 
	 //   menu.setHeaderTitle("Choose");
		
	    menu.add("Edit");
	    menu.add("Delete");
		
		
	}


	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        Log.d(TAG, "removing item pos=" + info.position);
		if(item.getTitle() == "Edit")
			Log.d(TAG, "Edit");
		else
			if(item.getTitle() == "Delete"){
				Log.d(TAG, "Delete");
				
				for(int i = 0; i < mMyAdapter.getCount(); i++)
					Log.d(TAG, mMyAdapter.getAll().get(i).getProfileName());
				new DeleteProfileTask(mManager, this).execute(mMyAdapter.getItem(info.position));
				mMyAdapter.removeItem(info.position);
			}
			else 
				return false;
		
		return true;
		
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stubgetMenuInflater().inflate(R.menu.profiles, menu);
		getMenuInflater().inflate(R.menu.profiles, menu);
		
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Toast.makeText(getApplicationContext(), "Settings pressed", Toast.LENGTH_LONG).show();
			return true;
		}
		if (id == R.id.action_add) {
			Intent intent = new Intent(this, AddProfileActivity.class);
			intent.setAction(Intent.ACTION_SEND);
			showDialogue();
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	/**
	 * This method prompts the User to enter name of the new profile via 
	 * DialogBox. Pressing "Done" initiates the start of AddProfileActivity
	 */
	private void showDialogue(){
		
		AlertDialog.Builder alert = new AlertDialog.Builder(this); 
		final EditText edittext = new EditText(this);
		
		alert.setMessage("Enter Profile Name");
		alert.setTitle("Add new profile");

		alert.setView(edittext);

		alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int whichButton) {
		       
		       mNewName = edittext.getText().toString();
		       edittext.setImeOptions(EditorInfo.IME_ACTION_DONE);
		       Intent addProfileIntent = new Intent(getApplicationContext(), AddProfileActivity.class);
				addProfileIntent.setAction(Intent.ACTION_SEND);
				addProfileIntent.putExtra("name", mNewName);
				startActivityForResult(addProfileIntent, NEW_PROFILE_RESULT);
		      
		      
		    }
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int whichButton) {
		        // what ever you want to do with No option.
		    	mNewName = null;
		    	dialog.cancel();
		    }
		});
		alert.show();
		
	}
	
	/**
	 * Callback method , called when the AddProfileActivity exits. 
	 * Returns the control to the caller activity - ProfilesActivity.
	 * Gets the new DataProfile object from the intent and starts an 
	 * AsyncTask to add the profile to the dB table
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		 // Check which request we're responding to
	    if (requestCode == NEW_PROFILE_RESULT) {
	        // Make sure the request was successful
	        if (resultCode == RESULT_OK) {
	        	
	         /*   ProfileData newProfile = data.getParcelableExtra(AddProfileActivity.PARCABLE_CODE);
	            if(newProfile == null)
	            	Log.d(TAG, "intent data extracted are null");
	            else
	            	Log.d(TAG, newProfile.getProfileName()); */
	        	
	            
	            new AddNewProfile(mManager, Utils.Utils.getIntentData(data, AddProfileActivity.PARCABLE_CODE), this).execute();
	            
	           
	        }
	    }

	}

	/**
	 * Getter method returns List with ProfileData objects
	 * @return List<ProfileData>
	 */
	public  List<ProfileData> getProfiles() {
		return this.mProfiles;
	}

	/**
	 * Setter method. Sets the member variable mProfiles.
	 * @param profiles List<ProfileData>
	 */
	public void setProfiles(List<ProfileData> profiles) {
		this.mProfiles = profiles;
	}


	}


	
	
	


