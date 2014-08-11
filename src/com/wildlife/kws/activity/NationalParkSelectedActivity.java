package com.wildlife.kws.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kws.orangi.kenyawildlifeservice.R;
import com.wildlife.kws.adapter.TabsPagerAdapter;
import com.wildlife.kws.helper.JSONFunctions;
import com.wildlife.kws.helper.URLEncode;
import com.wildlife.kws.interfaces.IRequestTitle;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

public class NationalParkSelectedActivity extends ActionBarActivity implements IRequestTitle,ActionBar.TabListener, ViewPager.OnPageChangeListener{
	
	private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    private String[] tabs = { "Park Hits", "on Map" };    
    public String park_selected_for_display = "blank";
    
    HttpClient httpclient;
    HttpPost httppost;
    InputStream inputStream;
    List<NameValuePair> nameValuePairs;
    
    private static String url_base_selected_park = HomeActivity.url_base;
    public static String KEY_PARK_NAME = HomeActivity.KEY_PARK_NAME,
			KEY_PARK_DESCRIPTION = HomeActivity.KEY_PARK_DESCRIPTION,
			KEY_PARK_ID = HomeActivity.KEY_PARK_ID,
			KEY_PARK_LAT = HomeActivity.KEY_PARK_LAT,
			KEY_PARK_LONG = HomeActivity.KEY_PARK_LONG,
			KEY_PARK_ACTIVITY_NAME = HomeActivity.KEY_PARK_ACTIVITY_NAME,
			KEY_PARK_ACTIVITY_DESCRIPTION = HomeActivity.KEY_PARK_ACTIVITY_DESCRIPTION,
			KEY_PARK_SNIPPET = HomeActivity.KEY_PARK_SNIPPET,
			KEY_PARK_MAERKER_NAME= HomeActivity.KEY_PARK_MAERKER_NAME;
    private String params;
    public static String url = url_base_selected_park + "national_parks_byname/";
    private ArrayList<HashMap<String, String>> detailsForNationalParkSelected;
    public HashMap<String, String> map;
    public String [] toPass;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_national_park_selected_tabs);
        
        //Get data passed from previous activity
        getBundles();
        
        //Initialize
        initialise();  
        
        // get the action bar
        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayHomeAsUpEnabled(true);
//      actionBar.setHomeButtonEnabled(true);
        
        viewPager.setAdapter(mAdapter);               
        
     // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }
        
        
        
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        
        
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
          Toast.makeText(getApplicationContext(), "nothing passed", Toast.LENGTH_LONG).show();
        }
        // get data via the key
        String nameOfParkSelected = extras.getString("nameOfParkSelected");
        String nameOfParkDescription = extras.getString("nameOfParkDescription");
        
        if (nameOfParkSelected != null) {
        	getSupportActionBar().setTitle(nameOfParkSelected);   
        	Toast.makeText(getApplicationContext(), nameOfParkDescription, Toast.LENGTH_LONG).show();
        }      
        
        //Fetch content depending on what has been selected
        if (getTitle() == "Aberdere National Park") {
			//fetch data from DB for this particular park then display where necessary
        	Toast.makeText(getApplicationContext(),"loaded:: " + getTitle(),Toast.LENGTH_SHORT).show();
		} else if (getTitle() == "Aberdere National Park"){
			//fetch data from DB for this particular park then display where necessary
			Toast.makeText(getApplicationContext(),"loaded:: " + getTitle(),Toast.LENGTH_SHORT).show();
		}
        
        detailsForNationalParkSelected = new ArrayList<HashMap<String,String>>();
        
    }
	
	private void initialise() {
		// TODO Auto-generated method stub
		viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        
	}

	private void getBundles() {
		// TODO Auto-generated method stub
	      Bundle extras = getIntent().getExtras();
	        if (extras != null) {
			    // get data via the key
			    park_selected_for_display  = extras.getString("national_park_selected");
			    setTitle(park_selected_for_display);   
	        }
	}

	@Override
	public void setTitle(CharSequence title) {
		// TODO Auto-generated method stub
		super.setTitle(title);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		int id = item.getItemId();
        switch (id) {
		case android.R.id.home:
//			getSupportFragmentManager().popBackStack();
//			getSupportActionBar().setDisplayHomeAsUpEnabled(false);
			
			Intent intent = new Intent(NationalParkSelectedActivity.this, HomeActivity.class);
			intent.putExtra("title", "National Parks");
			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			
			startActivity(intent);	
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}  
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		actionBar.setSelectedNavigationItem(position);
	}
	

	@Override
	public String getMyTitle() {
		// TODO Auto-generated method stub
		return (String) getTitle();//park_selected_for_display;
	}

	@Override
	public HashMap<String, String> getJsonForParkSelected() {
		// TODO Auto-generated method stub
		map = new HashMap<String, String>();
        
		 new fetchParkSelectedDataFromDB().execute(params);
		 System.out.println("KEY_PARK_NAME::inside LISTENER:: " + map.get(KEY_PARK_NAME));
		 
		return map;
	}

	class fetchParkSelectedDataFromDB extends AsyncTask<String, String, HashMap<String, String>>{

		String title_of_park_selected_1 = URLEncode.encodeURL((String) getTitle());
		
		private ProgressDialog progressDialog = new ProgressDialog(NationalParkSelectedActivity.this);
	    InputStream is = null ;
	    String result_json = "";
	    String result = "";
	    
		    protected void onPreExecute() {
		       progressDialog.setMessage("Loading park info... Please wait");
		       progressDialog.show();
		       progressDialog.setOnCancelListener(new OnCancelListener() {
		    	 @Override
		    	  public void onCancel(DialogInterface arg0) {
		    		 fetchParkSelectedDataFromDB.this.cancel(true);
		    	    }
		    	 });
		     }
    	
		   
		    @Override
			protected HashMap<String, String> doInBackground(String... params) {
				// TODO Auto-generated method stub
		    	
		    	try {
		    		
		    		String jsonString = (JSONFunctions.getJSONfromURLString(url + title_of_park_selected_1)).toString();
					System.out.println("jsonString for national_parks_byname:: " + jsonString);
					
					JSONArray valarray = null;
					try {
						valarray = new JSONArray(jsonString);
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						
						Log.e("log_tag_size", "Size of spec arary:: " + valarray.length());
						
							JSONObject e = (JSONObject) valarray.getJSONObject(0);
								
								map.put(KEY_PARK_NAME, e.getString(KEY_PARK_NAME));
								map.put(KEY_PARK_DESCRIPTION, e.getString(KEY_PARK_DESCRIPTION));
								map.put(KEY_PARK_ID, e.getString(KEY_PARK_ID));
								map.put(KEY_PARK_LAT, e.getString(KEY_PARK_LAT));
								map.put(KEY_PARK_LONG, e.getString(KEY_PARK_LONG));
								map.put(KEY_PARK_SNIPPET, e.getString(KEY_PARK_SNIPPET));
								
								detailsForNationalParkSelected.add(map);

						} catch (JSONException e) {
							// TODO: handle exception
							Log.e("log_tag_fetching_national_parks", "Error passing data " + e.toString());
						}
			           
				} catch (Exception e) {
					// TODO: handle exception
				}
		    	
				return map;
			}
	
		    protected void onPostExecute(HashMap<String, String> result) {
				// TODO Auto-generated method stub
		    	
		    	System.out.println("KEY_PARK_NAME::chiniiiii" + result.get(KEY_PARK_NAME));
					
				 this.progressDialog.dismiss();
			}
			
	}

	@Override
	public void onTaskCompleted() {
		// TODO Auto-generated method stub
		
	}

	//***
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
//		super.onBackPressed();
		
		Intent b = new Intent(this,HomeActivity.class);
		b.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(b);
		this.finish();
	}
	
	

		
}

