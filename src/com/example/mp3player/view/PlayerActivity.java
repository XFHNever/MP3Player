package com.example.mp3player.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Queue;

import com.example.mp3player.R;
import com.example.mp3player.model.Mp3Info;
import com.example.mp3player.service.PlayerService;
import com.example.mp3player.util.LrcProcessor;
import com.example.mp3player.util.MPConstants;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
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
     
 	/**
 	 * 根据歌词文件的名字，来读取歌词文件当中的信息
 	 * @param lrcName
 	 */
 	private void prepareLrc(String lrcName){
 		try {
 			InputStream inputStream = new FileInputStream(Environment.getExternalStorageDirectory().getAbsoluteFile() +File.separator + "mp3/" + mp3Info.getLrcName());
 			LrcProcessor lrcProcessor = new LrcProcessor();
 			queues = lrcProcessor.process(inputStream);
 			//创建一个UpdateTimeCallback对象
 			updateTimeCallback = new UpdateTimeCallback(queues);
 			begin = 0 ;
 			currentTimeMill = 0 ;
 			nextTimeMill = 0 ;
 		} catch (FileNotFoundException e) {
 			e.printStackTrace();
 		}
 	}
     
     public class BeginListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(PlayerActivity.this, PlayerService.class);
			intent.putExtra("mp3Info", mp3Info);
			intent.putExtra("MSG", MPConstants.PlayerMsg.PLAY_MSG);
			prepareLrc(mp3Info.getLrcName());
			startActivity(intent);
			begin = System.currentTimeMillis();
			handler.postDelayed(updateTimeCallback, 5);
			isPlaying = true;
		}
    	 
     }
     
     public class PauseListener implements OnClickListener {

    	 @Override
 		public void onClick(View arg0) {
 			// TODO Auto-generated method stub
    		Intent intent = new Intent();
 			intent.setClass(PlayerActivity.this, PlayerService.class);
 			intent.putExtra("MSG", MPConstants.PlayerMsg.PAUSE_MSG);
 			startActivity(intent);
 			
 			if(isPlaying) {
 				handler.removeCallbacks(updateTimeCallback);
 				pauseTimeMills = System.currentTimeMillis();
 			} else {
 				handler.postDelayed(updateTimeCallback, 5);
 				begin = System.currentTimeMillis()-pauseTimeMills + begin;
 			}
 			isPlaying = !isPlaying;
 			
 		}
     	 
      }
     
     public class StopListener implements OnClickListener {

    	 @Override
 		public void onClick(View arg0) {
 			// TODO Auto-generated method stub
    		 Intent intent = new Intent();
 			intent.setClass(PlayerActivity.this, PlayerService.class);
 			intent.putExtra("MSG", MPConstants.PlayerMsg.STOP_MSG);
 			startActivity(intent);
 			handler.removeCallbacks(updateTimeCallback);
 		}
      }
     
     class UpdateTimeCallback implements Runnable{
        Queue times = null;
        Queue messages = null;
		public UpdateTimeCallback(ArrayList<Queue> queues) {
			// TODO Auto-generated constructor stub
			times = queues.get(0);
			messages = queues.get(1);
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			long offset = System.currentTimeMillis() - begin;
			if(currentTimeMill == 0){
				nextTimeMill = (Long)times.poll();
				message = (String)messages.poll();
			}
			if(offset >= nextTimeMill){
				lrd_texView.setText(message);
				message = (String)messages.poll();
				nextTimeMill = (Long)times.poll();
			}
			currentTimeMill = currentTimeMill + 10;
			handler.postDelayed(updateTimeCallback, 10);
		}
    	 
     }
}
