package carddeckplatform.game;





import java.util.ArrayList;
import java.util.Set;

import org.newdawn.slick.geom.Circle;

import communication.link.HostFinder;
import communication.link.HostId;
import communication.link.TcpHostFinder;
import communication.link.TcpIdListener;

import carddeckplatform.game.gameEnvironment.GameEnvironment;
import carddeckplatform.game.gameEnvironment.GameEnvironment.ConnectionType;
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
	private static Context context;
	private ViewFlipper mFlipper;
	private boolean livePosition;

	public static Context getContext(){
		return context;
	}

	private void getPrefs() {
        // Get the xml/preferences.xml preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        
        boolean useBluetooth = prefs.getBoolean("useBluetooth", false);
        boolean useLivePosition=prefs.getBoolean("useLivePosition", false);
        if(!useBluetooth){
        	GameEnvironment.get().setConnectionType(ConnectionType.TCP);
        }
        else{
        	GameEnvironment.get().setConnectionType(ConnectionType.BLUETOOTH);
        }
        if (useLivePosition){
        	livePosition=true;
        }
        
        
	}
	
	

    /** Called when the activity is first created. */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
    	//tcpIdListener=null;
    	//host=null;    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        
        context = getApplicationContext();
        
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
        GameEnvironment.get().getTcpInfo().setLocalIp(ipStr);
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

        //take user name from prefs
        username.setText(PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("userName", "Guest"));
        
        //bring curser to end of text
        username.setSelection(username.length());
        
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
            	for(final String gameName : games){
            		Button gameBtn = new Button(getApplicationContext());
            		gameBtn.setText(gameName);
            		gameBtn.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {							
							GameEnvironment.get().getPlayerInfo().setServer(true);							
							GameEnvironment.get().getTcpInfo().setHostIp("127.0.0.1");			
							GameEnvironment.get().getPlayerInfo().setUsername(username.getText().toString());
			                Intent i = new Intent(CarddeckplatformActivity.this, GamePrefsActivity.class);
			                // always use the tcp server socket since we always need it to connect the hosting player.
			                GameEnvironment.get().getTcpInfo().initServerSocket();
			                
			                
			                // TODO: Correct this.
			                String prefs = ClientDataBase.getDataBase().getGame(gameName).getPrefsName();
			                
			                i.putExtra("gamePrefs", prefs);
			                i.putExtra("gameName", gameName);			                
			                i.putExtra("livePosition", livePosition);
			                
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
            	GameEnvironment.get().getPlayerInfo().setServer(false);
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
						
						GameEnvironment.get().getPlayerInfo().setServer(false);
						GameEnvironment.get().getTcpInfo().setHostIp("192.168.1.105");
		            	GameEnvironment.get().getPlayerInfo().setUsername(username.getText().toString());
		            	
		                Intent i = new Intent(CarddeckplatformActivity.this, GameActivity.class);
		                startActivity(i);
		                dialog.dismiss();
					}    		
            	});
            	ll.addView(hostBtn);
            	
            	
            	
            	if(GameEnvironment.get().getConnectionType()==ConnectionType.TCP){
//            		HostFinder hostFinder = new TcpHostFinder((WifiManager) getSystemService(Context.WIFI_SERVICE));
//                	hosts = hostFinder.findHosts();
//	            	for(final HostId hostId : hosts){
//	            		hostBtn = new Button(getApplicationContext());
//	            		hostBtn.setText("Play " + hostId.getGameName() + " with " + hostId.getOwner());
//	            		hostBtn.setOnClickListener(new OnClickListener(){
//	    					@Override
//	    					public void onClick(View arg0) {
//	    						
//	    		            	
//	    		            	GameEnvironment.get().getPlayerInfo().setServer(false);
//	    						GameEnvironment.get().getTcpInfo().setHostIp(hostId.getAddress());
//	    		            	GameEnvironment.get().getPlayerInfo().setUsername(username.getText().toString());
//	    		            	
//	    		                Intent i = new Intent(CarddeckplatformActivity.this, GameActivity.class);
//	    		                startActivity(i);
//	    		                dialog.dismiss();
//	    					}                		
//	                	});
//	            		ll.addView(hostBtn);
//	            	}
            	}else if(GameEnvironment.get().getConnectionType()==ConnectionType.BLUETOOTH){
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
	    						
	    		            	
	    		            	GameEnvironment.get().getPlayerInfo().setServer(false);
	    						GameEnvironment.get().getBluetoothInfo().setHostDevice(device);
	    		            	GameEnvironment.get().getPlayerInfo().setUsername(username.getText().toString());
	    		            	
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
				
				Intent i = new Intent(getBaseContext(), PrefsActivity.class);
				startActivity(i);
			}
		});
        
    }
}

