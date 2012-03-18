package client.gui.screens;

import java.net.InetAddress;
import java.net.Socket;

import carddeckplatform.game.DrawView;
import android.app.Activity;
import android.os.Bundle;

public class GameScreen extends Activity {
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
      
	        DrawView dv = new DrawView(this);        
	        // draw the view
	        setContentView(dv);	        
    }
}
