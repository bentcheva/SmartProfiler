package smartprofiler.views;

import java.io.Serializable;
import java.util.ArrayList;

import com.example.smartprofiler.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import smartprofiler.common.LifecycleLoggingActivity;
import smartprofiler.common.PropertyItem;
import smartprofiler.presenter.AddNewProfile;
import smartprofiler.presenter.ProfileData;
import smartprofiler.presenter.ProfilesManager;

public class AddProfileActivity extends LifecycleLoggingActivity {
	
	private AddDataAdapter mNewProfileAdapter;
	private EditText mName;
	private ListView mPropertiesList;
	private String[] mProfileProperties = {"WiFi", "Sound", "Mobile Data",
			"Vibration", "Start time", "Stop time"};
	private ArrayList<PropertyItem> mPropertyItemList;
	private ProfilesManager mManager;
	
	
	
	
	/**
     * Used for Android debugging.
     */
    private final static String TAG = 
        AddProfileActivity.class.getName();
	public static final String PARCABLE_CODE = "profile";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_profile);
		
		mPropertiesList = (ListView) findViewById(R.id.list2);
		mPropertyItemList = makePropertyData(mProfileProperties);
		mNewProfileAdapter = new AddDataAdapter(this,mPropertyItemList);
		mPropertiesList.setAdapter(mNewProfileAdapter);
		
		mPropertiesList.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		
		mPropertiesList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.d(TAG, "OnItemClick");
				PropertyItem prop = (PropertyItem) parent.getAdapter().getItem(position);
				String propN = prop.getName();
				Log.d(TAG, propN);
			}
		});
	}
	
	private ArrayList<PropertyItem> makePropertyData(String[] names){
		ArrayList<PropertyItem> propertiesList = new ArrayList<PropertyItem>();
		for(String str: names){
			propertiesList.add(new PropertyItem(str, 0));
		}
		return propertiesList;
	}
	
	
	
	
	/**
	 * Called when Cancel button is pressed
	 * Kills the AddProfileActivity and returns back to the  main activity  ProfilesActivity
	 * @param view
	 */
	public void cancelPressed(View view){
		//preparing the object mProperiesList for GC
		mPropertiesList = null;
		finish();
	}
	/**
	 * Called when OK button is pressed
	 * @param view
	 */
	public void okPressed(View view){
		
		
		
		ArrayList<PropertyItem> propertyList = new ArrayList<PropertyItem>();
	
		for(int i =0; i < mPropertiesList.getAdapter().getCount(); i++ ){
			propertyList.add((PropertyItem) mPropertiesList.getAdapter().getItem(i));
			Log.d(TAG, propertyList.get(i).getName());
			Log.d(TAG, String.valueOf(propertyList.get(i).getStatus()));
		}
		Log.d(TAG, "name of new profile " +  getIntent().getExtras().getString("name"));
		ProfileData newProfile = new ProfileData(getIntent().getExtras().getString("name"),
				0,
				propertyList.get(0).getStatus(),
				propertyList.get(1).getStatus(),
				propertyList.get(2).getStatus(),
				propertyList.get(3).getStatus(),
				propertyList.get(4).getStatus(),
				propertyList.get(1).getStatus(),
				null);
	
		Intent resultIntent = new Intent();
		Bundle mBundle = new Bundle(); 
		mBundle.putParcelable(PARCABLE_CODE, newProfile); 
		
		resultIntent.putExtras(mBundle);
		
		setResult(Activity.RESULT_OK, resultIntent);
		finish();
	}

}
