 package com.wildlife.kws.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.kws.orangi.kenyawildlifeservice.R;
import com.wildlife.kws.model.NavigationDrawerItemModel;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NavigationDrawerListAdapter extends BaseExpandableListAdapter{

	private Context context;
	private ArrayList<NavigationDrawerItemModel> navDrawerItems;
	
	//child data in the form of header title, child title
	private HashMap<String, List<NavigationDrawerItemModel>> listDataChild;
	
	
	public NavigationDrawerListAdapter(Context context,ArrayList<NavigationDrawerItemModel> navDrawerItems,HashMap<String, List<NavigationDrawerItemModel>> listDataChild){
		super();
		this.context = context;
		this.navDrawerItems = navDrawerItems;
		this.listDataChild = listDataChild;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return this.navDrawerItems.get(groupPosition).getTitle();
	}
	
	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return this.navDrawerItems.size();
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		String headerTitle = (String) getGroup(groupPosition);
		
		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		convertView = mInflater.inflate(R.layout.list_item_drawer, null);
		
		ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
		TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
//		TextView txtCount = (TextView) convertView.findViewById(R.id.counter);
		
		imgIcon.setImageResource(navDrawerItems.get(groupPosition).getIcon());
		
		txtTitle.setTypeface(null, Typeface.BOLD);
		txtTitle.setText(headerTitle);
				
		return convertView;
	}
	
	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}
	
	

	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
//		listDataChild.get
		return this.listDataChild.get(this.navDrawerItems.get(groupPosition).getTitle()).get(childPosition).getTitle();
//		return "Nairobi Walk Park";
	}
	
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		
		final String childText = getChild(groupPosition, childPosition).toString();
		
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.list_item_drawer_child, null);
		}
		
		TextView txtListChild = (TextView) convertView.findViewById(R.id.listItemDrawerChild);
		txtListChild.setText(childText);
		
		return convertView;
	}
	
	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return listDataChild.get(navDrawerItems.get(groupPosition).getTitle()).size();
//		return 4;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}



//	public NavigationDrawerListAdapter(Context context, ArrayList<NavigationDrawerItemModel> navDrawerItems) {
//		this.context = context;
//		this.navDrawerItems = navDrawerItems;
//	}

//	@Override
//	public int getCount() {
//		// TODO Auto-generated method stub
//		return navDrawerItems.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//		// TODO Auto-generated method stub
//		return navDrawerItems.get(position);
//	}
//
//	@Override
//	public long getItemId(int position) {
//		// TODO Auto-generated method stub
//		return position;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		if (convertView == null) {
//			LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
//			convertView = mInflater.inflate(R.layout.list_item_drawer, null);
//		}
//		
//		ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
//		TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
//		TextView txtCount = (TextView) convertView.findViewById(R.id.counter);
//		
//		imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
//		txtTitle.setText(navDrawerItems.get(position).getTitle());
//		
//		//displaying count
//		//check if its set to vsible or not
//		if (navDrawerItems.get(position).isCounterVisible()) {
//			txtCount.setText(navDrawerItems.get(position).getCount());
//		} else {
//			txtCount.setVisibility(View.GONE);
//		}
//		return convertView;
//	}


}
