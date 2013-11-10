package com.fivehugs;

import com.parse.ParseObject;
import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

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
		post.put("user", ParseUser.getCurrentUser());
		post.put("message", message);
		post.put("hugs", 0);
		post.put("hf", 0);
		post.saveInBackground();
	}

}