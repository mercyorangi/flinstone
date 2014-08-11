package com.wildlife.kws.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kws.orangi.kenyawildlifeservice.R;
import com.uservoice.uservoicesdk.Config;
import com.uservoice.uservoicesdk.UserVoice;
import com.wildlife.kws.adapter.NavigationDrawerListAdapter;
import com.wildlife.kws.fragment.HomeFragment;
import com.wildlife.kws.fragment.NationalParksFragment;
import com.wildlife.kws.helper.AlertDialogInternet;
import com.wildlife.kws.helper.ConnectionDetector;
import com.wildlife.kws.helper.JSONFunctions;
import com.wildlife.kws.model.NavigationDrawerItemModel;

public class HomeActivity extends ActionBarActivity {

	static String SHARE_TEXT = "I just shared from KWS App";

	private DrawerLayout mDrawerLayout;
	private ExpandableListView expListView;
	private ArrayList<NavigationDrawerItemModel> navDrawerItems;
	private HashMap<String, List<NavigationDrawerItemModel>> listDataChild;

	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mTitle; // stores the App Title
	private CharSequence mDrawerTitle; // stores the Navigation Drawer Title

	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private String share_text = "Download the KWS App, tembeaKenya, by following this link: \n\n http://www.google.com";

	public static String url_base = "http://www.api.maginnovate.com/";

	public static String KEY_PARK_NAME = "park_name",
			KEY_PARK_DESCRIPTION = "park_description", KEY_PARK_ID = "park_id",
			KEY_PARK_LAT = "park_latitude", KEY_PARK_LONG = "park_longitude",
			KEY_PARK_SNIPPET = "park_snipet",
			KEY_PARK_MAERKER_NAME = "park_maker_name",

			KEY_PARK_ACTIVITY_NAME = "act_name",
			KEY_PARK_ACTIVITY_DESCRIPTION = "act_description",

			KEY_PARK_ATTRACTION_NAME = "attr_name",
			KEY_PARK_ATTRACTION_DESCRIPTION = "attr_description",
			KEY_PARK_ATTRACTION_LAT = "attr_latitude",
			KEY_PARK_ATTRACTION_LONGITUDE = "attr_longitude",

			KEY_WILDLIFE_ID = "cat_id", KEY_WILDLIFE_NAME = "cat_name",
			KEY_WILDLIFE_DESCRIPTION = "cat_description",

			KEY_ACCOMODATION_ID = "acco_id",
			KEY_ACCOMODATION_NAME = "acco_name",
			KEY_ACCOMODATION_PRICE_RANGE = "acco_price_range",
			KEY_ACCOMODATION_DETAILS = "acco_details",
			KEY_ACCOMODATION_LAT = "acco_latitude",
			KEY_ACCOMODATION_LONGITUDE = "acco_longitude",
			KEY_ACCOMODATION_MARKER = "acco_marker";

	private NavigationDrawerListAdapter adapter;
	private List<NavigationDrawerItemModel> nationalParksChildItems;
	private List<NavigationDrawerItemModel> wildlifeChildItems;
	private String params;
	public fetchWildlifeData fetchWildlifeDataObject;
	public fetchDataFromDBTask fetchDataFromDBTaskObject;
	public String idWIldlifeCategory;
	private ConnectionDetector cd;
	private Boolean isInternetPresent;

	private GoogleMap gMap;

	public HomeActivity() {
		super();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_maps);// *** ***

		initialize();
		
		// set a custom shadow that overlays the main content when the
		// drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		// enable ActionBar app icon to behave as action to toggle nav
		// drawer
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				// super.onDrawerClosed(view);
				getSupportActionBar().setTitle(mTitle);
				supportInvalidateOptionsMenu();// creates call to
												// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View view) {
				// super.onDrawerOpened(view);
				getSupportActionBar().setTitle(mDrawerTitle);
				supportInvalidateOptionsMenu();// creates call to
												// onPrepareOptionsMenu()
			}

		};
		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		
		

		// Google Play Services is required for this activity
		// Check if the latest apk is installed on the device. If its not
		// installed, show a dialog that will enable the user to install it from
		// Google Play

		final int googlePlayServicesConnection = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		
		Log.e("tag_googlePlayServiceConnection_STATUS_outside", String.valueOf(googlePlayServicesConnection));
		
		if (googlePlayServicesConnection == ConnectionResult.SUCCESS) {
			
			Log.e("tag_googlePlayServiceConnection_STATUS_inIF", String.valueOf(googlePlayServicesConnection));

			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				// get data via the key
				String nameOfParkSelected = extras.getString("home");
				setTitle(nameOfParkSelected);
			}

			// checkPlayServices();

			navDrawerItems = new ArrayList<NavigationDrawerItemModel>();

			// adding nav drawer items to array list..
			// Home
			navDrawerItems.add(new NavigationDrawerItemModel(navMenuTitles[0],
					navMenuIcons.getResourceId(0, -1)));
			// National Parks
			navDrawerItems.add(new NavigationDrawerItemModel(navMenuTitles[1],
					navMenuIcons.getResourceId(1, -1)));
			// Wildlife
			navDrawerItems.add(new NavigationDrawerItemModel(navMenuTitles[2],
					navMenuIcons.getResourceId(2, -1)));
			// News
			navDrawerItems.add(new NavigationDrawerItemModel(navMenuTitles[7],
					navMenuIcons.getResourceId(3, -1)));
			// Feedback
			navDrawerItems.add(new NavigationDrawerItemModel(navMenuTitles[8],
					navMenuIcons.getResourceId(5, -1)));
			// About
			navDrawerItems.add(new NavigationDrawerItemModel(navMenuTitles[9],
					navMenuIcons.getResourceId(4, -1)));
			// Help
			navDrawerItems.add(new NavigationDrawerItemModel(navMenuTitles[10],
					navMenuIcons.getResourceId(6, -1)));

			// recycle the typedArray
			navMenuIcons.recycle();

			// ADDING Child Data National Parks
			nationalParksChildItems = new ArrayList<NavigationDrawerItemModel>();

			// ADDING Child Data Wildlife
			wildlifeChildItems = new ArrayList<NavigationDrawerItemModel>();

			// if launching for the 1st time ***

			fetchWildlifeDataObject = new fetchWildlifeData();
			fetchDataFromDBTaskObject = new fetchDataFromDBTask();

			// Connection detector class
			cd = new ConnectionDetector(getApplicationContext());

			// get Internet status
			isInternetPresent = cd.isConnectingToInternet();

			if (isInternetPresent) {
				fetchDataFromDBTaskObject.execute(params);
			} else {
				// Internet connection is not present
				// Ask user to connect to Internet
				AlertDialogInternet
						.showAlertDialog(
								HomeActivity.this,
								"No Internet Connection",
								"Please connect your device to the internet for the application to load.",
								false);
			}

			// this is a non issue log..
			Log.e("non issue", "non issue message");

			gMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
				// *** ***
				@Override
				public void onInfoWindowClick(Marker marker) {
					// TODO Auto-generated method stub

					String child_selected = marker.getTitle();
					Intent intent_national_parksIntent = new Intent(
							HomeActivity.this,
							NationalParkSelectedActivity.class);

					intent_national_parksIntent.putExtra(
							"national_park_selected", child_selected);
					startActivity(intent_national_parksIntent);

				}
			});

			listDataChild = new HashMap<String, List<NavigationDrawerItemModel>>();

			listDataChild.put(navMenuTitles[0],
					new ArrayList<NavigationDrawerItemModel>());
			listDataChild.put(navMenuTitles[1], nationalParksChildItems);
			listDataChild.put(navMenuTitles[2], wildlifeChildItems);
			listDataChild.put(navMenuTitles[7],
					new ArrayList<NavigationDrawerItemModel>());
			listDataChild.put(navMenuTitles[8],
					new ArrayList<NavigationDrawerItemModel>());
			listDataChild.put(navMenuTitles[9],
					new ArrayList<NavigationDrawerItemModel>());
			listDataChild.put(navMenuTitles[10],
					new ArrayList<NavigationDrawerItemModel>());

			// set the navigation drawer list adapter
			adapter = new NavigationDrawerListAdapter(this, navDrawerItems,
					listDataChild);

			expListView.setAdapter(adapter);

			expListView.setChoiceMode(ExpandableListView.CHOICE_MODE_SINGLE);

			expListView.setOnGroupClickListener(new OnGroupClickListener() {

				@Override
				public boolean onGroupClick(ExpandableListView parent, View v,
						int groupPosition, long id) {

					switch (groupPosition) {
					case 0: // HOME
						Intent intent_home = new Intent(HomeActivity.this,
								HomeActivity.class);
						startActivity(intent_home);
						break;
					case 1:// NATIONAL PARKS

						break;

					case 2: // WILDLIFE

						break;

					case 3: // NEWS
						Intent intent_news = new Intent(HomeActivity.this,
								NewsActivity.class);
						startActivity(intent_news);
						break;

					case 4: // FEEDBACK

						feedbackFunction();

						break;

					case 5: // ABOUT
						Intent intent_about = new Intent(HomeActivity.this,
								AboutActivity.class);
						startActivity(intent_about);
						break;

					case 6: // HELP

						final AlertDialog.Builder dialogAlert = new AlertDialog.Builder(
								HomeActivity.this);
						dialogAlert.setTitle("KWS");
						dialogAlert
								.setMessage("KWS App helps you to view all National Parks in Kenya, detailed elaborately to help you plan your safari using credible sources.\n\n"
										+ "To see: \n"
										+ "1) A list of all national parks: Click 'National Parks' on the side menu\n\n"
										+ "2) Detailed information on a park (Attractions, accomodation, wildlife): Click 'National Parks' on the side menu\n\n"
										+ "3) A list of major wildlife in all parks: Click 'Wildlife' on the side menu\n\n"
										+ "4) A list of latest news from the Kenya Wildlife Service: Click 'News' on the side menu");
						dialogAlert.setCancelable(true);
						dialogAlert.setPositiveButton("Contact Us",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										feedbackFunction();

									}
								});
						dialogAlert.setNegativeButton("OK",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										dialog.cancel();

									}
								});

						dialogAlert.create().show();

						break;

					default:
						break;
					}
					return false;
				}
			});

			expListView.setOnChildClickListener(new OnChildClickListener() {

				@Override
				public boolean onChildClick(ExpandableListView parent, View v,
						int groupPosition, int childPosition, long id) {
					// TODO Auto-generated method stub

					if (groupPosition == 1) { // NATIONAL parks

						// start it as a fragmentActivity
						Intent intent_national_parksIntent = new Intent(
								HomeActivity.this,
								NationalParkSelectedActivity.class);
						String child_selected = (String) adapter.getChild(
								groupPosition, childPosition);

						intent_national_parksIntent.putExtra(
								"national_park_selected", child_selected);
						startActivity(intent_national_parksIntent);

					} else if (groupPosition == 2) { // WILDLIFE
						// This is the Wildlife Group/Category

						idWIldlifeCategory = String.valueOf(childPosition + 1);

						gMap.clear();

						fetchWildlifeDataObject.execute(idWIldlifeCategory);

						fetchWildlifeDataObject = new fetchWildlifeData();

					}
					return false;
				}
			});

			expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

				@Override
				public void onGroupExpand(int groupPosition) {
					// TODO Auto-generated method stub

				}
			});



			if (savedInstanceState == null) {
				// if it's the 1st time created
				selectItem(0);
			}

		} else if (googlePlayServicesConnection == ConnectionResult.SERVICE_MISSING
				|| googlePlayServicesConnection == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED
				|| googlePlayServicesConnection == ConnectionResult.SERVICE_DISABLED) {
			
			Log.e("tag_googlePlayServiceConnection_STATUS_in ELSE IF", String.valueOf(googlePlayServicesConnection));
			// Show the dialog
			try {
				GooglePlayServicesUtil.getErrorDialog(googlePlayServicesConnection,HomeActivity.this, 1).show();
				
//				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com"));
//				startActivityForResult(browserIntent, 1);
			} catch (NullPointerException e) {
				// TODO: handle exception
				Log.i("no play services_ null pointer exception", e.toString());
			} catch (Exception e) {
				// TODO: handle exception
				Log.i("no play services_ general exception", e.toString());
			}
			
		}
	}

	private void checkPlayServices() {
		// TODO Auto-generated method stub

		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (resultCode == ConnectionResult.SUCCESS) {

		} else if (resultCode == ConnectionResult.SERVICE_MISSING
				|| resultCode == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED
				|| resultCode == ConnectionResult.SERVICE_DISABLED) {
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode,
					this, 1);
			dialog.show();
		}

	}

	protected void feedbackFunction() {

		// Set this up once when your application launches
		Config config = new Config("maginnovatekws.uservoice.com");
		config.setForumId(254595);

		// config.identifyUser("USER_ID", "User Name", "email@example.com");
		UserVoice.init(config, HomeActivity.this);

		// Call this wherever you want to launch UserVoice
		UserVoice.launchForum(HomeActivity.this);

	}

	public class fetchWildlifeData extends AsyncTask<String, String, Void> {

		// Toast.make

		ArrayList<HashMap<String, String>> itemsListNationalParks = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> itemsListWildlife = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> itemsListWildlifeCategory = new ArrayList<HashMap<String, String>>();

		private ProgressDialog progressDialog = new ProgressDialog(
				HomeActivity.this);
		InputStream is = null;
		String result = "";

		protected void onPreExecute() {
			progressDialog.setMessage("Fetching data... Please wait");
			progressDialog.show();
			progressDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface arg0) {
					fetchWildlifeData.this.cancel(true);
				}
			});
		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			// Toast.makeText(getApplicationContext(),
			// "param passed ID for cat:: "+ params, Toast.LENGTH_LONG).show();
			// Log.e("log_mappingwildlifeCateg_params passed", params);

			String jsonString = (JSONFunctions.getJSONfromURLString(url_base
					+ "mappingwildlife/" + idWIldlifeCategory)).toString();
			System.out
					.println("jsonString for mappingwildlife:: " + jsonString);

			JSONArray valarray = null;
			try {
				valarray = new JSONArray(jsonString);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {

				Log.e("log_tag_size", "Size:: " + valarray.length());

				for (int i = 0; i < valarray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					// *** ***
					JSONObject e = (JSONObject) valarray.getJSONObject(i);

					map.put(KEY_PARK_NAME, e.getString(KEY_PARK_NAME));
					map.put(KEY_PARK_DESCRIPTION,
							e.getString(KEY_PARK_DESCRIPTION));
					map.put(KEY_PARK_ID, e.getString(KEY_PARK_ID));
					// map.put(KEY_PARK_ATTRACTION,
					// e.getString(KEY_PARK_ATTRACTION));
					map.put(KEY_PARK_LAT, e.getString(KEY_PARK_LAT));
					map.put(KEY_PARK_LONG, e.getString(KEY_PARK_LONG));
					// map.put(KEY_PARK_ACTIVITY,
					// e.getString(KEY_PARK_ACTIVITY));
					map.put(KEY_PARK_SNIPPET, e.getString(KEY_PARK_SNIPPET));
					// map.put(KEY_PARK_MAERKER_NAME,
					// e.getString(KEY_PARK_MAERKER_NAME));***
					// *** ACTION change api name map.put(KEY_PARK_MAERKER_NAME,
					// e.getString(KEY_PARK_NAME));

					itemsListWildlifeCategory.add(map);
				}
			} catch (JSONException e) {
				// TODO: handle exception
				Log.e("log_tag_fetching_mappingwildlifeCateg",
						"Error passing data " + e.toString());
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			// super.onPostExecute(result);
			// gMap
			// Toast.makeText(getApplicationContext(), " result:: " + result
			// ,Toast.LENGTH_SHORT).show();

			for (int i = 0; i < itemsListWildlifeCategory.size(); i++) {

				int resourceId = 0;
				// create markers dynamically *** ***
				if (Integer.parseInt(idWIldlifeCategory) == 1) { // Big 5
					resourceId = R.drawable.pin_big5;

					gMap.addMarker(new MarkerOptions()
							.position(
									new LatLng(
											Double.parseDouble(itemsListWildlifeCategory
													.get(i).get(KEY_PARK_LAT)),
											Double.parseDouble(itemsListWildlifeCategory
													.get(i).get(KEY_PARK_LONG))))
							.title(itemsListWildlifeCategory.get(i).get(
									KEY_PARK_NAME))
							// put name of park here
							.snippet(
									itemsListWildlifeCategory.get(i).get(
											KEY_PARK_SNIPPET))
							.icon(BitmapDescriptorFactory
									.fromResource(resourceId)));

				} else if (Integer.parseInt(idWIldlifeCategory) == 2) { // Birds
					resourceId = R.drawable.pin_birds;

					gMap.addMarker(new MarkerOptions()
							.position(
									new LatLng(
											Double.parseDouble(itemsListWildlifeCategory
													.get(i).get(KEY_PARK_LAT)),
											Double.parseDouble(itemsListWildlifeCategory
													.get(i).get(KEY_PARK_LONG))))
							.title(itemsListWildlifeCategory.get(i).get(
									KEY_PARK_NAME))
							// put name of park here
							.snippet(
									itemsListWildlifeCategory.get(i).get(
											KEY_PARK_SNIPPET))
							.icon(BitmapDescriptorFactory
									.fromResource(resourceId)));

				} else if (Integer.parseInt(idWIldlifeCategory) == 3) { // Endangered
																		// species
					resourceId = R.drawable.pin_endangered;

					gMap.addMarker(new MarkerOptions()
							.position(
									new LatLng(
											Double.parseDouble(itemsListWildlifeCategory
													.get(i).get(KEY_PARK_LAT)),
											Double.parseDouble(itemsListWildlifeCategory
													.get(i).get(KEY_PARK_LONG))))
							.title(itemsListWildlifeCategory.get(i).get(
									KEY_PARK_NAME))
							// put name of park here
							.snippet(
									itemsListWildlifeCategory.get(i).get(
											KEY_PARK_SNIPPET))
							.icon(BitmapDescriptorFactory
									.fromResource(resourceId)));

				} else {
					resourceId = R.drawable.pin_parks;

					gMap.addMarker(new MarkerOptions()
							.position(
									new LatLng(
											Double.parseDouble(itemsListWildlifeCategory
													.get(i).get(KEY_PARK_LAT)),
											Double.parseDouble(itemsListWildlifeCategory
													.get(i).get(KEY_PARK_LONG))))
							.title(itemsListWildlifeCategory.get(i).get(
									KEY_PARK_NAME))
							// put name of park here
							.snippet(
									itemsListWildlifeCategory.get(i).get(
											KEY_PARK_SNIPPET))
							.icon(BitmapDescriptorFactory
									.fromResource(resourceId)));
				}

			}

			LatLng center_position = new LatLng(-1.283333, 36.816666);
			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(center_position) // Sets the center of the map to
												// Mountain View
					.zoom(6) // Sets the zoom
					// .bearing(90) // Sets the orientation of the camera to
					// east
					// .tilt(30) // Sets the tilt of the camera to 30 degrees
					.build(); // Creates a CameraPosition from the builder
			gMap.animateCamera(CameraUpdateFactory
					.newCameraPosition(cameraPosition));

			this.progressDialog.dismiss();

		}

	}

	class fetchDataFromDBTask extends AsyncTask<String, String, Void> {

		ArrayList<HashMap<String, String>> itemsListNationalParks = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> itemsListWildlife = new ArrayList<HashMap<String, String>>();

		private ProgressDialog progressDialog = new ProgressDialog(
				HomeActivity.this);
		InputStream is = null;
		String result = "";

		protected void onPreExecute() {
			progressDialog.setMessage("Fetching data... Please wait");
			progressDialog.show();
			progressDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface arg0) {
					fetchDataFromDBTask.this.cancel(true);
				}
			});
		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub

			String jsonString = (JSONFunctions.getJSONfromURLString(url_base
					+ "national_parks")).toString();
			System.out.println("jsonString:: " + jsonString);

			JSONArray valarray = null;
			try {
				valarray = new JSONArray(jsonString);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {

				Log.e("log_tag_size", "Size:: " + valarray.length());

				for (int i = 0; i < valarray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					// *** ***
					JSONObject e = (JSONObject) valarray.getJSONObject(i);

					map.put(KEY_PARK_NAME, e.getString(KEY_PARK_NAME));
					map.put(KEY_PARK_DESCRIPTION,
							e.getString(KEY_PARK_DESCRIPTION));
					map.put(KEY_PARK_ID, e.getString(KEY_PARK_ID));
					// map.put(KEY_PARK_ATTRACTION,
					// e.getString(KEY_PARK_ATTRACTION));
					map.put(KEY_PARK_LAT, e.getString(KEY_PARK_LAT));
					map.put(KEY_PARK_LONG, e.getString(KEY_PARK_LONG));
					// map.put(KEY_PARK_ACTIVITY,
					// e.getString(KEY_PARK_ACTIVITY));
					map.put(KEY_PARK_SNIPPET, e.getString(KEY_PARK_SNIPPET));
					// map.put(KEY_PARK_MAERKER_NAME,
					// e.getString(KEY_PARK_MAERKER_NAME));***
					map.put(KEY_PARK_MAERKER_NAME, e.getString(KEY_PARK_NAME));

					itemsListNationalParks.add(map);
				}
			} catch (JSONException e) {
				// TODO: handle exception
				Log.e("log_tag_fetching_national_parks", "Error passing data "
						+ e.toString());
			}

			String jsonString2 = (JSONFunctions.getJSONfromURLString(url_base
					+ "wildlife")).toString();
			System.out.println("jsonString:: " + jsonString2);

			JSONArray valarray2 = null;
			try {
				valarray2 = new JSONArray(jsonString2);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {

				Log.e("log_tag_size", "Size:: " + valarray2.length());

				for (int i = 0; i < valarray2.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					// *** ***
					JSONObject e = (JSONObject) valarray2.getJSONObject(i);

					map.put(KEY_WILDLIFE_ID, e.getString(KEY_WILDLIFE_ID));
					map.put(KEY_WILDLIFE_NAME, e.getString(KEY_WILDLIFE_NAME));
					map.put(KEY_WILDLIFE_DESCRIPTION,
							e.getString(KEY_WILDLIFE_DESCRIPTION));

					itemsListWildlife.add(map);
				}
			} catch (JSONException e) {
				// TODO: handle exception
				Log.e("log_tag_fetching_wildlife",
						"Error passing data " + e.toString());
				// Toast.makeText(getApplicationContext(),
				// " log_tag_fetching_wildlife:: " +
				// "Error passing data",Toast.LENGTH_SHORT).show();

			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			// super.onPostExecute(result);

			for (int i = 0; i < itemsListNationalParks.size(); i++) {
				nationalParksChildItems.add(new NavigationDrawerItemModel(
						itemsListNationalParks.get(i).get(KEY_PARK_NAME),
						navMenuIcons.getResourceId(2, -1)));

				// create markers dynamically *** ***
				gMap.addMarker(new MarkerOptions()
						.position(
								new LatLng(
										Double.parseDouble(itemsListNationalParks
												.get(i).get(KEY_PARK_LAT)),
										Double.parseDouble(itemsListNationalParks
												.get(i).get(KEY_PARK_LONG))))
						.title(itemsListNationalParks.get(i).get(KEY_PARK_NAME))
						// put name of park here
						.snippet(
								itemsListNationalParks.get(i).get(
										KEY_PARK_SNIPPET))
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.pin_parks)));

			}

			for (int i = 0; i < itemsListWildlife.size(); i++) {
				wildlifeChildItems.add(new NavigationDrawerItemModel(
						itemsListWildlife.get(i).get(KEY_WILDLIFE_NAME),
						navMenuIcons.getResourceId(2, -1)));

			}

			// gMap.setPadding(left, top, right, bottom)*** ***
			LatLng center_position = new LatLng(-1.283333, 36.816666);
			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(center_position) // Sets the center of the map to
												// Mountain View
					.zoom(6) // Sets the zoom
					// .bearing(90) // Sets the orientation of the camera to
					// east
					// .tilt(30) // Sets the tilt of the camera to 30 degrees
					.build(); // Creates a CameraPosition from the builder
			gMap.animateCamera(CameraUpdateFactory
					.newCameraPosition(cameraPosition));

			this.progressDialog.dismiss();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		menu.findItem(R.id.action_share);

		// // Associate searchable configuration with the SearchView
		// SearchManager searchManager = (SearchManager)
		// getSystemService(Context.SEARCH_SERVICE);
		// MenuItem searchItem = menu.findItem(R.id.action_searchh);
		// SearchView searchView = (SearchView)
		// MenuItemCompat.getActionView(searchItem);
		// searchView.setSearchableInfo(searchManager.getSearchableInfo(new
		// ComponentName(getApplicationContext(),
		// SearchResultsActivity.class)));

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		// Handle action bar actions click
		int id = item.getItemId();
		switch (id) {
		// case R.id.action_searchh:
		// openSearch();
		// return true;
		case R.id.action_refresh:
			openRefresh();
			return true;
		case R.id.action_about:
			openAbout();
			return true;
		case R.id.action_feedback:
			openFeedback();
			return true;
		case R.id.action_share:
			openShare();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void openShare() {
		// TODO Auto-generated method stub
		Uri uri = Uri.parse("smsto:");
		Intent myShareIntent = new Intent(Intent.ACTION_SENDTO, uri);
		myShareIntent.putExtra("sms_body", share_text);
		startActivity(myShareIntent);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(expListView);
		// menu.findItem(R.id.action_searchh).setVisible(!drawerOpen);
		menu.findItem(R.id.action_share).setVisible(!drawerOpen);
		menu.findItem(R.id.action_refresh).setVisible(!drawerOpen);
		menu.findItem(R.id.action_feedback).setVisible(!drawerOpen);
		menu.findItem(R.id.action_about).setVisible(!drawerOpen);

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private void selectItem(int position) {
		switch (position) {
		case 0:
			new HomeFragment();
			break;
		case 1:
			Intent intent = new Intent(HomeActivity.this,
					NationalParksFragment.class);

			startActivity(intent);
			break;

		default:
			break;
		}

	}

	private void initialize() {

		mTitle = mDrawerTitle = getTitle();

		navMenuTitles = getResources().getStringArray(
				R.array.navigation_drawer_titles);
		navMenuIcons = getResources().obtainTypedArray(
				R.array.navigation_drawer_icons);
		// uiCardHomeIcons =
		// getResources().obtainTypedArray(R.array.ui_card_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		gMap = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.home_map)).getMap();

		expListView = (ExpandableListView) findViewById(R.id.listexp);
	}

	private void openAbout() {
		// TODO Auto-generated method stub
		Intent intent_about = new Intent(HomeActivity.this, AboutActivity.class);
		startActivity(intent_about);
	}

	private void openRefresh() {
		// TODO Auto-generated method stub
		Intent intent_about = new Intent(HomeActivity.this, HomeActivity.class);
		startActivity(intent_about);
	}

	private void openSearch() {
		// TODO Auto-generated method stub

	}

	private void openFeedback() {

		feedbackFunction();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish();
		System.exit(0);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
