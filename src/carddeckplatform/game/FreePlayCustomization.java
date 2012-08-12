package carddeckplatform.game;

import java.io.File;

import client.controller.LivePosition;

import communication.link.ServerConnection;
import communication.messages.RestartMessage;

import carddeckplatform.game.gameEnvironment.GameEnvironment;
import freeplay.customization.FreePlayProfile;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FreePlayCustomization extends Activity {
	
	private EditView editView=null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); 
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.freeplaycustomization);
		
		EditView ev = (EditView)findViewById(R.id.EditView1);
		editView = ev;
		
		if((FreePlayProfile)getIntent().getSerializableExtra("profile")!=null)
			editView.setProfile((FreePlayProfile)getIntent().getSerializableExtra("profile"));
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu){
    	menu.add(0, Menu.FIRST, Menu.NONE, "Save").setIcon(R.drawable.save);
    	menu.add(0, Menu.FIRST+1, Menu.NONE, "Cancel").setIcon(R.drawable.exit);
    	    	
    	
    	return true;
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {    	
    	switch(item.getItemId()){
    		case Menu.FIRST:
    			final Dialog dialog = new Dialog(FreePlayCustomization.this);
    			dialog.setContentView(R.layout.saveprofile);
    			dialog.setTitle("Please choose a profile name");
    			dialog.show();
    			Button saveProfileButton = (Button) dialog.findViewById(R.id.saveProfileButton);
    			final EditText profileName = (EditText) dialog.findViewById(R.id.profileName);
    			if((FreePlayProfile)getIntent().getSerializableExtra("profile")!=null)
    					profileName.setText(((FreePlayProfile)getIntent().getSerializableExtra("profile")).getProfileName());
    			saveProfileButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						editView.saveProfile(profileName.getText().toString());
						dialog.dismiss();
						Toast.makeText(getApplicationContext(), "Saved...", 2000).show();
					}
				});
    			
    			//editView.saveProfile("profile1");
    			
    			return true;
    		case Menu.FIRST+1:
    			return true;
    		default:
    			Toast.makeText(this, "NOthing", 2000).show();
    			System.out.println(item.getItemId());
    			return true;
    		
    	
    	
    	}

    }
}
