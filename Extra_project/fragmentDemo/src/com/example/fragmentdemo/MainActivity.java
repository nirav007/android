package com.example.fragmentdemo;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    int mStackLevel = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        // Watch for button clicks.
        Button button = (Button)findViewById(R.id.new_fragment);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                addFragmentToStack();
            }
        });
 
        if (savedInstanceState == null) {
            Fragment newFragment = CountingFragment.newInstance(mStackLevel);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.simple_fragment, newFragment).commit();
        } else {
            mStackLevel = savedInstanceState.getInt("level");
        }
    }
 
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("level", mStackLevel);
    }
 
 
    void addFragmentToStack() {
        mStackLevel++;
        Fragment newFragment = CountingFragment.newInstance(mStackLevel);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
   /*     ft.setCustomAnimations(R.animator.fragment_slide_left_enter,
                R.animator.fragment_slide_left_exit,
                R.animator.fragment_slide_right_enter,
               R.animator.fragment_slide_right_exit);*/
        ft.replace(R.id.simple_fragment, newFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
 
    public static class CountingFragment extends Fragment {
        int mNum;
 
        static CountingFragment newInstance(int num) {
            CountingFragment f = new CountingFragment();
            Bundle args = new Bundle();
            args.putInt("num", num);
            f.setArguments(args);
 
            return f;
        }
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment, container, false);
            View tv = v.findViewById(R.id.text);
            ((TextView)tv).setText("Hello. This is fragment example #" + mNum);
            tv.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.gallery_thumb));
            return v;
        }
    }
}