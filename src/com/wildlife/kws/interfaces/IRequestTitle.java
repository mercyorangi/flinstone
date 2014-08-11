package com.wildlife.kws.interfaces;

import java.util.HashMap;

public interface IRequestTitle {
	
	public String getMyTitle();
	
	public HashMap<String, String> getJsonForParkSelected();
	
	public  void onTaskCompleted();

}
