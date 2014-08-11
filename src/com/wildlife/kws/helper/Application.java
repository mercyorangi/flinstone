package com.wildlife.kws.helper;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;
import com.wildlife.kws.activity.NewsActivity;

public class Application extends android.app.Application {

  public Application() {
  }

  @Override
  public void onCreate() {
    super.onCreate();

////    rovides a local datastore which can be used to store and retrieve ParseObjects, even when the network is unavailable
//    Parse.enableLocalDatastore(this);
    
	// Initialize the Parse SDK.
    Parse.initialize(this, "6JaRp7oiKBjGTj0UuAesosb20zo4VK46DGa9ka5S", "OuzLrOdDkUekC0D219AYNbAyPwW5tCnt454h5Ikg");

	// Specify an Activity to handle all pushes by default.
	PushService.setDefaultPushCallback(this, NewsActivity.class);
	ParseInstallation.getCurrentInstallation().saveInBackground();
  }
}