package com.wildlife.kws.fragment;

import com.kws.orangi.kenyawildlifeservice.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NationalParkSelectedFragment extends Fragment{

	@SuppressWarnings("unused")
	private TextView tv;
	public NationalParkSelectedFragment() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_national_park_selected, container, false);
		tv = (TextView)rootView.findViewById(R.id.mytv1);
		return rootView;
	}
	
}
