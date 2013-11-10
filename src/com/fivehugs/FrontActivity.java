package com.fivehugs;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;

import com.parse.ParseUser;


public class FrontActivity extends Activity {
	public static int d = 10;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_front);
		
		Config.context=this;
		
		checkGPS();
		
		LocationManager locationManager;
		Criteria criteria = new Criteria();
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		String provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);
		double lat = location.getLatitude();
		double lon = location.getLongitude();
		
		PostAdapter adapter = new PostAdapter(this);
		adapter.loadObjects();

		ListView listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(adapter);
		
		
	}

	public static class PrefsFragment extends PreferenceFragment {
		 
        @Override
        public void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);

                    // Load the preferences from an XML resource
                    addPreferencesFromResource(R.xml.preferences);
        }
}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.front, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.logout:
			logout();
			return true;
		case R.id.compose:
			compose();
			return true;
		case R.id.action_settings:
			Intent i = new Intent(this, FragmentPreferences.class);
			startActivity(i);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void logout() {
		ParseUser.logOut();
		startActivity(new Intent(this, MainActivity.class));
		finish();
	}

	private void compose() {
		startActivity(new Intent(this, ComposeActivity.class));
	}

	public void toggleHug(View view){
		CheckBox hug = (CheckBox)view.findViewById(R.id.hug);
		if(hug.isChecked()){
			hug.setButtonDrawable(getResources().getDrawable(R.drawable.sshug));
			
		}
		else{
			hug.setButtonDrawable(getResources().getDrawable(R.drawable.ssbhug));
		}
	}
	
	public void toggleFive(View view){
		CheckBox five = (CheckBox)view.findViewById(R.id.five);
		if(five.isChecked()){
			five.setButtonDrawable(getResources().getDrawable(R.drawable.ssfive));
			//(TextView)view.findViewById(objId);
		}
		else{
			five.setButtonDrawable(getResources().getDrawable(R.drawable.ssbfive));
		}
	}
	
	public void checkGPS(){
		LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
		boolean enabled = service
		  .isProviderEnabled(LocationManager.GPS_PROVIDER);
		
		if (!enabled) {
			  Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			  startActivity(intent);
			} 
	}
	
	public void fragMe(){
		FragmentManager mFragmentManager = getFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager
                                .beginTransaction();
        PrefsFragment mPrefsFragment = new PrefsFragment();
        mFragmentTransaction.add(android.R.id.content, mPrefsFragment);
        mFragmentTransaction.commit();
	}
	
}
