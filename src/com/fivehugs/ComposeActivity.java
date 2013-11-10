package com.fivehugs;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

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
		
		Request request = Request.newMeRequest(Session.getActiveSession(),
				new Request.GraphUserCallback() {

					@Override
					public void onCompleted(GraphUser user, Response response) {
						TextView tv = (TextView) findViewById(R.id.hiddenName);
						tv.setText(user.getFirstName());
					}
				});
		request.executeAsync();
		String userFirstName = ((TextView)findViewById(R.id.hiddenName)).toString();
		
		EditText et = (EditText) findViewById(R.id.message);
		String message = et.getText().toString();
		ParseObject post = new ParseObject("post");
		
		ParseUser user = ParseUser.getCurrentUser();
		post.put("user", user);
		post.put("message", message);
		post.put("hugs", 0);
		post.put("hf", 0);
		post.put("first_name", userFirstName);
		post.saveInBackground();
	}

}
