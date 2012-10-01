package carddeckplatform.game.tutorial;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import carddeckplatform.game.gameEnvironment.GameEnvironment;

public class TutorialActivity extends Activity  {
	
	private TutorialView tableview;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); 

        //gravity = new AutoHide(getApplicationContext());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
       
        
        // draw the view
        tableview = new TutorialView(this);
        setContentView(tableview);
       // tableview = (TableView)findViewById(R.id.TableView1);
        // necessary to transparent background!!!!
        tableview.setZOrderOnTop(true);        
        tableview.getHolder().setFormat(PixelFormat.TRANSPARENT);
        tableview.setxDimention(GameEnvironment.get().getDeviceInfo().getScreenWidth());
        tableview.setyDimention(GameEnvironment.get().getDeviceInfo().getScreenHeight());        
        
        //ClientController.get().setGui(tableview); 
        
    }
}
