package carddeckplatform.game;





import java.util.ArrayList;
import java.util.Set;

import communication.link.HostFinder;
import communication.link.HostId;
import communication.link.TcpHostFinder;
import communication.link.TcpIdListener;

import client.dataBase.ClientDataBase;

import logic.host.Host;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;


public class CarddeckplatformActivity extends Activity {
	private ViewFlipper mFlipper;
	private TcpIdListener tcpIdListener;
	private Host host;
	//public static Handler h = new Handler();
	

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	tcpIdListener=null;
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
        
        
        
        
        final EditText username = (EditText) findViewById(R.id.nickText);
        
        username.setText("user1");
        
        
        Button hostBtn = (Button) findViewById(R.id.creategameButton);
        Button joinBtn = (Button) findViewById(R.id.joingamebutton);
        hostBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            	final Dialog dialog = new Dialog(CarddeckplatformActivity.this);
            	dialog.setContentView(R.layout.gamelistdialog);
            	dialog.setTitle("Please choose a game");
            	Set<String> games = ClientDataBase.getDataBase().getGamesNames();
            	
            	LinearLayout ll = (LinearLayout)dialog.findViewById(R.id.gameListLayout);
            	for(final String name : games){
            		Button gameBtn = new Button(getApplicationContext());
            		gameBtn.setText(name);
            		gameBtn.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {							
			            	GameStatus.isServer = true;
			            	GameStatus.hostIp = "127.0.0.1";
			            	GameStatus.username = username.getText().toString();
			            	//GameStatus.me=new Player(GameStatus.username,GameStatus.localIp);
			                Intent i = new Intent(CarddeckplatformActivity.this, GameActivity.class);
			                if (tcpIdListener==null){
			                	tcpIdListener=new TcpIdListener(GameStatus.username, name);			                	
			                }
			                tcpIdListener.start();		                	
			                new Thread(new Host(ClientDataBase.getDataBase().getGame(name))).start();
			                
			                startActivity(i);
			                
			                dialog.dismiss();
						}
					});
            		ll.addView(gameBtn);
            	}
            	dialog.show();
                } 
             });
        
        joinBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            	GameStatus.isServer = false;
            	//making dialog to get ip
            	//GameStatus.me=new Player(GameStatus.username,GameStatus.localIp);
            	final Dialog dialog = new Dialog(CarddeckplatformActivity.this);
            	dialog.setContentView(R.layout.hostlist);
            	dialog.setTitle("List of available games");
            	LinearLayout ll = (LinearLayout)dialog.findViewById(R.id.hostListLayout);
            	// finds all available 
            	ArrayList<HostId> hosts;
            	HostFinder hostFinder = new TcpHostFinder((WifiManager) getSystemService(Context.WIFI_SERVICE));
            	hosts = hostFinder.findHosts();
            	
            	// adds the localhost option for emulator debug (should be removed in the release version).
            	Button hostBtn = new Button(getApplicationContext());
            	hostBtn.setText("Local host");
            	hostBtn.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						GameStatus.isServer = false;
						GameStatus.hostIp = "192.168.2.108";
		            	GameStatus.username = username.getText().toString();
		            	
		                Intent i = new Intent(CarddeckplatformActivity.this, GameActivity.class);
		                startActivity(i);
		                dialog.dismiss();
					}    		
            	});
            	ll.addView(hostBtn);
            	
            	for(final HostId hostId : hosts){
            		hostBtn = new Button(getApplicationContext());
            		hostBtn.setText("Play " + hostId.getGameName() + " with " + hostId.getOwner());
            		hostBtn.setOnClickListener(new OnClickListener(){
    					@Override
    					public void onClick(View arg0) {
    						// TODO Auto-generated method stub
    						GameStatus.isServer = false;
    						GameStatus.hostIp = hostId.getAddress();
    		            	GameStatus.username = username.getText().toString();
    		                Intent i = new Intent(CarddeckplatformActivity.this, GameActivity.class);
    		                startActivity(i);
    		                dialog.dismiss();
    					}                		
                	});
            		ll.addView(hostBtn);
            	}
            	
//            	final TextView ip = (TextView) dialog.findViewById(R.id.getIpText);
//            	ip.setText("10.0.2.2");
//            	Button connect= (Button) dialog.findViewById(R.id.connectButton);
//            	connect.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						
//						GameStatus.isServer = false;
//		            	GameStatus.hostIp = ip.getText().toString();
//		            	GameStatus.username = username.getText().toString();
//		                Intent i = new Intent(CarddeckplatformActivity.this, GameActivity.class);
//		                startActivity(i);
//						System.out.println("finish");
//						dialog.dismiss();
//					}
//				});
            	//making blur when button pressed
            	dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                        WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            	dialog.show();
//            	Button retur= (Button) dialog.findViewById(R.id.returnButtonIP);
//            	retur.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						System.out.println("finish");
//						dialog.dismiss();
//					}
//				});
//            	GameStatus.hostIp = ip.getText().toString();
//            	GameStatus.username = username.getText().toString();
//            	GameStatus.me=new Player(GameStatus.username,GameStatus.localIp);
//                Intent i = new Intent(CarddeckplatformActivity.this, GameActivity.class);
//                startActivity(i);
                } 
             });
    }
    
//    @Override
//    protected void onResume() {
//    	
//    	super.onResume();
//    	tcpIdListener.stop();
//    }
    
    public void onWindowFocusChanged(boolean hasWindowFocus){
    	if (hasWindowFocus){
    		if (tcpIdListener!=null){
    			tcpIdListener.stop();
    		}
    	}
    }
}

