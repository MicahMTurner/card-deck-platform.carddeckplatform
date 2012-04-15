package carddeckplatform.game;


import java.net.InetAddress;
import java.net.Socket;

import logic.client.Game;
import logic.client.Player.Position;

import war.War;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import client.controller.ClientController;

import communication.link.ServerConnection;

public class GameActivity extends Activity {
	boolean ipshown;//if ip dialog was shown at the beggining
	TableView tableview;
	public static String getLocalHostAddress() { 
        InetAddress ipAddress=null;
        
/** Detects the default IP address of this host. */ 
        try { 
                Socket conn = new Socket("www.google.com", 80); 
                ipAddress = conn.getLocalAddress(); 
                //Log.d(TAG,"getLocalHostAddress returned: " + ipAddress.toString()); 
                conn.close(); 
        } catch (Exception e) { 
                //Log.d(TAG,"getLocalHostAddress failed: "); 
                //return new IpAddress("127.0.0.2"); 
        } 
        return ipAddress.getHostAddress(); 
} 
	
	
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
        
//        if(GameStatus.isServer)
////        	new SampleServer();
        ipshown=false;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Display display = getWindowManager().getDefaultDisplay();
        GameStatus.screenWidth = display.getWidth();
        GameStatus.screenHeight = display.getHeight();
        
//        TableView dv = new TableView(this,null);
        
        // draw the view
        setContentView(R.layout.game);
        tableview = (TableView)findViewById(R.id.TableView1);
     // necessary to transparent background!!!!
        tableview.setZOrderOnTop(true);        
        tableview.getHolder().setFormat(PixelFormat.TRANSPARENT);
        tableview.setxDimention(GameStatus.screenWidth);
        tableview.setyDimention(GameStatus.screenHeight);
        ClientController.getController().setTv(tableview);
        
        
        //-------CONNECT TO SERVER(HOST)------//
        ServerConnection.getConnection().openConnection();
        
        
        
        Position posistion=ClientController.getController().getPosition();
        ClientController.getController().buildGameLayout(getApplicationContext(), tableview, posistion);
        System.out.println("Layout was created");

    }
    @Override
	public void onWindowFocusChanged(boolean hasWindowFocus){
    	if(!GameStatus.isServer)
    		return;
    	//making the ip dialog in case its the first time that we entered
    	final Dialog dialog = new Dialog(GameActivity.this);
    	dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
    	dialog.setContentView(R.layout.showmyip);
    	dialog.setTitle("Host IP Dialog");
    	TextView myIP=(TextView)dialog.findViewById(R.id.myipnumbertext);
    	Button contin= (Button) dialog.findViewById(R.id.myipbutton);
    	System.out.println("Your ip address is:\n "+GameStatus.localIp);
		myIP.setText(GameStatus.localIp);
		contin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ipshown=true;
				dialog.dismiss();
			}
		});
    	//dialog.show();
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	menu.add(0, Menu.FIRST, Menu.NONE, "Restart").setIcon(R.drawable.restart);
    	menu.add(0, Menu.FIRST+1, Menu.NONE, "Ranking").setIcon(R.drawable.rank);
    	menu.add(0, Menu.FIRST+2, Menu.NONE, "Main Menu").setIcon(R.drawable.exit);
    	
    	
    	return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// TODO Auto-generated method stub
    	switch(item.getItemId()){
    		case Menu.FIRST:
//    			tableview.moveDraggable(0, 100, 100);
    			Toast.makeText(this, "Restart", 2000).show();
    			return true;
    		case Menu.FIRST+1:
    			Toast.makeText(this, "Ranking", 2000).show();
    			return true;
    		case Menu.FIRST+2:
    			Toast.makeText(this, "Main Menu", 2000).show();
    			finish();
    			return true;
    		default:
    			Toast.makeText(this, "NOthing", 2000).show();
    			System.out.println(item.getItemId());
    			return true;
    		
    	
    	
    	}
//    	return super.onOptionsItemSelected(item);
    }




    
}

