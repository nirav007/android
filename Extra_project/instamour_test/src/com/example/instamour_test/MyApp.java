package com.example.instamour_test;

import android.app.Application;

public class MyApp extends Application {

  private String uname;

  public String getState(){
    return uname;
  }
  public void setState(String s){
    uname = s;
  }
  
  
}

