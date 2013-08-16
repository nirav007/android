package com.image;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProjectsAdapter extends ArrayAdapter<Projects> {

    int resource;
    String response;
    Context context;
    //Initialize adapter
    public ProjectsAdapter(Context context, int resource, List<Projects> items) {
        super(context, resource, items);
        this.resource=resource;

    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        LinearLayout projectView;
        //Get the current alert object
        Projects pro = getItem(position);

        //Inflate the view
        if(convertView==null)
        {
            projectView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater)getContext().getSystemService(inflater);
            vi.inflate(resource, projectView, true);
        }
        else
        {
            projectView = (LinearLayout) convertView;
        }

        TextView Title =(TextView)projectView.findViewById(R.id.title);

        try {
              ImageView i = (ImageView)projectView.findViewById(R.id.image);
              Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(pro.smallImageUrl).getContent());
              i.setImageBitmap(bitmap); 
            } catch (MalformedURLException e) {
              e.printStackTrace();
            } catch (IOException e) {
              e.printStackTrace();
            }


        //Assign the appropriate data from our alert object above
        //Image.setImageDrawable(pro.smallImageUrl);
        Title.setText(pro.title);

        return projectView;
    }

}