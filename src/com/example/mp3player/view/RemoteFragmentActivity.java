package com.example.mp3player.view;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.example.mp3player.R;
import com.example.mp3player.download.HttpDownloader;
import com.example.mp3player.model.Mp3Info;
import com.example.mp3player.util.Mp3ListContentHandler;

import android.R.anim;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

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
    	 private List<Mp3Info> mp3Infos;
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
			remoteListView = (ListView) view.findViewById(R.id.remote_list);
			updateListView();
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
			updateListView();
		}
		
		 private List<Mp3Info> parse(String xmlStr) {
	    	 List<Mp3Info> list = new ArrayList<Mp3Info>();
	         SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
	         try {
				XMLReader xmlReader = saxParserFactory.newSAXParser().getXMLReader();
				Mp3ListContentHandler mp3ListContentHandler = new Mp3ListContentHandler(list);
				
				xmlReader.setContentHandler(mp3ListContentHandler);
				xmlReader.parse(new InputSource(new StringReader(xmlStr)));
				
				for (Iterator iterator = list.iterator();iterator.hasNext();) {
					Mp3Info mp3Info = (Mp3Info) iterator.next();
					System.out.println(mp3Info);
				}
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       
	         
	         return list;
	     }
	     
	  
	     private String downloadXML(String urlStr) {
	    	 HttpDownloader httpDownloader=new HttpDownloader();  
	         String result=httpDownloader.download(urlStr);  
	         return result;
	     }
	     
	     private void updateListView() {
	    	 String xml = downloadXML("http://10.0.2.2:8080/mp3/resources.xml");
	    	 mp3Infos = parse(xml);
	    	 SimpleAdapter simpleAdapter = buildAdapter(mp3Infos);
	    	 remoteListView.setAdapter(simpleAdapter);
	    	 remoteListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					if(mp3Infos != null) {
						Mp3Info mp3Info = mp3Infos.get(arg2);
						Intent intent = new Intent();
						intent.putExtra("mp3Info", mp3Info);
						intent.setClass(getActivity(), PlayerActivity.class);
						startActivity(intent);
					}
					
				}
	    		 
			});
	     }
	     
	     private SimpleAdapter buildAdapter(List<Mp3Info> mp3Infos) {
	    	 List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
	    	 for(Iterator iterator = mp3Infos.iterator();iterator.hasNext();) {
	    		 Mp3Info mp3Info = (Mp3Info) iterator.next();
	    		 HashMap<String, String> map = new HashMap<String, String>();
	    		 map.put("mp3_name", mp3Info.getMp3Name());
	    		 map.put("mp3_size", mp3Info.getMp3Size());
	    		 list.add(map);
	    	 }
	    	 
	    	 SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), list, R.layout.mp3info_item, new String[]{"mp3_name","mp3_size"}, new int[]{R.id.mp3_name,R.id.mp3_size});
	         return simpleAdapter;
	     }
    	 
     }
}
