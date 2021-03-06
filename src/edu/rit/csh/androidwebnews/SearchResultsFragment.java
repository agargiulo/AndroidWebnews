/**
See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  This code is licensed
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
*/	
package edu.rit.csh.androidwebnews;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.app.ListFragment;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SearchResultsFragment extends Fragment {
	HttpsConnector hc;
	ArrayList<String> threads;
	ArrayAdapter<String> listAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		threads = SearchResultsActivity.searchResults;
		
		Log.d("MyDebugging","Starting SearchListFragment view creation");
		ListView mainListView = new ListView(getActivity());
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
	    String apiKey = sharedPref.getString("api_key", "");
		hc = new HttpsConnector(getActivity());
		//hc.getNewsGroups();
		
		listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.search_result_textview, threads);
		
		
		mainListView.setAdapter(listAdapter);
		
		Log.d("MyDebugging", "Setting click listener");
		mainListView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> adapter, View arg1, int position,
					long id) {
				Log.d("MyDebugging", "Clicky!");
				//String value = (String) adapter.getItemAtPosition(position);
				((SearchResultsActivity)getActivity()).onSelectThread(position);
			}
			
		});
		Log.d("MyDebugging","SearchListFragment view creation done");
		
		return mainListView;
	
	}
	
	public void update(ArrayList<String> threads)
	{
		if(listAdapter != null)
		{
			this.threads = threads;
			listAdapter.clear();
			for(String s : threads)
				listAdapter.add(s);
			listAdapter.notifyDataSetChanged();
		}
	}

}