package com.example.mp3player.view;

import java.util.ArrayList;
import java.util.Queue;

import com.example.mp3player.R;
import com.example.mp3player.model.Mp3Info;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class PlayerActivity extends Activity{
	private ImageButton begin_button,pause_button,stop_button;
	private TextView lrd_texView;
	
	private ArrayList<Queue> queues = null;
	private Mp3Info mp3Info = null;
	private MediaPlayer mediaPlayer = null;
	private Handler handler = new Handler();
	private UpdateTimeCallback updateTimeCallback = null;
	
	private long begin = 0;
	private long nextTimeMill = 0;
	private long currentTimeMill = 0;
	private String message = null;
	private long pauseTimeMills = 0;
	private boolean isPlaying = false;
      @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.player);
    	initView();
    }
      
     private void initView() {
    	 begin_button = (ImageButton) findViewById(R.id.begin);
    	 begin_button.setOnClickListener(new BeginListener());
    	 
    	 pause_button = (ImageButton) findViewById(R.id.pause);
    	 pause_button.setOnClickListener(new PauseListener());
    	 
    	 stop_button = (ImageButton) findViewById(R.id.stop);
    	 stop_button.setOnClickListener(new StopListener());
    	 
    	 lrd_texView = (TextView) findViewById(R.id.lrcText);
    	 
     }
     
     public class BeginListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
		}
    	 
     }
     
     public class PauseListener implements OnClickListener {

    	 @Override
 		public void onClick(View arg0) {
 			// TODO Auto-generated method stub
 			
 		}
     	 
      }
     
     public class StopListener implements OnClickListener {

    	 @Override
 		public void onClick(View arg0) {
 			// TODO Auto-generated method stub
 			
 		}
      }
     
     class UpdateTimeCallback implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
    	 
     }
}
