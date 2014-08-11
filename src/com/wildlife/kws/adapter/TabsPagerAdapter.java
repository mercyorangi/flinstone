package com.wildlife.kws.adapter;

import com.wildlife.kws.activity.TabParkShownOnMapFragment;
import com.wildlife.kws.fragment.TabParkTopHitsInParkFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class TabsPagerAdapter extends FragmentStatePagerAdapter{
	
	private String title_passed;
	private Fragment frag_top_hits = new TabParkTopHitsInParkFragment();

	public TabsPagerAdapter(FragmentManager fm) {
		// TODO Auto-generated constructor stub
		super(fm);
		
	}
	
	public String getMyTitle(String title){
		this.title_passed = title;
		return title_passed;
	}

	
	@Override
	public Fragment getItem(int index) {
		switch (index) {
        case 0:
        	
            return frag_top_hits;
            
        case 1:
            // Games fragment activity
            return new TabParkShownOnMapFragment();
        }
 
        return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}
	
	@Override
    public CharSequence getPageTitle(int position) {
        return "Section " + (position + 1);
    }

}
