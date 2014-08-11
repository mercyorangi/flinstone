package com.wildlife.kws.interfaces;

import com.wildlife.kws.adapter.BaseInflaterAdapter;

import android.view.View;
import android.view.ViewGroup;

public interface IAdapterViewInflater<T> {
	
		public View inflate(BaseInflaterAdapter<T> adapter, int pos, View convertView, ViewGroup parent);
	
}
