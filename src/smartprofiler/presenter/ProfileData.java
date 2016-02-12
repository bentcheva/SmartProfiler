package smartprofiler.presenter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A simple POJO that stores information about a Profile.
 * @author Bobi
 *
 */

public class ProfileData implements Parcelable{
	
	public ProfileData(String profileName, int status, int wifi, int sound,
			int mobile_data, int vibration, int start, int stop, String[] days) {
		this.profileName = profileName;
		this.profileStatus = status;
		this.profileWiFi = wifi;
		this.profileSound = sound;
		this.profileMobileData = mobile_data;
		this.profileVibration = vibration;
		this.profileStartTime = start;
		this.profileStopTime = stop;
		this.profileWeekday = days;
	}
	public ProfileData() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 * @return returns as String the name of the Profile
	 */
	public String getProfileName() {
		return profileName;
	}
	/**
	 *
	 * @param profileName sets the name of the Profile.
	 */
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	/**
	 * @return the profile status 
	 */
	public int getProfileStatus() {
		return profileStatus;
	}
	/**
	 * @param profileStatus sets the profile status
	 */
	public void setProfileStatus(int profileStatus) {
		this.profileStatus = profileStatus;
	}
	/**
	 * @return the Profile wifi status
	 */
	public int getProfileWiFi() {
		return profileWiFi;
	}
	/**
	 * @param profileWiFi sets the Profile status.
	 */
	public void setProfileWiFi(int profileWiFi) {
		this.profileWiFi = profileWiFi;
	}
	/**
	 * @return the Profile sound status.
	 */
	public int getProfileSound() {
		return profileSound;
	}
	/**
	 * @param profileSound set the Profile sound status.
	 */
	public void setProfileSound(int profileSound) {
		this.profileSound = profileSound;
	}
	/**
	 * @return the Profile mobile data status.
	 */
	public int getProfileMobileData() {
		return profileMobileData;
	}
	/**
	 * @param profileMobileData sets the Profile's mobile data status.
	 */
	public void setProfileMobileData(int profileMobileData) {
		this.profileMobileData = profileMobileData;
	}
	/**
	 * @return the Profile's vibration status.
	 */
	public int getProfileVibration() {
		return profileVibration;
	}
	/**
	 * @param profileVibration sets the Profile's vibration status.
	 */
	public void setProfileVibration(int profileVibration) {
		this.profileVibration = profileVibration;
	}
	/**
	 * 
	 * @return returns the Profile's activation time.
	 */
	public int getProfileStartTime() {
		return profileStartTime;
	}
	/**
	 * 
	 * @param profileStartTime sets the Profile's activation time.
	 */
	public void setProfileStartTime(int profileStartTime) {
		this.profileStartTime = profileStartTime;
	}
	/**
	 * 
	 * @return the Profile's deactivation time.
	 */
	public int getProfileStopTime() {
		return profileStopTime;
	}
	/**
	 * 
	 * @param profileStopTime sets the Profile's deactivation time.
	 */
	public void setProfileStopTime(int profileStopTime) {
		this.profileStopTime = profileStopTime;
	}
	/**
	 * 
	 * @return array containing days of the week for which the Profile is active
	 */
	public String[] getProfileWeekday() {
		return profileWeekday;
	}
	/**
	 * 
	 * @param profileWeekday sets array with days of the week for which the profile is not active.
	 */
	public void setProfileWeekday(String[] profileWeekday) {
		this.profileWeekday = profileWeekday;
	}
	/**
	 * 
	 * @param id sets Profile's id in the db table.
	 */
	public void setProfileId(long id)
	{
		this._id = id;
	}
	
	/**
	 * 
	 * @return gets theProfile's Id in the db table.
	 */
	public long getProfileId(){
		return _id;
	}
	/**
	 * Id of the profile.
	 */
	private long  _id;
	/**
	 * Name of the Profile.
	 */
	private String profileName;
	/**
	 * Status of the profile. Active/true or NotActive/false
	 */
	private int profileStatus;
	/**
	 * WiFi status of the profile.
	 * true if Wifi is on or false if Wifi is off.
	 */
	private int profileWiFi;
	/**
	 * Sound status of the profile.
	 * true if Sound is on or false if sound is off
	 */
	private int profileSound;
	/**
	 * Status of the MobileData
	 * true if mobile data is on or false if mobile data is off.
	 */
	private int profileMobileData;
	/**
	 * Vibration status of the profile.
	 * true if the Vibration is on or false if the vibration is off.
	 */
	private int profileVibration;
	/**
	 * Indicates the time when the Profile is automatically activated.
	 */
	private int profileStartTime;
	/**
	 * Indicates the time when the Profile is automatically disactivated.
	 */
	private int profileStopTime;
	/**
	 * Array containing days of the week for which the profile is applied.
	 */
	private String[] profileWeekday;
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(profileName);
		dest.writeInt(profileStatus);
		dest.writeInt(profileWiFi);
		dest.writeInt(profileSound);
		dest.writeInt(profileMobileData);
		dest.writeInt(profileVibration);
		dest.writeInt(profileStartTime);
		dest.writeInt(profileStopTime);
		dest.writeArray(null);
		
	}
	
	 private void readFromParcel(Parcel in) {   
	        profileName = in.readString();
	        profileStatus = in.readInt(); 
	        profileWiFi = in.readInt(); 
	        profileSound = in.readInt(); 
	        profileMobileData = in.readInt(); 
	        profileVibration = in.readInt(); 
	        profileStartTime = in.readInt();
	        profileStopTime = in.readInt();
	        profileWeekday = null;
	    } 
	 
	    public ProfileData(Parcel in){
	        readFromParcel(in);
	    }
	    
	    public static final Parcelable.Creator<ProfileData> CREATOR = new Parcelable.Creator<ProfileData>() {
	    	 
	        @Override
	        public ProfileData createFromParcel(Parcel source) {
	            return new ProfileData(source);
	        }
	 
	        @Override
	        public ProfileData[] newArray(int size) {
	            return new ProfileData[size];
	        }
	    };
	

}
