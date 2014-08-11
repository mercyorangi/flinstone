package com.wildlife.kws.fragment;

import com.kws.orangi.kenyawildlifeservice.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabParkAccomodationFragment extends Fragment {
	
	@SuppressWarnings("unused")
	private TextView tv;
	public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
	

	
	public TabParkAccomodationFragment() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_tab_park_accomodation, container, false);

        Log.e("log_tag_tabs", "TAB 3 ");
        return rootView;
    }
}
