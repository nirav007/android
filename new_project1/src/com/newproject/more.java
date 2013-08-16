package com.newproject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.newproject.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class more extends Activity {

	private InputStream is;
	private StringBuilder sb;
	private String result;
	private JSONArray jArray;
	private TextView clove;
	
	 Integer count,user_id;
	 LinearLayout vliLayout;
	 LinearLayout linearLayout;
	 TextView more ;
	ArrayList<String> ar = new ArrayList<String>();
	ArrayList<String> arr = new ArrayList<String>();
	
	LinearLayout liLayout;
	DataHendleCls obS = new DataHendleCls();

	private String c_love,c_hate,c_like;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.you);
		
		init();
		
		selectitem();
	}

	
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		selectitem();
		super.onPostCreate(savedInstanceState);
	}

	private void init() {
		// TODO Auto-generated method stub
		
		//list_you = (ListView) findViewById(R.id.listone);
		SharedPreferences sp = this.getSharedPreferences("data", MODE_WORLD_READABLE);
		user_id = sp.getInt("user_id",0);
		
	}
	
	private void selectitem()
	{
		 try
		 {
	        	HttpClient httpsClient = new DefaultHttpClient();
	        	HttpPost httpPost = new HttpPost(obS.moreitemUrl);
	        	List<NameValuePair> nameValue = new ArrayList<NameValuePair>();
	        	nameValue.add(new BasicNameValuePair("user_id"," "+ user_id));
	        	httpPost.setEntity(new UrlEncodedFormEntity(nameValue));
	        	HttpResponse httpResponce = httpsClient.execute(httpPost);
	        	HttpEntity httpEntity = httpResponce.getEntity();
	        	is = httpEntity.getContent();       	
	        	
	        }
	        catch (Exception e) {		}
	        
	        try
	        {
	        	BufferedReader BufR = new BufferedReader(new InputStreamReader(is, "iso-8859-1"),8);
	        	sb = new StringBuilder();
	        	sb.append(BufR.readLine() + "\n");
	        	String line = "0";
	        	
	        	while((line = BufR.readLine()) != null)
	        	{
	        		sb.append(line + "\n");
	        	}
	        	
	        	is.close();
	        	result = sb.toString();
	        }
	        catch (Exception e) {
				}
	        
	        String owner_name;
	        String user_name;
	        Integer id;
	        
	        linearLayout  =  (LinearLayout) findViewById(R.id.layout_comment);
	       
	        linearLayout.removeAllViews();
	        
	        
	        
	        
	        try{
        		JSONObject json_data = null;
        	json_data = new JSONObject(result);
        	
        	String data = json_data.getString("Data");
        	JSONArray jArray1= new JSONArray(data);
        	JSONObject json_data1 = null;
        	json_data1 = jArray1.getJSONObject(0);
        	
        	
    		//Toast.makeText(getApplicationContext(), user_name, Toast.LENGTH_LONG).show();
        	for(int i = 0 ; i < jArray1.length(); i++)
        	{
        		json_data = jArray1.getJSONObject(i);
        		id=json_data.getInt("id");
        		owner_name = json_data.getString("item");
        		user_name = json_data.getString("image_name");
        		ar.add("" + owner_name );
        		arr.add("" + user_name );
        		count= linearLayout .getChildCount();
        		adddata(id,owner_name,user_name);
        	}
       
        		
            }
	        catch (JSONException e) {
			}
	        catch (Exception e) {
	        	
			}
	}
	    
	  public void adddata(final Integer id,final String ownString,final String uString)
	    {
	    //	Toast.makeText(getApplicationContext(),"dsf"+ count1, Toast.LENGTH_LONG).show();
		  final LinearLayout	liLayout ;
	 		
	    	liLayout = new LinearLayout(this);
			liLayout.setOrientation(0);
			liLayout.setId(id);
			
			liLayout.setPadding(10, 10, 10, 10);
			
			liLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.list_item));
			
			vliLayout = new LinearLayout(this);
			vliLayout.setOrientation(0);
			vliLayout.setId(id);
			vliLayout.setPadding(10, 10, 10, 5);
			vliLayout.setWeightSum(3);
		//	vliLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.list_item));
		//	vliLayout.setBackgroundResource(R.drawable.list_item);
			
			
			
			
			final TextView valueTV  = new TextView(this);
			 final TextView more ;
			 more = new TextView(this);
	
			final View view = new View(this);
		
			ImageView img_remove = new ImageView(this);
			
			//img_remove.setBackgroundDrawable(getResources().getDrawable(R.drawable.imgborder));
			//Toast.makeText(getApplicationContext(), arr.get(i), Toast.LENGTH_LONG).show();
			
			img_remove.setImageResource(R.drawable.removebutton);
			
			LinearLayout.LayoutParams para1=new LinearLayout.LayoutParams(
				    30,30);
			para1.gravity = Gravity.LEFT;
			
			img_remove.setLayoutParams(para1);
			
		
			ImageView imageView = new ImageView(this);
			
			imageView.setBackgroundDrawable(getResources().getDrawable(R.drawable.imgbgdesign));
			//Toast.makeText(getApplicationContext(), arr.get(i), Toast.LENGTH_LONG).show();
			
			imageView.setImageBitmap(getImageBitmap(obS.ImageString+uString));
		
			LinearLayout.LayoutParams para=new LinearLayout.LayoutParams(
				    75,75 );
			para.gravity = Gravity.LEFT;
			
			imageView.setLayoutParams(para);
			view.setLayoutParams(new LayoutParams(
	                LayoutParams.FILL_PARENT,1));
			
			
		  
	       
	        valueTV.setId(id);
	        valueTV.setPadding(10, 5, 0, 0);
	        valueTV.setTextColor(Color.rgb(135,30,45));
	        valueTV.setText(Html.fromHtml(ownString));
	        valueTV.setTextSize(20);
	        valueTV.setTypeface(null, Typeface.BOLD);
	        valueTV.setGravity(Gravity.CENTER_HORIZONTAL);
	       
	    
	         
	        more.setText("More");
	        more.setId(id);
	      //  more.setPadding(10, 50, 10, 0);
	        more.setTextColor(Color.RED);
	        more.setTextColor(Color.rgb(69,128,139));
	        more.setTextSize(14);
	       // more.setTypeface(null, Typeface.BOLD);
	        more.setGravity(Gravity.RIGHT);
	        more.setVisibility(1);
	        more.setLayoutParams(new LayoutParams(
	                LayoutParams.FILL_PARENT,
	                LayoutParams.WRAP_CONTENT));
	        
	        valueTV.setLayoutParams(new LayoutParams(
	                LayoutParams.WRAP_CONTENT,
	                LayoutParams.WRAP_CONTENT));
	
	      
	        more.setOnClickListener(new OnClickListener() {
				
				
				public void onClick(View v) {
				
					Toast.makeText(getApplicationContext(),ownString , Toast.LENGTH_LONG).show();
					
				}
			});

			final ImageView f_imageView_n = new ImageView(this);
			f_imageView_n.setBackgroundDrawable(getResources().getDrawable(R.drawable.imgborder));
			f_imageView_n.setLayoutParams(new LinearLayout.LayoutParams(65,65));
			f_imageView_n.setPadding(5, 8, 0, 0);
			FrameLayout.LayoutParams f_para1=new FrameLayout.LayoutParams(
				    90,70 );
			//f_para2.gravity = Gravity.LEFT;
			//f_para1.setMargins(15,20,0,0);
			
	
			f_imageView_n.setLayoutParams(f_para1);
			
			
			
			
			f_imageView_n.setImageBitmap(getImageBitmap(obS.ImageString+uString));
			
			ImageView f_imageView_remove = new ImageView(this);
				
			FrameLayout.LayoutParams f_para2=new FrameLayout.LayoutParams(
				    25,25 );
			//f_para2.gravity = Gravity.LEFT;
			f_para2.setMargins(0,0,0,0);
			
	
			f_imageView_remove.setLayoutParams(f_para2);
			
			f_imageView_remove.setBackgroundResource(R.drawable.removebutton);
			
		
			
			
			final FrameLayout f_vliLayout2 = new FrameLayout(this);
			//vliLayout2.setOrientation(0);
			f_vliLayout2.setId(id);
			//vliLayout2.setPadding(10,2,10,10);
			FrameLayout.LayoutParams para11=new FrameLayout.LayoutParams(
				    90,70);
			para11.gravity = Gravity.LEFT;
			//f_vliLayout2.setLayoutParams(para11);
			//f_vliLayout2.setBackgroundDrawable(getResources().getDrawable(R.drawable.imgbgdesign));
			//f_vliLayout2.setLayoutParams(new LinearLayout.LayoutParams(30,30));
			
			((FrameLayout) f_vliLayout2).addView(f_imageView_n);
			((FrameLayout) f_vliLayout2).addView(f_imageView_remove);
			
			 //((LinearLayout) vliLayout2).addView(d);
			 
			//((LinearLayout) linearLayout).addView(f_vliLayout2);
	        
	        ((LinearLayout) liLayout).addView(f_vliLayout2,0);
	        //((LinearLayout) liLayout).addView(imageView,1);
	        ((LinearLayout) liLayout).addView(valueTV,1);
	        ((LinearLayout) liLayout).addView(more,2);
	        
	        ((LinearLayout) liLayout).addView(view);
	        liLayout.setVisibility(View.VISIBLE);
	        
	        ((LinearLayout) linearLayout).addView(liLayout);
	
	        
img_remove.setOnClickListener(new OnClickListener() {
				
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
				
					
					liLayout.removeAllViews();
			    	liLayout.removeAllViewsInLayout();
					
					adddata(id, ownString, uString);
					Toast.makeText(getApplicationContext(), "fdg", Toast.LENGTH_LONG).show();
					hate(id);
				}
			});
			
	        
	        TextView valueTV_n  = new TextView(this);
	        
	        valueTV_n.setText(Html.fromHtml(ownString));
	        valueTV_n.setId(id);
	        valueTV_n.setPadding(0, 0, 10, 0);
	        valueTV_n.setTextColor(Color.rgb(135,30,45));
	        valueTV_n.setTextSize(30);
	        valueTV_n.setTypeface(null, Typeface.BOLD);
	        valueTV_n.setGravity(Gravity.CENTER_HORIZONTAL);
	        valueTV_n.setPadding(15, 5, 5, 5);
	    
	        valueTV_n.setLayoutParams(new LayoutParams(
	                LayoutParams.WRAP_CONTENT,
	                LayoutParams.WRAP_CONTENT));
	        
	        final TextView less  = new TextView(this);
	        
	    	less.setText("Less");
	    	less.setId(id);
	    
	    	less.setTextColor(Color.RED);
	    	less.setTextSize(14);
	    	less.setTextColor(Color.rgb(69,128,139));
	    	
	    	 less.setGravity(Gravity.RIGHT);
	    	 less.setLayoutParams(new LayoutParams(
		                LayoutParams.FILL_PARENT,
		                LayoutParams.WRAP_CONTENT));
		
			
			
	        
	       final LinearLayout vliLayout1 = new LinearLayout(this);
			vliLayout1.setOrientation(0);
			vliLayout1.setId(id);
			vliLayout1.setPadding(10, 0, 10, 10);
			vliLayout1.setWeightSum(2);
			((LinearLayout) vliLayout1).addView(valueTV_n,0);
			 ((LinearLayout) vliLayout1).addView(less,1);
			 
			 
			((LinearLayout) linearLayout).addView(vliLayout1);
			
			vliLayout1.setVisibility(View.GONE);
	     
			
		
			
			
			ImageView imageView_n = new ImageView(this);
			imageView_n.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT));
		
			imageView_n.setImageBitmap(getImageBitmap(obS.ImageString+uString));
			
			ImageView imageView_remove = new ImageView(this);
			imageView_remove.setLayoutParams(new LinearLayout.LayoutParams(25,25));
			imageView_remove.setPadding(5,5,5,5);
			
			FrameLayout.LayoutParams para2=new FrameLayout.LayoutParams(
				    30,30);
			para2.gravity = Gravity.LEFT;
			para2.setMargins(5,5,5,5);
		
			imageView_remove.setLayoutParams(para2);
			
			imageView_remove.setBackgroundResource(R.drawable.removebutton);
			
			ImageView imageView_remind = new ImageView(this);
			imageView_remind.setLayoutParams(new LinearLayout.LayoutParams(25,25));
			imageView_remind.setPadding(5, 5, 5, 5);
			
			
			FrameLayout.LayoutParams para3=new FrameLayout.LayoutParams(
				    30,30 );
			para3.gravity=Gravity.RIGHT;
			para3.setMargins(5, 5, 5, 5);
			
			imageView_remind.setLayoutParams(para3);
			
			imageView_remind.setBackgroundResource(R.drawable.reminderbutton);
			
			
			final FrameLayout vliLayout2 = new FrameLayout(this);
	
			vliLayout2.setId(id);
			vliLayout2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT));
			
			((FrameLayout) vliLayout2).addView(imageView_n);
			((FrameLayout) vliLayout2).addView(imageView_remove);
			((FrameLayout) vliLayout2).addView(imageView_remind);
			 //((LinearLayout) vliLayout2).addView(d);
			 
			((LinearLayout) linearLayout).addView(vliLayout2);
			
			vliLayout2.setVisibility(View.GONE);
			
			
			final LinearLayout vliLayout3 = new LinearLayout(this);
			vliLayout3.setOrientation(0);
			vliLayout3.setId(id);
			vliLayout3.setPadding(10,5,10,2);
		//	vliLayout3.setBackgroundDrawable(getResources().getDrawable(R.drawable.imgbgdesign));
			vliLayout3.setGravity(Gravity.RIGHT);
			
			
			ImageView love = new ImageView(this);
			love.setId(id);
			love.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
			love.setBackgroundDrawable(getResources().getDrawable(R.drawable.lovebutton));
			

			LinearLayout.LayoutParams para_love=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
		
			para_love.setMargins(0, 3, 0, 0);
			
			love.setLayoutParams(para_love);
			
	        final TextView clove  = new TextView(this);
	        
	    	//less.setText("Less");
	        clove.setId(id);
	    	//less.setPadding(10, 10, 10, 10);
	        clove.setTextColor(Color.RED);
	        clove.setTextSize(12);
	        
	        clove.setTypeface(null, Typeface.BOLD);
	    	clove.setText(count(id)); 
			
			
			love.setOnClickListener(new OnClickListener() {
				
				
				public void onClick(View v) {
			
					love(id);
					count(id);
				
			
				}
			});
			
			
			((LinearLayout) vliLayout3).addView(love);
		
			
			
			ImageView like = new ImageView(this);
			like.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
			like.setBackgroundDrawable(getResources().getDrawable(R.drawable.likebutton));
			
			   final TextView clike  = new TextView(this);
		        
		    	
			   clike.setId(id);
		    	
			   clike.setTextColor(Color.RED);
			   clike.setTextSize(12);
		        
			   clike.setTypeface(null, Typeface.BOLD);
			   clike.setText(count_like(id)); 
			
			like.setOnClickListener(new OnClickListener() {
				
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					like(id);
					count_like(id);
					
				}
			});
			
			((LinearLayout) vliLayout3).addView(like);
		
			
			ImageView votedown = new ImageView(this);
			votedown.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
			votedown.setBackgroundDrawable(getResources().getDrawable(R.drawable.votedownbutton));
			
			
			 
	        final TextView chate  = new TextView(this);
	        
	    	//less.setText("Less");
	        chate.setId(id);
	    	//less.setPadding(10, 10, 10, 10);
	        chate.setTextColor(Color.RED);
	        chate.setTextSize(12);
	        
	        chate.setTypeface(null, Typeface.BOLD);
	        chate.setText(count_hate(id)); 
			
			((LinearLayout) vliLayout3).addView(votedown);
			//((LinearLayout) vliLayout3).addView(chate);
			 
			((LinearLayout) linearLayout).addView(vliLayout3);
			
			
			
			votedown.setOnClickListener(new OnClickListener() {
				
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
				
					hate(id);
					count_hate(id);
				}
			});
			
			vliLayout3.setVisibility(View.GONE);
			
			
			final LinearLayout vliLayout4 = new LinearLayout(this);
			vliLayout4.setOrientation(1);
			vliLayout4.setId(id);
			vliLayout4.setPadding(10,5,10,2);
			//vliLayout4.setBackgroundDrawable(getResources().getDrawable(R.drawable.imgbgdesign));
			vliLayout4.setGravity(Gravity.LEFT);
			
			
			  TextView txt_heading  = new TextView(this);
		        
			  txt_heading.setText("5 Thing to Know");
			  txt_heading.setId(id);
			  txt_heading.setPadding(0, 0, 5, 0);
			  txt_heading.setTextColor(Color.rgb(59,35,20));
			  txt_heading.setTextSize(20);
			  txt_heading.setTypeface(null, Typeface.BOLD);
			  txt_heading.setGravity(Gravity.CENTER_HORIZONTAL);
			  txt_heading.setPadding(15, 5, 5, 5);
		        //valueTV.setTextAppearance(getApplicationContext(), R.style.commentstyle);
			  txt_heading.setLayoutParams(new LayoutParams(
		                LayoutParams.WRAP_CONTENT,
		                LayoutParams.WRAP_CONTENT));
			  
			  
			  TextView txt_content  = new TextView(this);
		        
			  txt_content.setText("1. Lorem ipsum dolor sit amet, consectetur adipiscing elit.Etiam vitae enim id arcu vehicula mollias." +
			  		"\n2. Phasellus viverra imperdiet ipsum, et vestibulum" +
			  		"\n3. leo elementum et. Donec quis dolor vel dolor" +
			  		"\n4. placerat auctor consequat sed metus. Morbi" +
			  		"\n5. tincidunt, velit interdum mollis faucibus, dui mangna faucibus.");
			  txt_content.setId(id);
			  txt_content.setPadding(0, 0, 5, 0);
			  txt_content.setTextColor(Color.rgb(59,35,20));
			  txt_content.setTextSize(13);
			  //txt_content.setTypeface(null, Typeface.BOLD);
			  txt_content.setGravity(Gravity.LEFT);
			  txt_content.setPadding(5,5,5,5);
		        //valueTV.setTextAppearance(getApplicationContext(), R.style.commentstyle);
			  txt_content.setLayoutParams(new LayoutParams(
		                LayoutParams.WRAP_CONTENT,
		                LayoutParams.WRAP_CONTENT));
			  
			  
			  ((LinearLayout) vliLayout4).addView(txt_heading);
			((LinearLayout) vliLayout4).addView(txt_content);
				 
				((LinearLayout) linearLayout).addView(vliLayout4);
				
				vliLayout4.setVisibility(View.GONE);
				
				
			less.setOnClickListener(new OnClickListener() {
				
				
				public void onClick(View v) {
				
				//	d.setVisibility(View.GONE);
					vliLayout1.setVisibility(View.GONE);
					vliLayout2.setVisibility(View.GONE);
					vliLayout3.setVisibility(View.GONE);
					vliLayout4.setVisibility(View.GONE);

				
					
					liLayout.setVisibility(View.VISIBLE);
					f_vliLayout2.setVisibility(View.VISIBLE);
					valueTV.setVisibility(View.VISIBLE);
					more.setVisibility(View.VISIBLE);
					
					/*	
					
					f_vliLayout2.setVisibility(View.VISIBLE);
					valueTV.setVisibility(View.VISIBLE);
					more.setVisibility(View.VISIBLE);*/
				}
			});
	        
	        more.setOnClickListener(new OnClickListener() {
				
				
				public void onClick(View v) {
				
			
			vliLayout1.setVisibility(View.VISIBLE);
			vliLayout2.setVisibility(View.VISIBLE);
			vliLayout3.setVisibility(View.VISIBLE);
			vliLayout4.setVisibility(View.VISIBLE);
		
			liLayout.setVisibility(View.INVISIBLE);
			liLayout.setVisibility(View.GONE);
			
			//more.setVisibility(View.GONE);
			//more.setVisibility(View.INVISIBLE);
			//f_imageView_n.setVisibility(View.GONE);
			
			
			f_vliLayout2.setVisibility(View.GONE);
			valueTV.setVisibility(View.GONE);
			more.setVisibility(View.GONE);
			
			
			
				}
			});
	        
	       
		        
		       	    }
	  
	  private void love(int item_id) {

			
			try
	        {
			
				
				HttpClient httpsClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(obS.loveUrl);
				
				List<NameValuePair> nameValue = new ArrayList<NameValuePair>();
				nameValue.add(new BasicNameValuePair("user_id"," "+ user_id));
				nameValue.add(new BasicNameValuePair("item_id", " " + item_id));
				nameValue.add(new BasicNameValuePair("action", "" + 1));	
				
				httpPost.setEntity(new UrlEncodedFormEntity(nameValue));
				
				HttpResponse httpRes = httpsClient.execute(httpPost);
				
				//count(item_id);
			
				
				
				
	        }
	        catch (Exception e) { } 	 	
		}
		
	private void like(int item_id) {

		
		try
	    {
		
			
			HttpClient httpsClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(obS.loveUrl);
			
			List<NameValuePair> nameValue = new ArrayList<NameValuePair>();
			nameValue.add(new BasicNameValuePair("user_id"," "+ user_id));
			nameValue.add(new BasicNameValuePair("item_id", " " + item_id));
			nameValue.add(new BasicNameValuePair("action", "" + 2));	
			
			httpPost.setEntity(new UrlEncodedFormEntity(nameValue));
			
			HttpResponse httpRes = httpsClient.execute(httpPost);
			
	    }
	    catch (Exception e) { } 	 	
	}

	private void hate(int item_id)
	{
		try
	    {
			HttpClient httpsClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(obS.loveUrl);
			
			List<NameValuePair> nameValue = new ArrayList<NameValuePair>();
			nameValue.add(new BasicNameValuePair("user_id"," "+ user_id));
			nameValue.add(new BasicNameValuePair("item_id", " " + item_id));
			nameValue.add(new BasicNameValuePair("action", "" + 3));	
			
			httpPost.setEntity(new UrlEncodedFormEntity(nameValue));
			
			HttpResponse httpRes = httpsClient.execute(httpPost);
			
	    }
	    catch (Exception e) { } 	

	}

	private String count(Integer item_id)
	{
		try
	    {
	    	HttpClient httpsClient = new DefaultHttpClient();
	    	HttpPost httpPost = new HttpPost(obS.countUrl);
	    	List<NameValuePair> nameValue = new ArrayList<NameValuePair>();
	    	nameValue.add(new BasicNameValuePair("item_id", "" + item_id));
	    //	Toast.makeText(getApplicationContext(), item_id, Toast.LENGTH_LONG).show();
	    	httpPost.setEntity(new UrlEncodedFormEntity(nameValue));
	    	HttpResponse httpResponce = httpsClient.execute(httpPost);
	    	HttpEntity httpEntity = httpResponce.getEntity();
	    	is = httpEntity.getContent();       	
	    	
	    }
	    catch (Exception e) {	}
	    
	    try
	    {
	    	BufferedReader BufR = new BufferedReader(new InputStreamReader(is, "iso-8859-1"),8);
	    	sb = new StringBuilder();
	    	sb.append(BufR.readLine() + "\n");
	    	String line = "0";
	    	
	    	while((line = BufR.readLine()) != null)
	    	{
	    		sb.append(line + "\n");
	    	}
	    	
	    	is.close();
	    	result = sb.toString();
	    }
	    catch (Exception e) {}
	    

	    
	    try{
	    
	    	JSONObject json_data = new JSONObject(result);
	    //	Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
	    	String data = json_data.getString("Data");
	    	JSONArray jArray1= new JSONArray(data);
	    	JSONObject json_data1 = null;
	    	json_data1 = jArray1.getJSONObject(0);
			//Toast.makeText(getApplicationContext(), id.toString(), Toast.LENGTH_LONG).show();
	    //	for(int i = 0 ; i < jArray.length(); i++)
	    	
	    	
	    	
	    	{
	    	//	json_data = jArray.getJSONObject(i);
	    		c_love = json_data1.getString("love");
	    		//clike = json_data1.getString("_like");
	    		//c_hate = json_data1.getString("hate");
	    		
	    	}
	    //	Toast.makeText(getApplicationContext(), c_love, Toast.LENGTH_LONG).show();
	    	
	    	{
	    		clove.setText(c_love);
	    		//txt_count_like.setText(clike);
	    		//txt_count_hate.setText(c_hate);
	    	}	
	         }
	    catch (JSONException e) {
		}
	    catch (Exception e) {
	    	
		}
	    
	    return c_love.toString();
	}

	private String count_hate(Integer item_id)
	{
		try
	    {
	    	HttpClient httpsClient = new DefaultHttpClient();
	    	HttpPost httpPost = new HttpPost(obS.countUrl);
	    	List<NameValuePair> nameValue = new ArrayList<NameValuePair>();
	    	nameValue.add(new BasicNameValuePair("item_id", "" + item_id));
	    
	    	httpPost.setEntity(new UrlEncodedFormEntity(nameValue));
	    	HttpResponse httpResponce = httpsClient.execute(httpPost);
	    	HttpEntity httpEntity = httpResponce.getEntity();
	    	is = httpEntity.getContent();       	
	    	
	    }
	    catch (Exception e) {	}
	    
	    try
	    {
	    	BufferedReader BufR = new BufferedReader(new InputStreamReader(is, "iso-8859-1"),8);
	    	sb = new StringBuilder();
	    	sb.append(BufR.readLine() + "\n");
	    	String line = "0";
	    	
	    	while((line = BufR.readLine()) != null)
	    	{
	    		sb.append(line + "\n");
	    	}
	    	
	    	is.close();
	    	result = sb.toString();
	    }
	    catch (Exception e) {}
	    

	    try{
	    
	    	JSONObject json_data = new JSONObject(result);
	    	String data = json_data.getString("Data");
	    	JSONArray jArray1= new JSONArray(data);
	    	JSONObject json_data1 = null;
	    	json_data1 = jArray1.getJSONObject(0);
		
	    		c_hate = json_data1.getString("hate");
	    		
	    	

	         }
	    catch (JSONException e) {
		}
	    catch (Exception e) {
	    	
		}
	    
	    return c_hate.toString();
	}

	private String count_like(Integer item_id)
	{
		try
	    {
	    	HttpClient httpsClient = new DefaultHttpClient();
	    	HttpPost httpPost = new HttpPost(obS.countUrl);
	    	List<NameValuePair> nameValue = new ArrayList<NameValuePair>();
	    	nameValue.add(new BasicNameValuePair("item_id", "" + item_id));
	    
	    	httpPost.setEntity(new UrlEncodedFormEntity(nameValue));
	    	HttpResponse httpResponce = httpsClient.execute(httpPost);
	    	HttpEntity httpEntity = httpResponce.getEntity();
	    	is = httpEntity.getContent();       	
	    	
	    }
	    catch (Exception e) {	}
	    
	    try
	    {
	    	BufferedReader BufR = new BufferedReader(new InputStreamReader(is, "iso-8859-1"),8);
	    	sb = new StringBuilder();
	    	sb.append(BufR.readLine() + "\n");
	    	String line = "0";
	    	
	    	while((line = BufR.readLine()) != null)
	    	{
	    		sb.append(line + "\n");
	    	}
	    	
	    	is.close();
	    	result = sb.toString();
	    }
	    catch (Exception e) {}
	    

	    try{
	    
	    	JSONObject json_data = new JSONObject(result);
	    	String data = json_data.getString("Data");
	    	JSONArray jArray1= new JSONArray(data);
	    	JSONObject json_data1 = null;
	    	json_data1 = jArray1.getJSONObject(0);
		
	    		c_like = json_data1.getString("_like");
	    		
	    	

	         }
	    catch (JSONException e) {
		}
	    catch (Exception e) {
	    	
		}
	    
	    return c_like.toString();
	}
	
    public Bitmap getImageBitmap(String url) {
	            Bitmap bm = null;
	            URLConnection conn = null;
	            InputStream is = null;
	            BufferedInputStream bis =null;
	            try {
	                URL aURL = new URL(url);
	                
	                conn = aURL.openConnection();
	                
	                conn.connect();
	              is  = conn.getInputStream();
	              bis  = new BufferedInputStream(is);
	                bm = BitmapFactory.decodeStream(bis);
	                bis.close();
	                is.close();
	           } catch (IOException e) { }
	           return bm;
	        }


}
