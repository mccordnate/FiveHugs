package com.fivehugs;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseException;

public class PostAdapter extends ParseQueryAdapter<ParseObject> {
	
	public PostAdapter(Context context) {
		super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery<ParseObject> create() {
                    // Here we can configure a ParseQuery to display
                    // only top-rated meals.
                    ParseQuery query = new ParseQuery("post");
                    return query;
            }
    });
		
	}

	@Override
	public View getItemView(ParseObject object, View convertView, ViewGroup parent){
		View vi = convertView;
		if(convertView==null) vi = View.inflate(getContext(), R.layout.post, null);
		
		super.getItemView(object, convertView, parent);
		
		TextView title = (TextView)vi.findViewById(R.id.title);
		TextView message = (TextView)vi.findViewById(R.id.message);
		
		
		title.setText("Name here");
		message.setText(object.getString("message"));
		
		
		return vi;
	}
}
