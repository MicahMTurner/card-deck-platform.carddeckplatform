package carddeckplatform.game;





import java.util.ArrayList;
import java.util.Set;

import communication.link.HostFinder;
import communication.link.HostId;
import communication.link.TcpHostFinder;
import communication.link.TcpIdListener;

import carddeckplatform.game.GameEnvironment.ConnectionType;
import client.dataBase.ClientDataBase;

import logic.host.Host;


import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
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
	

	private void getPrefs() {
        // Get the xml/preferences.xml preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        
        boolean useBluetooth = prefs.getBoolean("useBluetooth", true);
        
        if(!useBluetooth){
        	GameEnvironment.getGameEnvironment().setConnectionType(ConnectionType.TCP);
        }
        else{
        	GameEnvironment.getGameEnvironment().setConnectionType(ConnectionType.BLUETOOTH);
        }
	}
	
	
	
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
        
        
        //GameStatus.localIp = ipStr;
        GameEnvironment.getGameEnvironment().getTcpInfo().setLocalIp(ipStr);
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
            	// gets user prefs.
                getPrefs();
            	dialog.setTitle("Please choose a game");
            	Set<String> games = ClientDataBase.getDataBase().getGamesNames();
            	
            	LinearLayout ll = (LinearLayout)dialog.findViewById(R.id.gameListLayout);
            	for(final String name : games){
            		Button gameBtn = new Button(getApplicationContext());
            		gameBtn.setText(name);
            		gameBtn.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {							
							GameEnvironment.getGameEnvironment().getPlayerInfo().setServer(true);							
							GameEnvironment.getGameEnvironment().getTcpInfo().setHostIp("127.0.0.1");			
							GameEnvironment.getGameEnvironment().getPlayerInfo().setUsername(username.getText().toString());
			                Intent i = new Intent(CarddeckplatformActivity.this, GameActivity.class);
			                
			                //GameEnvironment.getGameEnvironment().getHandler().post(new Host(ClientDataBase.getDataBase().getGame(name)));
			                
			                // always use the tcp server socket since we always need it to connect the hosting player.
			                GameEnvironment.getGameEnvironment().getTcpInfo().initServerSocket();
			                // if in tcp mode start id listener.
			                if(GameEnvironment.getGameEnvironment().getConnectionType()==ConnectionType.TCP){    	
			                	if (tcpIdListener==null){
				                	tcpIdListener = new TcpIdListener(GameEnvironment.getGameEnvironment().getPlayerInfo().getUsername() , name);
				                }
				                tcpIdListener.start();
			                }
			                else if(GameEnvironment.getGameEnvironment().getConnectionType()==ConnectionType.BLUETOOTH)
			                	GameEnvironment.getGameEnvironment().getBluetoothInfo().initServerSocket();
			                	// in bluetooth mode there is no need for host id listener.
			                
			                // starts the host thread.
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
            	//GameStatus.isServer = false;
            	GameEnvironment.getGameEnvironment().getPlayerInfo().setServer(false);
            	//making dialog to get ip
            	//GameStatus.me=new Player(GameStatus.username,GameStatus.localIp);
            	final Dialog dialog = new Dialog(CarddeckplatformActivity.this);
            	dialog.setContentView(R.layout.hostlist);
            	dialog.setTitle("List of available games");
            	LinearLayout ll = (LinearLayout)dialog.findViewById(R.id.hostListLayout);
            	// gets user prefs.
                getPrefs();
            	// finds all available 
            	ArrayList<HostId> hosts;
            	
            	
            	// adds the localhost option for emulator debug (should be removed in the release version).
            	Button hostBtn = new Button(getApplicationContext());
            	hostBtn.setText("Local host");
            	hostBtn.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						GameEnvironment.getGameEnvironment().getPlayerInfo().setServer(false);
						GameEnvironment.getGameEnvironment().getTcpInfo().setHostIp("192.168.2.108");
		            	GameEnvironment.getGameEnvironment().getPlayerInfo().setUsername(username.getText().toString());
		            	
		                Intent i = new Intent(CarddeckplatformActivity.this, GameActivity.class);
		                startActivity(i);
		                dialog.dismiss();
					}    		
            	});
            	ll.addView(hostBtn);
            	
            	HostFinder hostFinder = new TcpHostFinder((WifiManager) getSystemService(Context.WIFI_SERVICE));
            	hosts = hostFinder.findHosts();
            	
            	if(GameEnvironment.getGameEnvironment().getConnectionType()==ConnectionType.TCP){
	            	for(final HostId hostId : hosts){
	            		hostBtn = new Button(getApplicationContext());
	            		hostBtn.setText("Play " + hostId.getGameName() + " with " + hostId.getOwner());
	            		hostBtn.setOnClickListener(new OnClickListener(){
	    					@Override
	    					public void onClick(View arg0) {
	    						// TODO Auto-generated method stub
	    		            	
	    		            	GameEnvironment.getGameEnvironment().getPlayerInfo().setServer(false);
	    						GameEnvironment.getGameEnvironment().getTcpInfo().setHostIp(hostId.getAddress());
	    		            	GameEnvironment.getGameEnvironment().getPlayerInfo().setUsername(username.getText().toString());
	    		            	
	    		                Intent i = new Intent(CarddeckplatformActivity.this, GameActivity.class);
	    		                startActivity(i);
	    		                dialog.dismiss();
	    					}                		
	                	});
	            		ll.addView(hostBtn);
	            	}
            	}else if(GameEnvironment.getGameEnvironment().getConnectionType()==ConnectionType.BLUETOOTH){
            		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            		if (mBluetoothAdapter == null) {
                        // Device does not support Bluetooth
                    	System.out.println("Device does not support Bluetooth");
                    }
            		
            		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            		
            		if (pairedDevices.size() > 0) {
           	         // Loop through paired devices
           	         for (final BluetoothDevice device : pairedDevices) {
           	             // Add the name and address to an array adapter to show in a ListView
           	             System.out.println("found");
           	             String name = device.getName();
           	             String Address = device.getAddress(); 
           	             hostBtn = new Button(getApplicationContext());
           	             hostBtn.setText(name);
           	             hostBtn.setOnClickListener(new OnClickListener(){
	    					@Override
	    					public void onClick(View arg0) {
	    						// TODO Auto-generated method stub
	    		            	
	    		            	GameEnvironment.getGameEnvironment().getPlayerInfo().setServer(false);
	    						GameEnvironment.getGameEnvironment().getBluetoothInfo().setHostDevice(device);
	    		            	GameEnvironment.getGameEnvironment().getPlayerInfo().setUsername(username.getText().toString());
	    		            	
	    		                Intent i = new Intent(CarddeckplatformActivity.this, GameActivity.class);
	    		                startActivity(i);
	    		                dialog.dismiss();
	    					}                		
	                	});
	            		ll.addView(hostBtn);
           	         }
           	     }
            	}
            	
            	//making blur when button pressed
            	dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                        WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            	dialog.show();

                } 
             });
        
        Button prefsBtn = (Button) findViewById(R.id.optionsButton);
        prefsBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getBaseContext(), PrefsActivity.class);
				startActivity(i);
			}
		});
        
    }
    
    public void onWindowFocusChanged(boolean hasWindowFocus){
    	if (hasWindowFocus){
    		if (tcpIdListener!=null){
    			tcpIdListener.stop();
    		}
    	}
    }
}

