package com.wildlife.kws.helper;

import com.kws.orangi.kenyawildlifeservice.R;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomDialog extends Dialog implements android.view.View.OnClickListener {

		TextView where, when, details,event_title;
		ImageView cancel;

		OnMyDialogResult mDialogResult;

		public CustomDialog(Context context, String title, String event_date, String desc) {
			super(context);
			// TODO Auto-generated constructor stub
			this.requestWindowFeature(Window.FEATURE_RIGHT_ICON);
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.custom_dialog);
			
			initialize();
			
			this.setFeatureDrawableResource(Window.FEATURE_RIGHT_ICON, android.R.drawable.ic_delete);
			//where.setText(event_location);
			when.setText(event_date);
			details.setText(desc);
			event_title.setText(title);
			
			cancel.setOnClickListener(this);
			
			this.show();
		}

		private void initialize() {
		// TODO Auto-generated method stub
			//where = (TextView) findViewById(R.id.tvne);
			when = (TextView) findViewById(R.id.tvNewsDatePosted);
			details = (TextView) findViewById(R.id.tvNewsDescription);
			event_title = (TextView) findViewById(R.id.tvNewsTitle);
			cancel = (ImageView) findViewById(R.id.imgvCancel);
		}

		@Override
		public void onClick(View arg0) {
		// TODO Auto-generated method stub
			switch (arg0.getId()) {
				
				case R.id.imgvCancel:
					CustomDialog.this.dismiss();
					break;
				// mDialogResult.finish(String.valueOf(languages));
			
			}
		
		}

		public interface OnMyDialogResult {
			void finish(String result);
		}

		public void setDialogResult(OnMyDialogResult dialogResult) {
			mDialogResult = dialogResult;
		}
		
}
