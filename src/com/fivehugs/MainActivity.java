package com.fivehugs;

import java.util.Arrays;
import java.util.List;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Parse.initialize(this, "IMS4ulBv5P6ANtujuGvmAaeIPBugWYO3W7Hg4Lv9", "1ENx3d2wsqaShL1WtJpYmoCmfmcAQwSFGgUD3o08");
		ParseFacebookUtils.initialize("175236929349279");
		ParseAnalytics.trackAppOpened(getIntent());
		ParseUser currentUser = ParseUser.getCurrentUser();
	    if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
	        // Go to the user info activity
	      startActivity(new Intent(this, FrontActivity.class));
	      finish();
	    }
	    else{
	    setContentView(R.layout.activity_main);
	    }
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  super.onActivityResult(requestCode, resultCode, data);
	  ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
	}

	public void fbook(View view){
		List<String> permissions = Arrays.asList("basic_info", "user_about_me",
	            "user_relationships", "user_birthday", "user_location");
		ParseFacebookUtils.logIn(permissions, this, new LogInCallback() {
			  @Override
			  public void done(ParseUser user, ParseException err) {
			    if (user == null) {
			      Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
			    } else if (user.isNew()) {
			      Log.d("MyApp", "User signed up and logged in through Facebook!");
			      Request request = Request.newMeRequest(Session.getActiveSession(),
							new Request.GraphUserCallback() {

								@Override
								public void onCompleted(GraphUser user, Response response) {
									ParseUser pUser = ParseUser.getCurrentUser();
									pUser.put("firstName", user.getFirstName());
									pUser.put("lastName", user.getLastName());
									pUser.saveInBackground();
								}
							});
					request.executeAsync();
			      startActivity(new Intent(MainActivity.this, FrontActivity.class));
			      finish();
			    } else {
			      Log.d("MyApp", "User logged in through Facebook!");
			      startActivity(new Intent(MainActivity.this, FrontActivity.class));
			      finish();	
			    }
			  }
			});
	}
	
}
