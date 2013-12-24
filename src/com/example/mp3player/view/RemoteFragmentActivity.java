package com.example.mp3player.view;

import com.example.mp3player.R;

import android.R.anim;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class RemoteFragmentActivity extends FragmentActivity{
     @Override
    protected void onCreate(Bundle arg0) {
    	// TODO Auto-generated method stub
    	super.onCreate(arg0);
    	
    	FragmentManager fm = getSupportFragmentManager();
    	if (fm.findFragmentById(android.R.id.content) == null) {
    		RemoteFragment remoteFragment = new RemoteFragment();
    		fm.beginTransaction().add(android.R.id.content, remoteFragment).commit();
    	}
    }
     
     public static class RemoteFragment extends Fragment {
    	 private ListView remoteListView = null;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			View view = inflater.inflate(R.layout.remote, container,false);
			
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
