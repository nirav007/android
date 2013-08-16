package com.image;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	Bitmap bmp;
	private static LayoutInflater inflater = null;

	private ImageView[] mImages;
	String[] itemimage;

	String itemname;
	HashMap<String, String> map = new HashMap<String, String>();

	public ImageAdapter(Context context, JSONArray imageArrayJson) {
	    this.mImages = new ImageView[imageArrayJson.length()];
	    String qrimage;
	    try {

	        for (int i = 0; i < imageArrayJson.length(); i++) {
	            JSONObject image = imageArrayJson.getJSONObject(i);
	            qrimage = image.getString("itemimage");
	            itemname = image.getString("itemname");
	            map.put("itemname", image.getString("itemname"));
	            System.out.println(itemname);

	            byte[] qrimageBytes = Base64.decode(qrimage.getBytes(),0);

	            bmp = BitmapFactory.decodeByteArray(qrimageBytes, 0,
	                    qrimageBytes.length);
	            int width = 100;
	            int height = 100;
	      Bitmap resizedbitmap =                                          
	             Bitmap.createScaledBitmap(bmp,width,
	                    height, true);

	            Log.e("Image Height", " Height = " + bmp.getHeight()
	                    + " , Width = " + bmp.getWidth());

	            mImages[i] = new ImageView(context);
	            mImages[i].setImageBitmap(resizedbitmap);

	            mImages[i].setScaleType(ImageView.ScaleType.FIT_START);

	        }

	    } catch (Exception e) {
	        // TODO: handle exception
	    }
	}

	public int getCount() {
	    return mImages.length;
	}

	public Object getItem(int position) {
	    return position;
	}

	public long getItemId(int position) {
	    return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {


	    return mImages[position];

	}
	 }