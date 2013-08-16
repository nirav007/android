package com.chat;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;
import android.widget.Toast;

public class DataHendleCls {

//	public static final String delurl = "http://wingstechsolutions.com/paws/del.php";
//	public static final String getAllData = "http://wingstechsolutions.com/paws/index.php";
//	public static final String insUrl = "http://wingstechsolutions.com/paws/ins.php";
//	public static final String upUrl = "http://wingstechsolutions.com/paws/up.php";
	
	
	public static final String insUrl = "http://192.168.1.4/chat/ins.php";
	public static final String sUrl = "http://192.168.1.4/chat/select.php";
	
	public static String upid;
	public static String upname;
	
	
	

}
