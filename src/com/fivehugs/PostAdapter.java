package com.fivehugs;

import java.util.Arrays;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

public class PostAdapter extends ParseQueryAdapter<ParseObject> {
	private final Context context;
	private ParseObject[] posts;
	
	public PostAdapter(Context context) {
		super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
			public ParseQuery<ParseObject> create() {
				// Here we can configure a ParseQuery to display
				// only top-rated meals.
				ParseQuery query = new ParseQuery("post");
				
				return query;
			}
		});
		this.context=context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View vi = convertView;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(convertView==null) vi = inflater.inflate(R.layout.post, null);
		
		TextView title = (TextView)vi.findViewById(R.id.title);
		TextView artist = (TextView)vi.findViewById(R.id.message);
		
		return vi;
	}
}
