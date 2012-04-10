package client.gui.screens;

import java.io.IOException;

import carddeckplatform.game.CarddeckplatformActivity;
import carddeckplatform.game.GameActivity;
import carddeckplatform.game.GameStatus;
import carddeckplatform.game.R;
import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
public class StartMenu extends Activity{
	/** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.menu);
//        
//        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
//        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//        int ipAddress = wifiInfo.getIpAddress();
//        
//        String ipStr = String.format("%d.%d.%d.%d",
//        		(ipAddress & 0xff),
//        		(ipAddress >> 8 & 0xff),
//        		(ipAddress >> 16 & 0xff),
//        		(ipAddress >> 24 & 0xff));
//        
//        GameStatus.localIp = ipStr;
//        
//        TextView tv = (TextView) findViewById(R.id.textView1);
//        tv.setText("Your ip address is: " + ipStr);
//        
//        final EditText username = (EditText) findViewById(R.id.editText2);
//        
//        username.setText("user1");
//        
//        final EditText ip = (EditText) findViewById(R.id.editText1);
//        
//        
//        Button hostBtn = (Button) findViewById(R.id.button1);
//        Button joinBtn = (Button) findViewById(R.id.button2);
//        
//        
//        hostBtn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View arg0) {
//            	GameStatus.isServer = true;
//            	GameStatus.hostIp = "127.0.0.1";
//            	GameStatus.username = username.getText().toString();
//                Intent i = new Intent(StartMenu.this, GameActivity.class);
//                new Thread(new Runnable(){
//
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						Server s = new Server();
//						try {
//							s.start();
//						} catch (IOException e) {
//							// TODO Auto-generated catch blockbb
//							e.printStackTrace();
//						}
//					}
//                	
//                }).start();
//                try {
//					Thread.sleep(2000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//                startActivity(i);
//                } 
//             });
//        
//        joinBtn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View arg0) {
//            	GameStatus.isServer = false;
//            	GameStatus.hostIp = ip.getText().toString();
//            	GameStatus.username = username.getText().toString();
//                Intent i = new Intent(StartMenu.this, GameActivity.class);
//                startActivity(i);
//                } 
//             });
//    }
}
