package carddeckplatform.game;


import java.net.InetAddress;
import java.net.Socket;

import communication.link.Receiver;
import communication.link.Sender;
import communication.link.TcpReceiver;
import communication.link.TcpSender;
import communication.messages.CardMotionMessage;
import communication.messages.Message;
import communication.messages.MessageDictionary;
import communication.messages.RegistrationMessage;
import communication.messages.SampleMessage;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class GameActivity extends Activity {
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
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Display display = getWindowManager().getDefaultDisplay(); 
        int width = display.getWidth();  // deprecated
        int height = display.getHeight();  // deprecated
        
        
        TableView dv = new TableView(this,width, height);
        
        // draw the view
        setContentView(dv);
        
        
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
}

