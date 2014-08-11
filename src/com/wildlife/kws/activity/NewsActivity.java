package com.wildlife.kws.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kws.orangi.kenyawildlifeservice.R;
import com.parse.ParseAnalytics;
import com.wildlife.kws.helper.JSONFunctions;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class NewsActivity extends ListActivity{

	String KEY_NEWS_ID = "nws_id",
			KEY_NEWS_TITLE = "nws_title",
			KEY_NEWS_DATE = "nws_date",
			KEY_NEWS_CONTENT = "nws_content";
	
	String url = HomeActivity.url_base;
	fetchNewsData fetchNewsDataObject;
	String params;

	ListView list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);
		
//      PUSH SERVICES
		ParseAnalytics.trackAppOpened(getIntent());
		
		initialize();
		
		fetchNewsDataObject = new fetchNewsData();
		params = "parameters";
		
		showNewsItems();
	}

	private void showNewsItems() {
		// TODO Auto-generated method stub
	
		fetchNewsDataObject.execute(params);
		
	}

	private void initialize() {
		// TODO Auto-generated method stub
		list = (ListView) findViewById(android.R.id.list);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		int id = item.getItemId();
        switch (id) {
		case android.R.id.home:
//			getSupportFragmentManager().popBackStack();
//			getSupportActionBar().setDisplayHomeAsUpEnabled(false);
			
			Intent intent = new Intent(NewsActivity.this, HomeActivity.class);
			intent.putExtra("title", "National Parks");
			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			
			startActivity(intent);	
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}  
	}
	
	
    public class fetchNewsData extends AsyncTask<String, String, Void>{
    	ArrayList<HashMap<String, String>> itemslList = new ArrayList<HashMap<String, String>>();
    	
    	 private ProgressDialog progressDialog = new ProgressDialog(NewsActivity.this);
    	    InputStream is = null ;
    	    String result = "";
    	    protected void onPreExecute() {
    	       progressDialog.setMessage("Fetching data... Please wait");
    	       progressDialog.show();
    	       progressDialog.setOnCancelListener(new OnCancelListener() {
		    	 @Override
		    	  public void onCancel(DialogInterface arg0) {
		    		 fetchNewsData.this.cancel(true);
		    	    }
		    	 });
    	     }
    	
    	    @Override
    		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
    	    	
    			String jsonString = (JSONFunctions.getJSONfromURLString(url + "news")).toString();
    			
    			JSONArray valarray = null;
    			try {
    				valarray = new JSONArray(jsonString);
    			} catch (JSONException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}
    			
    			try {
    				
    				Log.e("log_news_items_size", "Size of news items arary:: " + valarray.length());
    						
    						for (int i = 0; i < valarray.length(); i++) {
    								HashMap<String, String> map = new HashMap<String, String>();
    								
    								JSONObject e = (JSONObject) valarray.getJSONObject(i);
    								map.put(KEY_NEWS_ID, e.getString(KEY_NEWS_ID));
    								map.put(KEY_NEWS_TITLE, e.getString(KEY_NEWS_TITLE));
    								map.put(KEY_NEWS_DATE, e.getString(KEY_NEWS_DATE));
    								map.put(KEY_NEWS_CONTENT, e.getString(KEY_NEWS_CONTENT));
    																		
    								itemslList.add(map);
    								
    							}
    						
    				} catch (JSONException e) {
    					// TODO: handle exception
    					Log.e("log_tag_fetching_log_news_items", "Error passing data in NEWS" + e.toString());
    				}
    			
    			
			
			return null;			
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			
			processNewsReceived(itemslList);	
			
			this.progressDialog.dismiss();

		}
    	
    }


	public void processNewsReceived(ArrayList<HashMap<String, String>> itemslList) {
		// TODO Auto-generated method stub
		
		ListAdapter adapter = new SimpleAdapter(this, itemslList, R.layout.activity_news_child, new String[]{KEY_NEWS_TITLE, KEY_NEWS_DATE}, new int[]{R.id.tv_news_title, R.id.tv_news_date});
		setListAdapter(adapter);
		
		final ListView lv = getListView();

		lv.setTextFilterEnabled(true);
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				@SuppressWarnings("unchecked")
				HashMap<String, String> itemsMap = (HashMap<String, String>) lv.getItemAtPosition(position);
				
				String event_title = itemsMap.get(KEY_NEWS_TITLE).toString();
				String event_date = itemsMap.get(KEY_NEWS_DATE).toString();
				String event_content = itemsMap.get(KEY_NEWS_CONTENT).toString();
				
				
				final AlertDialog.Builder dialogAlert = new AlertDialog.Builder(NewsActivity.this);
				dialogAlert.setTitle(event_title);
				dialogAlert.setMessage("Posted on:  " + event_date + "\n\n" + event_content);
				dialogAlert.setCancelable(true);
				dialogAlert.setNegativeButton("Done", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
						
					}
				});
				
				dialogAlert.create().show();
				
			}
		});
	}
	
	
	
}
