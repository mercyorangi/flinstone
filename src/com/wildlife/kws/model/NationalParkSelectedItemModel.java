package com.wildlife.kws.model;

public class NationalParkSelectedItemModel {
	private String title;
	private String description;
	private int icon;
	
	
	public NationalParkSelectedItemModel() {
		
	}


	public NationalParkSelectedItemModel(String title) {
		super();
		this.title = title;
	}


	public NationalParkSelectedItemModel(String title, String description) {
		super();
		this.title = title;
		this.description = description;
	}


	public NationalParkSelectedItemModel(String title, String description, int icon) {
		super();
		this.title = title;
		this.description = description;
		this.icon = icon;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getIcon() {
		return icon;
	}


	public void setIcon(int icon) {
		this.icon = icon;
	}
	
	

}
