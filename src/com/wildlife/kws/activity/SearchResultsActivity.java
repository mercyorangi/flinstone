package com.wildlife.kws.activity;

import com.kws.orangi.kenyawildlifeservice.R;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class SearchResultsActivity extends ActionBarActivity{
	
	private TextView txtQuery;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
 
        // get the action bar
        ActionBar actionBar = getSupportActionBar();
 
        // Enabling Back navigation on Action Bar icon
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
 
        txtQuery = (TextView) findViewById(R.id.txtQuery);
 
        handleIntent(getIntent());
    }
	
  @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

  /**
   * Handling intent data
   */
	private void handleIntent(Intent intent) {
		// TODO Auto-generated method stub
		 if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	            String query = intent.getStringExtra(SearchManager.QUERY);
	 
	            /**
	             * Use this query to display search results like 
	             * 1. Getting the data from SQLite and showing in listview 
	             * 2. Making webrequest and displaying the data 
	             * For now we just display the query only
	             */
	            txtQuery.setText("Search Query: " + query);
	 
	        }
	}
 

}
