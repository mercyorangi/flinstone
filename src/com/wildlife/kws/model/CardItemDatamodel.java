package com.wildlife.kws.model;

public class CardItemDatamodel {
	
	private int image;
	private String caption;
	public CardItemDatamodel(int image, String caption) {
		
		this.image = image;
		this.caption = caption;
	}
	public int getImage() {
		return image;
	}
	public void setImage(int image) {
		this.image = image;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	

}
