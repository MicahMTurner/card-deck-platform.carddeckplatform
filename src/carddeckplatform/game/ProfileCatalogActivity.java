package carddeckplatform.game;

import java.util.ArrayList;

import freeplay.customization.FreePlayProfile;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProfileCatalogActivity extends Activity {
	 /** Called when the activity is first created. */
	static final int BUTTON_PADDING=20;
	@Override
	public void onResume()
	{  // After a pause OR at startup
	    super.onResume();
	    LinearLayout ll = (LinearLayout)findViewById(R.id.profileListLayout);
    	ArrayList<FreePlayProfile> profiles = FreePlayProfile.getAllProfiles();
    	final Intent i = new Intent(ProfileCatalogActivity.this, GameActivity.class);
    	ll.removeAllViews();
    	i.putExtra("gameName", getIntent().getStringExtra("gameName"));
    	i.putExtra("livePosition", getIntent().getBooleanExtra("livePosition", false));
    	
    	
    	LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
    		     LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    	
    	layoutParams.setMargins(0, 4, 0, 4);
    	
    	Button newProfBtn = new Button(getApplicationContext());
    	newProfBtn.setBackgroundDrawable( getResources().getDrawable( R.drawable.bluebutton));
    	newProfBtn.setText("New profile");
    	
    	//newProfBtn.setPadding(BUTTON_PADDING, BUTTON_PADDING, BUTTON_PADDING, BUTTON_PADDING);
    	newProfBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getBaseContext(), FreePlayCustomization.class);
				startActivity(i);
			}
			
		});
    	ll.addView(newProfBtn, layoutParams);
    	
    	
    	for(final FreePlayProfile fpp : profiles){
    		Button profileBtn = new Button(getApplicationContext());
    		//profileBtn.setPadding(BUTTON_PADDING, BUTTON_PADDING, BUTTON_PADDING, BUTTON_PADDING);
    		if (fpp!=null)
    			profileBtn.setText(fpp.getProfileName());
    		profileBtn.setBackgroundDrawable( getResources().getDrawable( R.drawable.greenbutton));
    		
    		profileBtn.setOnClickListener(new OnClickListener() {
    			
				@Override
				public void onClick(View arg0) {
					i.putExtra("profile", fpp);
					startActivity(i);
					finish();
				}
    			
    		});
    		profileBtn.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View arg0) {
					
					final Dialog dialog = new Dialog(ProfileCatalogActivity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.editdeleteprofile);
					
					
					
					
					TextView profileName = (TextView) dialog.findViewById(R.id.editRemoveProfileName);
					
					profileName.setText(fpp.getProfileName());
					
					
					Button editBtn = (Button) dialog.findViewById(R.id.editRemoveProfileEditBtn);
					Button deleteBtn = (Button) dialog.findViewById(R.id.editRemoveProfileDeleteBtn);
					
					editBtn.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent i = new Intent(getBaseContext(), FreePlayCustomization.class);
							i.putExtra("profile", fpp);
							startActivity(i);
							dialog.dismiss();
						}
					});
					
					deleteBtn.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							fpp.deleteProfile();
							dialog.dismiss();
							startActivity(getIntent()); 
							finish();
						}
					});
					dialog.show();
					return true;
				}
    			
    		});
    		
    		ll.addView(profileBtn, layoutParams);
    	}
    	
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); 
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    	
    	getWindow().setFormat(PixelFormat.RGBA_8888);
    	getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
    	setContentView(R.layout.profilecatalog);
    	setTitle("Please choose a profile");
//    	LinearLayout ll = (LinearLayout)findViewById(R.id.profileListLayout);
//    	ArrayList<FreePlayProfile> profiles = FreePlayProfile.getAllProfiles();
//    	final Intent i = new Intent(ProfileCatalogActivity.this, GameActivity.class);
//    	
//    	i.putExtra("gameName", getIntent().getStringExtra("gameName"));
//    	i.putExtra("livePosition", getIntent().getBooleanExtra("livePosition", false));
//    	
//    	for(final FreePlayProfile fpp : profiles){
//    		Button profileBtn = new Button(getApplicationContext());
//    		profileBtn.setText(fpp.getProfileName());
//    		profileBtn.setBackgroundDrawable( getResources().getDrawable( R.drawable.greenbutton));
//    		
//    		profileBtn.setOnClickListener(new OnClickListener() {
//    			
//				@Override
//				public void onClick(View arg0) {
//					i.putExtra("profile", fpp);
//					startActivity(i);
//				}
//    			
//    		});
//    		profileBtn.setOnLongClickListener(new OnLongClickListener() {
//				@Override
//				public boolean onLongClick(View arg0) {
//					Intent i = new Intent(getBaseContext(), FreePlayCustomization.class);
//					i.putExtra("profile", fpp);
//					startActivity(i);
//					return true;
//				}
//    			
//    		});
//    		
//    		ll.addView(profileBtn);
//    	}
//    	Button newProfBtn = new Button(getApplicationContext());
//    	newProfBtn.setBackgroundDrawable( getResources().getDrawable( R.drawable.bluebutton));
//    	newProfBtn.setText("New profile");
//    	newProfBtn.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				Intent i = new Intent(getBaseContext(), FreePlayCustomization.class);
//				startActivity(i);
//			}
//			
//		});
//    	ll.addView(newProfBtn);
    }
}
