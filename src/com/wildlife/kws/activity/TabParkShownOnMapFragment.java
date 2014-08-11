package com.wildlife.kws.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kws.orangi.kenyawildlifeservice.R;
import com.wildlife.kws.helper.JSONFunctions;
import com.wildlife.kws.helper.URLEncode;
import com.wildlife.kws.interfaces.IRequestTitle;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TabParkShownOnMapFragment extends Fragment {
 
	public static final String ARG_SECTION_NUMBER = "section_number";
	public GoogleMap gMap;
	
	private String url = NationalParkSelectedActivity.url;
	private String url_base = HomeActivity.url_base;
	
	public static String KEY_PARK_NAME = HomeActivity.KEY_PARK_NAME,
			KEY_PARK_DESCRIPTION = HomeActivity.KEY_PARK_DESCRIPTION,
			KEY_PARK_ID = HomeActivity.KEY_PARK_ID,
			KEY_PARK_LAT = HomeActivity.KEY_PARK_LAT,
			KEY_PARK_LONG = HomeActivity.KEY_PARK_LONG,
			KEY_PARK_SNIPPET = HomeActivity.KEY_PARK_SNIPPET,
			KEY_PARK_MAERKER_NAME= HomeActivity.KEY_PARK_MAERKER_NAME,
			
			KEY_PARK_ATTRACTION_NAME = HomeActivity.KEY_PARK_ATTRACTION_NAME,
			KEY_PARK_ATTRACTION_DESCRIPTION = HomeActivity.KEY_PARK_ATTRACTION_DESCRIPTION,
			KEY_PARK_ATTRACTION_LAT = HomeActivity.KEY_PARK_ATTRACTION_LAT,
			KEY_PARK_ATTRACTION_LONGITUDE = HomeActivity.KEY_PARK_ATTRACTION_LONGITUDE,
			
			KEY_ACCOMODATION_NAME = HomeActivity.KEY_ACCOMODATION_NAME,
			KEY_ACCOMODATION_PRICE_RANGE = HomeActivity.KEY_ACCOMODATION_PRICE_RANGE,
			KEY_ACCOMODATION_DETAILS = HomeActivity.KEY_ACCOMODATION_DETAILS,
			KEY_ACCOMODATION_LONGITUDE = HomeActivity.KEY_ACCOMODATION_LONGITUDE,
			KEY_ACCOMODATION_LAT = HomeActivity.KEY_ACCOMODATION_LAT;
	
	public fetchDataForParkSelected fetchSelectedParkDataObject;
	String params = null;
	
    public TabParkShownOnMapFragment() {
		super();
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_tab_park_on_map, container, false);
        
        Log.e("log_tag_tabs", "TAB 2 ");
        return rootView;
    }

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
        gMap = ((SupportMapFragment)  getFragmentManager().findFragmentById(R.id.map)).getMap();
        fetchSelectedParkDataObject = new fetchDataForParkSelected();
        
        gMap.clear();
        
        fetchSelectedParkDataObject.execute(params);
       
        fetchSelectedParkDataObject = new fetchDataForParkSelected();        

	}
	
	
    class fetchDataForParkSelected extends AsyncTask<String, String, Void>{

    	IRequestTitle titleListener = (IRequestTitle)getActivity();
				
		String title_passed = titleListener.getMyTitle();		
		String title_of_park_selected_1 = URLEncode.encodeURL(title_passed);		
		String id_of_park;
    	ArrayList<HashMap<String, String>> detailsForNationalParkSelectedAttractions = new ArrayList<HashMap<String,String>>();
    	ArrayList<HashMap<String, String>> detailsForNationalParkSelectedActivities = new ArrayList<HashMap<String,String>>();
    	ArrayList<HashMap<String, String>> detailsForNationalParkSelectedAccomodation = new ArrayList<HashMap<String,String>>();
    	ArrayList<HashMap<String, String>> detailsForNationalParkSelectedB = new ArrayList<HashMap<String,String>>();
    	HashMap<String, String> mapParkSelected = new HashMap<String, String>();
    	HashMap<String, String> mapParkSelectedActivitiesDIB = new HashMap<String, String>();
    	
    	 private ProgressDialog progressDialog = new ProgressDialog(getActivity());
    	    InputStream is = null ;
    	    String result = "";
    	    protected void onPreExecute() {
    	       progressDialog.setMessage("Fetching data... Please wait");
    	       progressDialog.show();
    	       progressDialog.setOnCancelListener(new OnCancelListener() {
		    	 @Override
		    	  public void onCancel(DialogInterface arg0) {
		    		 fetchDataForParkSelected.this.cancel(true);
		    	    }
		    	 });
    	     }
    	
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			  try {
		    		
		    		String jsonString = (JSONFunctions.getJSONfromURLString(url + title_of_park_selected_1)).toString();
					System.out.println("SHOWMAP jsonString for national_parks_byname:: " + jsonString);
					
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
								
								mapParkSelected.put(KEY_PARK_NAME, e.getString(KEY_PARK_NAME));
								mapParkSelected.put(KEY_PARK_DESCRIPTION, e.getString(KEY_PARK_DESCRIPTION));
								mapParkSelected.put(KEY_PARK_ID, e.getString(KEY_PARK_ID));
								mapParkSelected.put(KEY_PARK_LAT, e.getString(KEY_PARK_LAT));
								mapParkSelected.put(KEY_PARK_LONG, e.getString(KEY_PARK_LONG));
								mapParkSelected.put(KEY_PARK_SNIPPET, e.getString(KEY_PARK_SNIPPET));
								
						} catch (JSONException e) {
							// TODO: handle exception
							Log.e("log_tag_fetching_national_parks", "Error passing data " + e.toString());
						}
			           
				} catch (Exception e) {
					// TODO: handle exception
				}
			  id_of_park = mapParkSelected.get(KEY_PARK_ID);
			  
			  Log.e("TAB MAPS retrieved id:: ", id_of_park);
			
			  
			  //for getting attractions
			  try {
		    		
		    		String jsonString = (JSONFunctions.getJSONfromURLString(url_base + "attractions/" + id_of_park)).toString();
					System.out.println("jsonString for attractions_in_this_park:: " + jsonString);
					
					JSONArray valarray = null;
					try {
						valarray = new JSONArray(jsonString);
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						
						Log.e("log_tag_size", "Size of spec arary:: " + valarray.length());
								
								for (int i = 0; i < valarray.length(); i++) {
										HashMap<String, String> map = new HashMap<String, String>();
										//*** ***
										JSONObject e = (JSONObject) valarray.getJSONObject(i);
										map.put(KEY_PARK_ATTRACTION_NAME, e.getString(KEY_PARK_ATTRACTION_NAME));
										map.put(KEY_PARK_ATTRACTION_DESCRIPTION, e.getString(KEY_PARK_ATTRACTION_DESCRIPTION));
										map.put(KEY_PARK_ATTRACTION_LAT, e.getString(KEY_PARK_ATTRACTION_LAT));
										map.put(KEY_PARK_ATTRACTION_LONGITUDE, e.getString(KEY_PARK_ATTRACTION_LONGITUDE));
																														
										detailsForNationalParkSelectedAttractions.add(map);
										
									}
								
						} catch (JSONException e) {
							// TODO: handle exception
							Log.e("log_tag_fetching_ATTRACTIONS", "Error passing data " + e.toString());
						}
			           
				} catch (Exception e) {
					// TODO: handle exception
				}
			  //end of for getting attractions
		
			  
			//START accomodation in park
			  try {
		    		
		    		String jsonString = (JSONFunctions.getJSONfromURLString(url_base + "accommodation/" + id_of_park)).toString();
					System.out.println("jsonString for activities_in_this_park:: " + jsonString);
					
					JSONArray valarray = null;
					try {
						valarray = new JSONArray(jsonString);
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						
						Log.e("log_tag_size", "Size of spec arary:: " + valarray.length());
								
								for (int i = 0; i < valarray.length(); i++) {
										HashMap<String, String> map = new HashMap<String, String>();
									
										JSONObject e = (JSONObject) valarray.getJSONObject(i);
										map.put(KEY_ACCOMODATION_NAME, e.getString(KEY_ACCOMODATION_NAME));
										map.put(KEY_ACCOMODATION_PRICE_RANGE, e.getString(KEY_ACCOMODATION_PRICE_RANGE));
										map.put(KEY_ACCOMODATION_DETAILS, e.getString(KEY_ACCOMODATION_DETAILS));
										map.put(KEY_ACCOMODATION_LAT, e.getString(KEY_ACCOMODATION_LAT));
										map.put(KEY_ACCOMODATION_LONGITUDE, e.getString(KEY_ACCOMODATION_LONGITUDE));
																			
										detailsForNationalParkSelectedAccomodation.add(map);
										
									}
								
						} catch (JSONException e) {
							// TODO: handle exception
							Log.e("log_tag_fetching_ACCOMODATION", "Error passing data " + e.toString());
						}
			           
				} catch (Exception e) {
					// TODO: handle exception
				}
			//END accommodation in park
			
			return null;			
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			
			processMapReceived(mapParkSelected,detailsForNationalParkSelectedAttractions,detailsForNationalParkSelectedAccomodation);

			this.progressDialog.dismiss();
		}
    	
    }


	public void processMapReceived(
			HashMap<String, String> mapParkSelected,
			ArrayList<HashMap<String, String>> detailsForNationalParkSelectedAttractions,
			ArrayList<HashMap<String, String>> detailsForNationalParkSelectedAccomodation) {
		// TODO Auto-generated method stub
		
			//marker for park
			gMap.addMarker(new MarkerOptions()
					.position(new LatLng(Double.parseDouble(mapParkSelected.get(KEY_PARK_LAT)), Double.parseDouble(mapParkSelected.get(KEY_PARK_LONG))))
					.title(mapParkSelected.get(KEY_PARK_NAME))//put name of park here
					.snippet(mapParkSelected.get(KEY_PARK_SNIPPET))
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_parks)));
			
			
			//markers for ATTRACTIONS
			for (int i = 0; i < detailsForNationalParkSelectedAttractions.size(); i++) {
				
				//create markers dynamically *** ***
				gMap.addMarker(new MarkerOptions()
						.position(new LatLng(Double.parseDouble(detailsForNationalParkSelectedAttractions.get(i).get(KEY_PARK_ATTRACTION_LAT)), Double.parseDouble(detailsForNationalParkSelectedAttractions.get(i).get(KEY_PARK_ATTRACTION_LONGITUDE))))
						.title(detailsForNationalParkSelectedAttractions.get(i).get(KEY_PARK_ATTRACTION_NAME))//put name of park here
						.snippet(detailsForNationalParkSelectedAttractions.get(i).get(KEY_PARK_ATTRACTION_DESCRIPTION))
						.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_attraction)));		
				
			}
			
			//markers for ACCOMODATION
			for (int i = 0; i < detailsForNationalParkSelectedAccomodation.size(); i++) {	
				
				//create markers dynamically *** ***
				gMap.addMarker(new MarkerOptions()
						.position(new LatLng(Double.parseDouble(detailsForNationalParkSelectedAccomodation.get(i).get(KEY_ACCOMODATION_LAT)), Double.parseDouble(detailsForNationalParkSelectedAccomodation.get(i).get(KEY_ACCOMODATION_LONGITUDE))))
						.title(detailsForNationalParkSelectedAccomodation.get(i).get(KEY_ACCOMODATION_NAME))//put name of park here
						.snippet(detailsForNationalParkSelectedAccomodation.get(i).get(KEY_ACCOMODATION_PRICE_RANGE) + "\n" + detailsForNationalParkSelectedAccomodation.get(i).get(KEY_ACCOMODATION_DETAILS))
						.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_accomodation)));		
				
			}
	
			LatLng center_position = new LatLng(Double.parseDouble(mapParkSelected.get(KEY_PARK_LAT)), Double.parseDouble(mapParkSelected.get(KEY_PARK_LONG)));
			CameraPosition cameraPosition = new CameraPosition.Builder()
					    .target(center_position)      // Sets the center of the map to Mountain View
					    .zoom(10)                   // Sets the zoom
					 //   .bearing(90)                // Sets the orientation of the camera to east
					  //  .tilt(30)                   // Sets the tilt of the camera to 30 degrees
					    .build();                   // Creates a CameraPosition from the builder
			gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
			
			gMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {			

				@Override
				public void onInfoWindowClick(Marker arg0) {
					// TODO Auto-generated method stub
					final AlertDialog.Builder dialogAlert = new AlertDialog.Builder(getActivity());
					dialogAlert.setTitle(arg0.getTitle());
					dialogAlert.setMessage(arg0.getSnippet());
					dialogAlert.setCancelable(true);
					
					dialogAlert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
						
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
