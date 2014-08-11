package com.wildlife.kws.activity;

import com.kws.orangi.kenyawildlifeservice.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

public class AboutActivity extends ActionBarActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method sub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
//		String htmlText = " %s ";
//		String myData = "KWS is a Kenya Wildlife Service (KWS) mobile application that is used to provide information about National Parks in Kenya. \n\n Wildlife and other tourist attraction features in each of these parks are highlighted \n\n Tourists can plan their safari easily, quickly, using authentic consolidated information in tembeaKenya \n\n Feedback on poaching and pertinent information such as their experiences is available in a sleek, fast provision ";
//		String mimeType = "text/html";
//		String encoding = "utf-8";
//		
//		WebView webView = (WebView) findViewById(R.id.webView1);
//		webView.loadData(String.format(htmlText, myData), mimeType, encoding);
		
//		there is an option of using fragments for preference activity, good thing
//		is it gives a uniform UI with phones noraml settings...
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		int id = item.getItemId();
        switch (id) {
		case android.R.id.home:
//			getSupportFragmentManager().popBackStack();
//			getSupportActionBar().setDisplayHomeAsUpEnabled(false);
			
			Intent intent = new Intent(AboutActivity.this, HomeActivity.class);
			intent.putExtra("title", "National Parks");
			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			
			startActivity(intent);	
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}  
	}

	
}
