package com.fivehugs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class ComposeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_post:
			post();
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void post(){
		
		EditText et = (EditText) findViewById(R.id.message);
		String message = et.getText().toString();
		ParseObject post = new ParseObject("post");
		
		
		LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
		boolean enabled = service
		  .isProviderEnabled(LocationManager.GPS_PROVIDER);
		
		if (!enabled) {
			  Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			  startActivity(intent);
		}
		
		LocationManager locationManager;
		Criteria criteria = new Criteria();
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		String provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);
		double lat = location.getLatitude();
		double lon = location.getLongitude();
		
		ParseGeoPoint point = new ParseGeoPoint(lat,lon);
		
		ParseUser user = ParseUser.getCurrentUser();
		post.put("user", user);
		post.put("message", message);
		post.put("hugs", 0);
		post.put("hf", 0);
		post.put("location", point);
		post.saveInBackground();
	}

}
