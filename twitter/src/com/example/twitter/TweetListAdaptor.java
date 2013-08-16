package com.example.twitter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TweetListAdaptor extends ArrayAdapter<Tweet> {  
    private ArrayList<Tweet> tweets;  
    public TweetListAdaptor(Context context,  
                                int textViewResourceId,  
                                ArrayList<Tweet> items) {  
             super(context, textViewResourceId, items);  
             this.tweets = items;  
    }  
      
    public View getView(int position, View convertView, ViewGroup parent) {  
            View v = convertView;  
            if (v == null) {  
                    LayoutInflater vi = (LayoutInflater) getContext().getSystemService (Context.LAYOUT_INFLATER_SERVICE);  
                    v = vi.inflate(R.layout.list_item, null);  
            }  
            Tweet o = tweets.get(position);  
            TextView tt = (TextView) v.findViewById(R.id.toptext);  
            TextView bt = (TextView) v.findViewById(R.id.bottomtext);  
            tt.setText(o.content);  
            bt.setText(o.author);  
            return v;  
    }  
}  