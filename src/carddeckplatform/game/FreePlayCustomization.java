package carddeckplatform.game;

import carddeckplatform.game.gameEnvironment.GameEnvironment;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;

public class FreePlayCustomization extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		//EditView ev = new EditView(getApplicationContext(),null);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); 
		setContentView(R.layout.freeplaycustomization);
	}
}
