package com.wildlife.kws.helper;

import com.kws.orangi.kenyawildlifeservice.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

public class AlertDialogInternet extends Activity{
	
	 /**
     * Function to display simple Alert Dialog
     * @param context - application context
     * @param title - alert dialog title
     * @param message - alert message
     * @param status - success/failure (used to set icon)
     * */
    @SuppressWarnings("deprecation")
	public static void showAlertDialog(final Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
 
        // Setting Dialog Title
        alertDialog.setTitle(title);
 
        // Setting Dialog Message
        alertDialog.setMessage(message);
         
        // Setting alert dialog icon
        alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);
 
        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	
//            	Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);            	
//            	context.startActivity(intent);
            }
        });
 
        // Showing Alert Message
        alertDialog.show();
    }

}
