package com.example.mp3player.view;

import java.util.HashMap;

import com.example.mp3player.R;
import com.example.mp3player.R.id;
import com.example.mp3player.R.layout;
import com.example.mp3player.R.menu;
import com.example.mp3player.R.string;
import com.example.mp3player.util.DebugUtil;
import com.example.mp3player.util.MPConstants;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.DebugUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

public class MainActivity extends FragmentActivity {
    TabHost mTabHost;
    TabManager mTabManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Resources res = getResources();
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		
		mTabManager = new TabManager(this, mTabHost, R.id.realtabcontent);
	
	    mTabManager.addTab(mTabHost.newTabSpec("Remote").setIndicator("Remote"),
	    		RemoteFragmentActivity.RemoteFragment.class, null);
	    mTabManager.addTab(mTabHost.newTabSpec("Local").setIndicator("Local"),
	    		LocalFragmentActivity.LocalFragment.class, null);
	    
	    if(savedInstanceState != null) {
	    	mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
	    }
	    

	}
	
	 @Override
	    protected void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	        outState.putString("tab", mTabHost.getCurrentTabTag());
	    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add(0, MPConstants.UPDATE, 1, R.string.menu_update);
		menu.add(0, MPConstants.ABOUT, 2, R.string.menu_about);
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == MPConstants.UPDATE) {
			DebugUtil.debug("MainActivity", "update is selected");
		} else if (item.getItemId() == MPConstants.ABOUT) {
			DebugUtil.debug("MainActivity", "about is selected");
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	
	
	
	public static class TabManager implements OnTabChangeListener {
        private final FragmentActivity mActivity;
        private final TabHost mTabHost;
        private final int mContainerId;
        private final HashMap<String, TabInfo> mTabs = new HashMap<String, TabInfo>();
        TabInfo mLastTab;
        
        static final class TabInfo {
            private final String tag;
            private final Class<?> clss;
            private final Bundle args;
            private Fragment fragment;
 
            TabInfo(String _tag, Class<?> _class, Bundle _args) {
                tag = _tag;
                clss = _class;
                args = _args;
            }
        }
      
        static class DummyTabFactory implements TabHost.TabContentFactory {
            private final Context mContext;
 
            public DummyTabFactory(Context context) {
                mContext = context;
            }
 
            @Override
            public View createTabContent(String tag) {
                View v = new View(mContext);
                v.setMinimumWidth(0);
                v.setMinimumHeight(0);
                return v;
            }
        }
        
        
        public TabManager(FragmentActivity activity, TabHost tabHost, int containerId) {
            mActivity = activity;
            mTabHost = tabHost;
            mContainerId = containerId;
            mTabHost.setOnTabChangedListener(this);
        }
        
        
        
        public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
            tabSpec.setContent(new DummyTabFactory(mActivity));
            String tag = tabSpec.getTag();
 
            TabInfo info = new TabInfo(tag, clss, args);
 
            // Check to see if we already have a fragment for this tab, probably
            // from a previously saved state.  If so, deactivate it, because our
            // initial state is that a tab isn't shown.
            info.fragment = mActivity.getSupportFragmentManager().findFragmentByTag(tag);
            if (info.fragment != null && !info.fragment.isDetached()) {
                FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
                ft.detach(info.fragment);
                ft.commit();
            }
 
            mTabs.put(tag, info);
            mTabHost.addTab(tabSpec);
        }
        
		@Override
		public void onTabChanged(String tabId) {
			// TODO Auto-generated method stub
			 TabInfo newTab = mTabs.get(tabId);
	            if (mLastTab != newTab) {
	                FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
	                if (mLastTab != null) {
	                    if (mLastTab.fragment != null) {
	                        ft.detach(mLastTab.fragment);
	                    }
	                }
	                if (newTab != null) {
	                    if (newTab.fragment == null) {
	                        newTab.fragment = Fragment.instantiate(mActivity,
	                                newTab.clss.getName(), newTab.args);
	                        ft.add(mContainerId, newTab.fragment, newTab.tag);
	                    } else {
	                        ft.attach(newTab.fragment);
	                    }
	                }
	 
	                mLastTab = newTab;
	                ft.commit();
	                mActivity.getSupportFragmentManager().executePendingTransactions();
	            }
		}
		
	}

}
