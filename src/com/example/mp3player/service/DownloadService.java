package com.example.mp3player.service;

import com.example.mp3player.download.HttpDownloader;
import com.example.mp3player.model.Mp3Info;
import com.example.mp3player.util.DebugUtil;
import com.example.mp3player.util.MPConstants;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DownloadService extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Mp3Info mp3Info = (Mp3Info) intent.getSerializableExtra("mp3Info");
		DownloadThread downloadThread = new DownloadThread(mp3Info);
		Thread thread = new Thread(downloadThread);
		thread.start();
		return super.onStartCommand(intent, flags, startId);
	}
	
	public class DownloadThread implements Runnable {
        private Mp3Info mp3Info = null;
        
        public DownloadThread(Mp3Info mp3Info) {
        	this.mp3Info = mp3Info;
        }
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String mp3_url = MPConstants.URL.BASE_URL + mp3Info.getMp3Name();
			String lrd_url = MPConstants.URL.BASE_URL + mp3Info.getLrcName();
			
			HttpDownloader downloader = new HttpDownloader();
			
			int mp3_result = downloader.downFile(mp3_url, "mp3/", mp3Info.getMp3Name());
			int lrd_result = downloader.downFile(lrd_url, "mp3/", mp3Info.getLrcName());
			DebugUtil.debug("downloadService", mp3_result + ":" +lrd_result);
		}
		
	}

}
