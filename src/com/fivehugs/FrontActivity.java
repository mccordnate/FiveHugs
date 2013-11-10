package com.fivehugs;


import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class FrontActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_front);
		
		PostAdapter adapter = new PostAdapter(this);
		adapter.loadObjects();

		ListView listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(adapter);

		
		
		
		
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

}
