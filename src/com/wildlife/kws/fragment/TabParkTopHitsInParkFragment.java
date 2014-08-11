package com.wildlife.kws.fragment;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kws.orangi.kenyawildlifeservice.R;
import com.wildlife.kws.activity.HomeActivity;
import com.wildlife.kws.activity.NationalParkSelectedActivity;
import com.wildlife.kws.helper.JSONFunctions;
import com.wildlife.kws.helper.URLEncode;
import com.wildlife.kws.interfaces.IRequestTitle;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.SimpleExpandableListAdapter;

public class TabParkTopHitsInParkFragment extends ListFragment {
	
	private String title_passed, blankis;
	private String url = NationalParkSelectedActivity.url;
	private String url_base = HomeActivity.url_base;
	@SuppressWarnings("unused")
	private HashMap<String, String> mapParkSelectedAbout;
	@SuppressWarnings("unused")
	private HashMap<String, String> mapParkSelectedActivities;
	private ExpandableListView expListView;
	private String [] childListAccomodation = new String []{"sub 1"};
	private String [] childListAbout = new String[1];
	private String [] childListActivities, childListAttractions ;
	private String [] groupList ;
	public static final String ARG_SECTION_NUMBER = "section_number";
	IRequestTitle titleListener;
	public fetchDataForParkSelected fetchDataForParkSelectedObject;
	
	public static String KEY_PARK_NAME = HomeActivity.KEY_PARK_NAME,
			KEY_PARK_DESCRIPTION = HomeActivity.KEY_PARK_DESCRIPTION,
			KEY_PARK_ID = HomeActivity.KEY_PARK_ID,
			KEY_PARK_LAT = HomeActivity.KEY_PARK_LAT,
			KEY_PARK_LONG = HomeActivity.KEY_PARK_LONG,
			KEY_PARK_SNIPPET = HomeActivity.KEY_PARK_SNIPPET,
			KEY_PARK_MAERKER_NAME= HomeActivity.KEY_PARK_MAERKER_NAME,
			
			KEY_PARK_ATTRACTION_NAME = HomeActivity.KEY_PARK_ATTRACTION_NAME,
			KEY_PARK_ATTRACTION_DESCRIPTION = HomeActivity.KEY_PARK_ATTRACTION_DESCRIPTION,
			
						
			KEY_PARK_ACTIVITY_NAME = HomeActivity.KEY_PARK_ACTIVITY_NAME,
			kEY_PARK_ACTIVITY_DESCRIPTION = HomeActivity.KEY_PARK_ACTIVITY_DESCRIPTION,
			
			
			KEY_ACCOMODATION_ID = HomeActivity.KEY_ACCOMODATION_ID,
			KEY_ACCOMODATION_NAME = HomeActivity.KEY_ACCOMODATION_NAME,
			KEY_ACCOMODATION_PRICE_RANGE = HomeActivity.KEY_ACCOMODATION_PRICE_RANGE,
			KEY_ACCOMODATION_DETAILS = HomeActivity.KEY_ACCOMODATION_DETAILS,
			KEY_ACCOMODATION_LAT = HomeActivity.KEY_ACCOMODATION_LAT,
			KEY_ACCOMODATION_LONGITUDE = HomeActivity.KEY_ACCOMODATION_LONGITUDE,
			KEY_ACCOMODATION_MARKER = HomeActivity.KEY_ACCOMODATION_MARKER
			;
	
	
	
	public TabParkTopHitsInParkFragment() {
		super();				
	}
	
	

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_tab_park_top_hits, container, false);
        expListView = (ExpandableListView) rootView.findViewById(android.R.id.list);
        Log.e("log_tag_tabs", "TAB 1 ");
        
        return rootView;
    }

	//the child list population
	public List<ArrayList<HashMap<String, String>>> createChildList(HashMap<String, String> aboutParkPassedHere, ArrayList<HashMap<String, String>> attractionsInParkPassedHere, ArrayList<HashMap<String, String>> activitiesInParkPassedHere, ArrayList<HashMap<String, String>> accomodationInParkPassedHere) {
		ArrayList<ArrayList<HashMap<String, String>>> result = new ArrayList<ArrayList<HashMap<String, String>>>();
		 childListAttractions = new String[attractionsInParkPassedHere.size()];
         for (int j = 0; j < attractionsInParkPassedHere.size(); j++) {
       	  String activity_at = attractionsInParkPassedHere.get(j).get(KEY_PARK_ATTRACTION_NAME) + 
       			  ":- "+ attractionsInParkPassedHere.get(j).get(KEY_PARK_ATTRACTION_DESCRIPTION);
       	  
       	childListAttractions[j] = activity_at;
		}
         
         childListActivities = new String[activitiesInParkPassedHere.size()];
         for (int j = 0; j < activitiesInParkPassedHere.size(); j++) {
       	  String activity_at = activitiesInParkPassedHere.get(j).get(KEY_PARK_ACTIVITY_NAME) + 
       			  ":- "+ activitiesInParkPassedHere.get(j).get(kEY_PARK_ACTIVITY_DESCRIPTION);
       	  
       	childListActivities[j] = activity_at;
		}
         
         childListAccomodation = new String[accomodationInParkPassedHere.size()];
         for (int j = 0; j < accomodationInParkPassedHere.size(); j++) {
       	  String activity_at = accomodationInParkPassedHere.get(j).get(KEY_ACCOMODATION_NAME) + 
       			  ":- "+ accomodationInParkPassedHere.get(j).get(KEY_ACCOMODATION_PRICE_RANGE);
       	  
       	childListAccomodation[j] = activity_at;
		}
		
		for( int i = 0 ; i < groupList.length  ; i++ ) { // this -15 is the number of groups(Here it's fifteen)
          /* each group need each HashMap-Here for each group we have 3 subgroups */
          ArrayList<HashMap<String, String>> secList = new ArrayList<HashMap<String, String>>();
          
          childListAbout[0] = aboutParkPassedHere.get(KEY_PARK_DESCRIPTION);
          
          if(i == 0){
        	  for( int n = 0 ; n < childListAbout.length ; n++ ) {
    	            HashMap<String, String> child = new HashMap<String, String>();
    	            child.put( "child_list", childListAbout[n] );
    	            secList.add( child );
    	          }
    	          result.add( secList );
          }
          
         
          else if (i == 1){
        	  for( int n = 0 ; n < childListAttractions.length ; n++ ) {
  	            HashMap<String, String> child = new HashMap<String, String>();
  	            child.put( "child_list", childListAttractions[n] );
  	            secList.add( child );
  	          }
  	          result.add( secList );
          }
          
          else if (i == 2){
        	  for( int n = 0 ; n < childListActivities.length ; n++ ) {
  	            HashMap<String, String> child = new HashMap<String, String>();
  	            child.put( "child_list", childListActivities[n] );
  	            secList.add( child );
  	          }
  	          result.add( secList );
          }
          
          else{
        	  for( int n = 0 ; n < childListAccomodation.length ; n++ ) {
  	            HashMap<String, String> child = new HashMap<String, String>();
  	            child.put( "child_list", childListAccomodation[n] );
  	            secList.add( child );
  	          }
  	          result.add( secList );
          }
        }
        return result;
	}

	
	// the group list population
	private List<HashMap<String, String>> createGroupList() {
		 ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
         for( int i = 0 ; i < groupList.length ; i++ ) { // 15 <span id="IL_AD11" class="IL_AD">groups</span>........
           HashMap<String, String> map = new HashMap<String, String>();
           map.put( "group_list",groupList[i] ); // the key and it's value.
           result.add( map );
         }
         return result;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		groupList = getResources().getStringArray(R.array.about_park_selected_tabs);
		mapParkSelectedAbout = new HashMap<String, String>();
		mapParkSelectedActivities = new HashMap<String, String>();
		fetchDataForParkSelectedObject = new fetchDataForParkSelected();
		
		titleListener = (IRequestTitle)getActivity();
		blankis = "params";	
		
		title_passed = titleListener.getMyTitle();
		
	     Log.e("log_passed_onActivity_created", title_passed);
	     
	     fetchDataForParkSelectedObject.execute(blankis);
	      

        expListView.setOnGroupClickListener(new OnGroupClickListener() {
			
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// TODO Auto-generated method stub
				System.out.println("Group Clicked Listener => groupPosition = " + groupPosition);
				return false;
			}
		});
        
        expListView.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				System.out.println("Inside onChildClick at groupPosition = " + groupPosition +" Child clicked at position " + childPosition);
		        return true;
			}
		});
        
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
			
			@Override
			public void onGroupExpand(int groupPosition) {
				try{
		             System.out.println("Group exapanding Listener => groupPosition = " + groupPosition);
		        }catch(Exception e){
		            System.out.println(" groupPosition Errrr +++ " + e.getMessage());
		        }
				
			}
		});
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try{
			titleListener = (IRequestTitle)activity;
			
		}catch(Exception e){
		
		}
		
		 String title = titleListener.getMyTitle();		
	     Log.e("log_passed_onAtatch", title);

	}
	
    public class fetchDataForParkSelected extends AsyncTask<String, String, Void>{
    	
    	IRequestTitle titleListener = (IRequestTitle)getActivity();
		
		String title_passed = titleListener.getMyTitle();
		
		String title_of_park_selected_1 = URLEncode.encodeURL(title_passed);
		
		String id_of_park;
		
		ArrayList<HashMap<String, String>> detailsForNationalParkSelected = new ArrayList<HashMap<String,String>>();
    	ArrayList<HashMap<String, String>> detailsForNationalParkSelectedActivities = new ArrayList<HashMap<String,String>>();
    	ArrayList<HashMap<String, String>> detailsForNationalParkSelectedAccomodation = new ArrayList<HashMap<String,String>>();
    	ArrayList<HashMap<String, String>> detailsForNationalParkSelectedB = new ArrayList<HashMap<String,String>>();
    	HashMap<String, String> mapParkSelectedAboutDIB = new HashMap<String, String>();
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
								
								mapParkSelectedAboutDIB.put(KEY_PARK_NAME, e.getString(KEY_PARK_NAME));
								mapParkSelectedAboutDIB.put(KEY_PARK_DESCRIPTION, e.getString(KEY_PARK_DESCRIPTION));
								mapParkSelectedAboutDIB.put(KEY_PARK_ID, e.getString(KEY_PARK_ID));
								mapParkSelectedAboutDIB.put(KEY_PARK_LAT, e.getString(KEY_PARK_LAT));
								mapParkSelectedAboutDIB.put(KEY_PARK_LONG, e.getString(KEY_PARK_LONG));
								mapParkSelectedAboutDIB.put(KEY_PARK_SNIPPET, e.getString(KEY_PARK_SNIPPET));
								
						} catch (JSONException e) {
							// TODO: handle exception
							Log.e("log_tag_fetching_national_parks", "Error passing data " + e.toString());
						}
			           
				} catch (Exception e) {
					// TODO: handle exception
				}
			  id_of_park = mapParkSelectedAboutDIB.get(KEY_PARK_ID);
			  
			  Log.e("NOWNOW retrieved id:: ", id_of_park);
						  
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
																		
										detailsForNationalParkSelected.add(map);
										
									}
								
						} catch (JSONException e) {
							// TODO: handle exception
							Log.e("log_tag_fetching_ATTRACTIONS", "Error passing data " + e.toString());
						}
			           
				} catch (Exception e) {
					// TODO: handle exception
				}
			  //end of for getting attractions
			  
			  //BEGIN activities in park
			  try {
		    		
		    		String jsonString = (JSONFunctions.getJSONfromURLString(url_base + "activities/" + id_of_park)).toString();
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
										map.put(KEY_PARK_ACTIVITY_NAME, e.getString(KEY_PARK_ACTIVITY_NAME));
										map.put(kEY_PARK_ACTIVITY_DESCRIPTION, e.getString(kEY_PARK_ACTIVITY_DESCRIPTION));
																	
										detailsForNationalParkSelectedActivities.add(map);
										
									}
								Log.e("up_activity_name 0", detailsForNationalParkSelectedActivities.get(0).get(KEY_PARK_ACTIVITY_NAME));
								Log.e("up_activity_dessc 0", detailsForNationalParkSelectedActivities.get(0).get(kEY_PARK_ACTIVITY_DESCRIPTION));
								
								Log.e("up_activity_name 1", detailsForNationalParkSelectedActivities.get(1).get(KEY_PARK_ACTIVITY_NAME));
								Log.e("up_activity_dessc 1", detailsForNationalParkSelectedActivities.get(1).get(kEY_PARK_ACTIVITY_DESCRIPTION));
								
						} catch (JSONException e) {
							// TODO: handle exception
							Log.e("log_tag_fetching_ACTIVITIES", "Error passing data " + e.toString());
						}
			           
				} catch (Exception e) {
					// TODO: handle exception
				}
			  //END activities in park
			  
			//START accommodation in park
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
			
			processMapReceived(mapParkSelectedAboutDIB,detailsForNationalParkSelected,detailsForNationalParkSelectedActivities,detailsForNationalParkSelectedAccomodation);

			this.progressDialog.dismiss();
		}
    	
    }

	public void processMapReceived(HashMap<String, String> parkSelectedAbout, ArrayList<HashMap<String, String>> parkSelectedAttractions, ArrayList<HashMap<String, String>> parkSelectedActivities, ArrayList<HashMap<String, String>> parkSelectedAccomodation) {
		// TODO Auto-generated method stub
		Log.e("external method", parkSelectedAbout.get(KEY_PARK_DESCRIPTION));
		
		try {
            SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(getActivity(), createGroupList(), R.layout.group_row_expandable, new String[] {"group_list"}, new int[] {R.id.row_group_name}, createChildList(parkSelectedAbout, parkSelectedAttractions,parkSelectedActivities,parkSelectedAccomodation), R.layout.group_child_row_expandable, new String[] {"child_list"}, new int[] {R.id.grp_childd});
            expListView.setAdapter(adapter);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error:: Park hits" + e.getMessage());
		}		
		
	}
	
	
	
}
