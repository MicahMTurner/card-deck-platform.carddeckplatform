package carddeckplatform.game;


import java.net.InetAddress;
import java.net.Socket;

import client.controller.Controller;

import communication.link.Receiver;
import communication.link.Sender;
import communication.link.ServerConnection;
import communication.link.TcpReceiver;
import communication.link.TcpSender;
import communication.messages.CardMotionMessage;
import communication.messages.Message;
import communication.messages.MessageDictionary;
import communication.messages.RegistrationMessage;
import communication.messages.SampleMessage;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity {
	boolean ipshown;//if ip dialog was shown at the beggining
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
        TableView tv = (TableView)findViewById(R.id.TableView1);
        Controller.getController().setTv(tv);
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
}

