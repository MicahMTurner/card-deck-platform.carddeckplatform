package carddeckplatform.game;

import android.app.Activity;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import freeplay.customization.FreePlayProfile;

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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK && editView.isShowHelp()) {
	    	editView.toggleHelp();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu){
    	menu.add(0, Menu.FIRST, Menu.NONE, "Save").setIcon(R.drawable.save);
    	menu.add(0, Menu.FIRST+1, Menu.NONE, "Auto deal").setIcon(R.drawable.rank);
    	menu.add(0, Menu.FIRST+2, Menu.NONE, "Help").setIcon(R.drawable.info);    	
    	
    	return true;
    }
    
	
	
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {    	
    	switch(item.getItemId()){
    		case Menu.FIRST:{
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
    		}
    		case Menu.FIRST+1:{
    			final String cardsToEachPlayerStr = "Cards to each player: ";
    			final Dialog dialog = new Dialog(FreePlayCustomization.this);
    			dialog.setTitle("More options");
    			dialog.setContentView(R.layout.freeplaycustomizationoptions);
    			
    			final SeekBar sk = (SeekBar) dialog.findViewById(R.id.customizationSeekBar);
    			sk.setProgress(editView.getCardsToDeal());
    			final TextView numOfCardsLabel = (TextView) dialog.findViewById(R.id.customizationNumOfCards);
    			numOfCardsLabel.setText(cardsToEachPlayerStr + sk.getProgress());
    			
    			sk.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					
					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						
						
					}
					
					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						
						
					}
					
					@Override
					public void onProgressChanged(SeekBar seekBar, int progress,
							boolean fromUser) {
						numOfCardsLabel.setText(cardsToEachPlayerStr + sk.getProgress());
						editView.setCardsToDeal(sk.getProgress());
						
					}
				});
    			
    			dialog.show();
    			return true;
    		}
    		case Menu.FIRST+2:{
//    			final Dialog dialog = new Dialog(FreePlayCustomization.this);
//    			dialog.setTitle("Help");
//    			dialog.setContentView(R.layout.textlayout);
//    			dialog.show();
//    			Toast.makeText(this, "Help", 2000).show();
    			
    			editView.toggleHelp();
    			return true;
    		}
    		default:{
    			Toast.makeText(this, "NOthing", 2000).show();
    			System.out.println(item.getItemId());
    			return true;
    		}
    	
    	
    	}

    }
}
