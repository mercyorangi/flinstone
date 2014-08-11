package com.wildlife.kws.adapter;

import java.util.ArrayList;

import com.kws.orangi.kenyawildlifeservice.R;
import com.wildlife.kws.model.NationalParkSelectedItemModel;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NationalParkSelectedListAdapter extends BaseAdapter{

	private Context context;
	private ArrayList<NationalParkSelectedItemModel> nationalParkItems;
	public NationalParkSelectedListAdapter(Context contect,ArrayList<NationalParkSelectedItemModel> nationalParkItems) {
		super();
		this.context = contect;
		this.nationalParkItems = nationalParkItems;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return nationalParkItems.size();
	}
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return nationalParkItems.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.list_item_nationalparks_another, null);
		}
		
		ImageView imgIcon = (ImageView) convertView.findViewById(R.id.ivanother);
		TextView txtTitle = (TextView) convertView.findViewById(R.id.tvtitleanother);
		TextView txtdescription = (TextView) convertView.findViewById(R.id.tvdescriptionanother);
		
		imgIcon.setImageResource(nationalParkItems.get(position).getIcon());
		txtTitle.setText(nationalParkItems.get(position).getTitle());
		txtdescription.setText(nationalParkItems.get(position).getDescription());
		
		return convertView;
	}
	
	
}
