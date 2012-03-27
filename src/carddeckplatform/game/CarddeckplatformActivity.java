package carddeckplatform.game;




import java.io.IOException;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewFlipper;

import communication.server.Server;

public class CarddeckplatformActivity extends Activity {
	private ViewFlipper mFlipper;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        //making some wifi
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        
        String ipStr = String.format("%d.%d.%d.%d",
        		(ipAddress & 0xff),
        		(ipAddress >> 8 & 0xff),
        		(ipAddress >> 16 & 0xff),
        		(ipAddress >> 24 & 0xff));
        
        GameStatus.localIp = ipStr;
        //*********************************************
        //making widgets
        //*********************************************
        
        //making the flipper
        mFlipper = ((ViewFlipper) this.findViewById(R.id.welcomeFlipper));
        mFlipper.startFlipping();
        mFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
                R.anim.push_up_in));
        mFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
                R.anim.push_up_out));
        
        
        
        TextView tv = (TextView) findViewById(R.id.textView1);
        tv.setText("Your ip address is: " + ipStr);
        
        final EditText username = (EditText) findViewById(R.id.nickText);
        
        username.setText("user1");
        
        
        Button hostBtn = (Button) findViewById(R.id.creategameButton);
        Button joinBtn = (Button) findViewById(R.id.joingamebutton);
        hostBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            	GameStatus.isServer = true;
            	GameStatus.hostIp = "127.0.0.1";
            	GameStatus.username = username.getText().toString();
                Intent i = new Intent(CarddeckplatformActivity.this, GameActivity.class);
                new Thread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Server s = new Server();
						try {
							s.start();
						} catch (IOException e) {
							// TODO Auto-generated catch blockbb
							e.printStackTrace();
						}
					}
                	
                }).start();
                try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                startActivity(i);
                } 
             });
        
        joinBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            	GameStatus.isServer = false;
            	//making dialog to get ip
            	final Dialog dialog = new Dialog(CarddeckplatformActivity.this);
            	dialog.setContentView(R.layout.getipdialog);
            	dialog.setTitle("Host Ip Dialog");
            	TextView ip = (TextView) dialog.findViewById(R.id.getIpText);
            	Button connect= (Button) dialog.findViewById(R.id.connectButton);
            	if(connect!=null)
            	connect.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						System.out.println("finish");
						dialog.dismiss();
					}
				});
            	//making blur when button pressed
            	dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                        WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            	dialog.show();
            	
            	GameStatus.hostIp = ip.getText().toString();
            	GameStatus.username = username.getText().toString();
                Intent i = new Intent(CarddeckplatformActivity.this, GameActivity.class);
                startActivity(i);
                } 
             });
    }
}

