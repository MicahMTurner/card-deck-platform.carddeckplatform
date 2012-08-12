package carddeckplatform.game;

import java.util.ArrayList;

import freeplay.customization.FreePlayProfile;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class ProfileCatalogActivity extends Activity {
	 /** Called when the activity is first created. */
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	setContentView(R.layout.profilecatalog);
    	
    	LinearLayout ll = (LinearLayout)findViewById(R.id.profileListLayout);
    	ArrayList<FreePlayProfile> profiles = FreePlayProfile.getAllProfiles();
    	final Intent i = new Intent(ProfileCatalogActivity.this, GameActivity.class);
    	
    	i.putExtra("gameName", getIntent().getStringExtra("gameName"));
    	i.putExtra("livePosition", getIntent().getBooleanExtra("livePosition", false));
    	
    	for(final FreePlayProfile fpp : profiles){
    		Button profileBtn = new Button(getApplicationContext());
    		profileBtn.setText(fpp.getProfileName());
    		profileBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					i.putExtra("profile", fpp);
					startActivity(i);
				}
    			
    		});
    		profileBtn.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View arg0) {
					Intent i = new Intent(getBaseContext(), FreePlayCustomization.class);
					i.putExtra("profile", fpp);
					startActivity(i);
					return true;
				}
    			
    		});
    		
    		ll.addView(profileBtn);
    	}
    	Button newProfBtn = new Button(getApplicationContext());
    	
    	newProfBtn.setText("New profile");
    	newProfBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getBaseContext(), FreePlayCustomization.class);
				startActivity(i);
			}
			
		});
    	ll.addView(newProfBtn);
    }
}
