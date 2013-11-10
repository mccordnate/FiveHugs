package com.fivehugs;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

public class PostAdapter extends ParseQueryAdapter<ParseObject> {
	public Context context;
	
	
	
	public PostAdapter(Context context) {
		super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery<ParseObject> create() {
                    
            		ParseGeoPoint point = getGPS();
            		
            		
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("post");
                    query.whereNear("location", point);
                    query.setLimit(10);
                    try {
						query.find();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    return query;
            }
    });
		this.context = context;
	}
	
	public PostAdapter(Context context, int d){
		super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery<ParseObject> create() {
                    
            		ParseGeoPoint point = getGPS();
            		
            		
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("post");
                    query.whereWithinMiles("location", point, FrontActivity.d);
                    query.setLimit(50);
                    try {
						query.find();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    return query;
            }
    });
		this.context = context;
	}

	@Override
	public View getItemView(ParseObject object, View convertView, ViewGroup parent){
		View vi = convertView;
		if(convertView==null) vi = View.inflate(getContext(), R.layout.post, null);
		
		super.getItemView(object, convertView, parent);
		
		TextView title = (TextView)vi.findViewById(R.id.title);
		TextView message = (TextView)vi.findViewById(R.id.message);
		TextView objId = (TextView)vi.findViewById(R.id.objId);
		
		
		ParseUser user = ParseUser.getCurrentUser();
		
		title.setText((String)user.get("firstName"));
		message.setText(object.getString("message"));
		objId.setText(object.getString("objectId"));

		
		
		return vi;
	}
	
	public static ParseGeoPoint getGPS(){
		LocationManager service = (LocationManager) Config.context.getSystemService(Context.LOCATION_SERVICE);
		boolean enabled = service
		  .isProviderEnabled(LocationManager.GPS_PROVIDER);
		
		if (!enabled) {
			  Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			  Config.context.startActivity(intent);
			} 
    	
		LocationManager locationManager;
		Criteria criteria = new Criteria();
		locationManager = (LocationManager) Config.context.getSystemService(Context.LOCATION_SERVICE);
		String provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);
		double lat = location.getLatitude();
		double lon = location.getLongitude();
		
		return new ParseGeoPoint(lat,lon);
	}
	
}
