package com.example.mp3player.view;


import com.example.mp3player.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class LocalFragmentActivity extends FragmentActivity{
	    @Override
	    protected void onCreate(Bundle arg0) {
	    	// TODO Auto-generated method stub
	    	super.onCreate(arg0);
	    	
	    	FragmentManager fm = getSupportFragmentManager();
	    	if (fm.findFragmentById(android.R.id.content) == null) {
	    		LocalFragment localFragment = new LocalFragment();
	    		fm.beginTransaction().add(android.R.id.content, localFragment).commit();
	    	}
	    }
	     
	     public static class LocalFragment extends Fragment {
	    	 private ListView localListView = null;

	 		@Override
	 		public void onCreate(Bundle savedInstanceState) {
	 			// TODO Auto-generated method stub
	 			super.onCreate(savedInstanceState);
	 		}

	 		@Override
	 		public View onCreateView(LayoutInflater inflater, ViewGroup container,
	 				Bundle savedInstanceState) {
	 			// TODO Auto-generated method stub
	 			View view = inflater.inflate(R.layout.local, container,false);
	 			
	 			return view;
	 		}

	 		@Override
	 		public void onPause() {
	 			// TODO Auto-generated method stub
	 			super.onPause();
	 		}

	 		@Override
	 		public void onResume() {
	 			// TODO Auto-generated method stub
	 			super.onResume();
	 		}
	     }
}
