package edu.rit.csh.androidwebnews;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.FragmentActivity;

public class SearchActivity extends FragmentActivity implements ActivityInterface {
	HttpsConnector hc;
	SearchFragment sf;
	SearchListFragment slf;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
	    String apiKey = sharedPref.getString("api_key", "");	    
	    hc = new HttpsConnector(apiKey, this);	    	    
	    if (!hc.validApiKey()) {
	         new InvalidApiKeyDialog(this).show();
	    }
	    hc.getNewsGroups(); // used for list of newsgroups to look through
	    sf = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.search_fragment);
	    slf = new SearchListFragment(this, new ArrayList<String>());
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_default, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		
		case R.id.menu_refresh:
			//hc.getNewest();
			return true;
			
		case R.id.menu_about:
			startActivity(new Intent(this, InfoActivity.class));
			return true;
		
		case R.id.menu_search:
			startActivity(new Intent(this, SearchActivity.class));
			return true;
		}
		return false;
	}

	@Override
	public void update(String jsonString) {
		ArrayList<String> subjects = new ArrayList<String>();
		for (PostThread thread : hc.getSearchFromString(jsonString)) {
			subjects.add(thread.subject);
		}
		slf.update(subjects);
	}
	
	@Override
	public void onNewsgroupSelected(String newsgroupName) {
		// TODO Auto-generated method stub
		
	}
	
	public void onSelectThread(String s) {
		
	}
	
	public void search(View view) {
		Log.d("newdebug", sf.getParams().toString());
		hc.search(sf.getParams());
		//setContentView(slf);
		
	}
}
 