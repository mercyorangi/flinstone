package com.wildlife.kws.fragment;

import java.util.ArrayList;

import com.kws.orangi.kenyawildlifeservice.R;
import com.wildlife.kws.activity.HomeActivity;
import com.wildlife.kws.activity.NationalParkSelectedActivity;
import com.wildlife.kws.adapter.NationalParkSelectedListAdapter;
import com.wildlife.kws.model.NationalParkSelectedItemModel;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class NationalParksFragment extends ActionBarActivity implements OnItemClickListener{
	String[] title;
	String[] description;
	TypedArray icons;
	String KEY_TITLE = "name", 
			   KEY_DESCRIPTION = "description",
			   KEY_ICON = "icon";
//	
	NationalParkSelectedListAdapter adapter;
	ListView nationalParksList;

	public NationalParksFragment() {
		super();
		// TODO Auto-generated constructor stub
	}	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_nationalparks);
        setTitle("National Parks");
        
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        
        nationalParksList = (ListView) findViewById(R.id.listparks);
		
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
	    // get data via the key
	      String title = extras.getString("title");
	      setTitle(title);   
        }
        
		title = new String[]{"Aberdare National Park","Amboseli National Park","Nairobi National Park","Tsavo East National Park"};
		description = new String[]{"Aberdare National Park Aberdare National Park","Amboseli National ParkAmboseli National Park","Nairobi National ParkNairobi National Park","Tsavo East National ParkTsavo East National Park"};
		icons = getResources().obtainTypedArray(R.array.national_parks_icons);
		
		
		ArrayList<NationalParkSelectedItemModel> itemsList = null;

			itemsList = new ArrayList<NationalParkSelectedItemModel>();

			itemsList.add(new NationalParkSelectedItemModel(title[0], description[0], icons.getResourceId(0, -1)));
			itemsList.add(new NationalParkSelectedItemModel(title[1], description[1], icons.getResourceId(1, -1)));
			itemsList.add(new NationalParkSelectedItemModel(title[2], description[2], icons.getResourceId(2, -1)));

//      recycle the typedArray
		icons.recycle();
		
		adapter = new NationalParkSelectedListAdapter(this, itemsList);
		
		nationalParksList.setAdapter(adapter);
		nationalParksList.setOnItemClickListener(this);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		int id = item.getItemId();
        switch (id) {
		case android.R.id.home:
//			getSupportFragmentManager().popBackStack();
			
			Intent intent = new Intent(NationalParksFragment.this, HomeActivity.class);
			intent.putExtra("home", "Home");
			startActivity(intent);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}  
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,	long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(NationalParksFragment.this, NationalParkSelectedActivity.class);
		
		NationalParkSelectedItemModel im = (NationalParkSelectedItemModel) nationalParksList.getItemAtPosition(position);
		String park_selected_name = im.getTitle();
		String park_selected_description = im.getDescription();
				
		intent.putExtra("nameOfParkSelected", park_selected_name);
		intent.putExtra("nameOfParkDescription", park_selected_description);
		startActivity(intent);
		
	}

	@Override
	public void setTitle(CharSequence title) {
		getSupportActionBar().setTitle(title);
	}
	
}
