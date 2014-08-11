package com.wildlife.kws.adapter;

import java.util.ArrayList;
import java.util.List;

import com.wildlife.kws.interfaces.IAdapterViewInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class BaseInflaterAdapter<T> extends BaseAdapter {

	private List<T> m_items = new ArrayList<T>();
	private IAdapterViewInflater<T> m_viewInflater;
	
	
	public BaseInflaterAdapter(IAdapterViewInflater<T> m_viewInflater) {
		
		this.m_viewInflater = m_viewInflater;
	}

	public BaseInflaterAdapter(List<T> m_items,	IAdapterViewInflater<T> m_viewInflater) {
		
		this.m_items = m_items;
		this.m_viewInflater = m_viewInflater;
	}
	
	public void setViewInflater(IAdapterViewInflater<T> viewInflater, boolean notifyChange)
	{
		m_viewInflater = viewInflater;

		if (notifyChange)
			notifyDataSetChanged();
	}
	
	public void addItem(T item, boolean notifyChange)
	{
		m_items.add(item);

		if (notifyChange)
			notifyDataSetChanged();
	}
	
	public void addItems(List<T> items, boolean notifyChange)
	{
		m_items.addAll(items);

		if (notifyChange)
			notifyDataSetChanged();
	}
	
	public void clear(boolean notifyChange)
	{
		m_items.clear();

		if (notifyChange)
			notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return m_items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return getTItem(position);
	}
	
	public T getTItem(int pos)
	{
		return m_items.get(pos);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return m_viewInflater != null ? m_viewInflater.inflate(this, position, convertView, parent) : null;
	}

}
