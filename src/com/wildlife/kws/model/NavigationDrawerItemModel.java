package com.wildlife.kws.model;

public class NavigationDrawerItemModel {
	
	private String title;
	private int icon;
	private String count = "0";
	
	//boolean to set counter visibility
	private boolean isCounterVisible = false;

	public NavigationDrawerItemModel() {
		
	}

	public NavigationDrawerItemModel(String title, int icon) {
//		super();
		this.title = title;
		this.icon = icon;
	}

	public NavigationDrawerItemModel(String title, int icon, String count,
			boolean isCounterVisible) {
//		super();
		this.title = title;
		this.icon = icon;
		this.count = count;
		this.isCounterVisible = isCounterVisible;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public boolean isCounterVisible() {
		return isCounterVisible;
	}

	public void setCounterVisible(boolean isCounterVisible) {
		this.isCounterVisible = isCounterVisible;
	}
	
	

}
