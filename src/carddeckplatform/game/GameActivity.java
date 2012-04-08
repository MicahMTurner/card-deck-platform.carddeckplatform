package carddeckplatform.game;


import java.net.InetAddress;
import java.net.Socket;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import client.controller.Controller;

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
        
//        TableView dv = new TableView(this,null);
        
        // draw the view
        setContentView(R.layout.game);
        tableview = (TableView)findViewById(R.id.TableView1);
     // necessary to transparent background!!!!
        tableview.setZOrderOnTop(true);    
        tableview.getHolder().setFormat(PixelFormat.TRANSPARENT);
        Controller.getController().setTv(tableview);
        ServerConnection.getConnection().openConnection(Controller.getController());
        
//        SampleObserver so = new SampleObserver();
//        so.setCntxt(getBaseContext());
//        Receiver rc = new TcpReceiver(9999);
//        System.out.println("Start Listening");
//        rc.reg(so);
//        
//        
//        System.out.println("WiFi address is " + getLocalHostAddress());
//        Context context = getApplicationContext();
//        CharSequence text = getLocalHostAddress();
//        int duration = Toast.LENGTH_LONG;
//
//        Toast toast = Toast.makeText(context, text, duration);
//        toast.show();
//        try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        System.out.println("Creating message");
//        Sender se = new TcpSender("localhost" , 9999);
//        SampleMessage msg = new SampleMessage();
//        msg.name = "Michael";
//        msg.messageType = "SampleMessage";
//        se.send(msg.messageType , msg);
        
    }
    @Override
	public void onWindowFocusChanged(boolean hasWindowFocus){
    	if(ipshown)
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
    	dialog.show();
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

