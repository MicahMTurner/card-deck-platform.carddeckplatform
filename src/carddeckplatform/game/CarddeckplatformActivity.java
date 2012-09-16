package carddeckplatform.game;





import java.util.ArrayList;
import java.util.Set;

import logic.client.Game;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import carddeckplatform.game.gameEnvironment.GameEnvironment;
import carddeckplatform.game.gameEnvironment.GameEnvironment.ConnectionType;
import carddeckplatform.game.tutorial.TutorialActivity;
import client.dataBase.ClientDataBase;

import communication.link.HostFinder;
import communication.link.HostGameDetails;


public class CarddeckplatformActivity extends Activity {
	private static Context context;

	private boolean livePosition;

	// constatnts for request code
	final private int MARKET = 50;
	final private int PLUGINRANK = 51;
	final private int FINDHOSTS = 51;
	AvailableHosts availableHosts;
	public static Context getContext() {
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
  	
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); 
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        setContentView(R.layout.menu);
        
        Display display = getWindowManager().getDefaultDisplay();        
        GameEnvironment.get().getDeviceInfo().setScreenWidth(display.getWidth());
        GameEnvironment.get().getDeviceInfo().setScreenHeight(display.getHeight());
        GameEnvironment.get().getDeviceInfo().setRotationAngle(display.getRotation());
        
        context = CarddeckplatformActivity.this;
        
        //making some wifi
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        
        String ipStr = String.format("%d.%d.%d.%d",
        		(ipAddress & 0xff),
        		(ipAddress >> 8 & 0xff),
        		(ipAddress >> 16 & 0xff),
        		(ipAddress >> 24 & 0xff));
        
        

        GameEnvironment.get().getTcpInfo().setLocalIp(ipStr);

        
        
        final EditText username = (EditText) findViewById(R.id.nickText);

        //take user name from prefs
        username.setText(PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("userName", "Guest"));
        
        //bring curser to end of text
        username.setSelection(username.length());
        
        final Button hostBtn = (Button) findViewById(R.id.creategameButton);
       
        Button joinBtn = (Button) findViewById(R.id.joingamebutton);

        hostBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            	
            	final Dialog dialog = new Dialog(CarddeckplatformActivity.this);
            	GameEnvironment.get().getPlayerInfo().setServer(true);							
				GameEnvironment.get().getTcpInfo().setHostIp("127.0.0.1");			
				GameEnvironment.get().getPlayerInfo().setUsername(username.getText().toString());
				InstalledGamesTable installedGames = new InstalledGamesTable(dialog);
            	//dialog.setContentView(new InstalledGamesTable(dialog).getView());
            	// gets user prefs.
                getPrefs();
            	//dialog.setTitle("Installed Games");
            	Set<String> games = ClientDataBase.getDataBase().getGamesNames();
            	
            	LinearLayout ll = (LinearLayout)dialog.findViewById(R.id.gameListLayout);
            	Game game;
            	for(final String gameName : games){
            		 game = ClientDataBase.getDataBase().getGame(gameName);
            		installedGames.addRow(game);
            		
//           		Button gameBtn = new Button(getApplicationContext());
//           		gameBtn.setBackgroundDrawable( getResources().getDrawable( R.drawable.graybutton));
//           		gameBtn.setText(gameName);
//            		gameBtn.setOnClickListener(new OnClickListener() {
//						
//						@Override
//						public void onClick(View v) {							
//							
//							
//							Intent i;
//							if(gameName.equals("free play"))
//								i = new Intent(CarddeckplatformActivity.this, ProfileCatalogActivity.class);
//							else
//								i = new Intent(CarddeckplatformActivity.this, GameActivity.class);
//							// always use the tcp server socket since we always need it to connect the hosting player.
//			                GameEnvironment.get().getTcpInfo().initServerSocket();
//			                
//			                i.putExtra("gameName", gameName);			                
//			                i.putExtra("livePosition", livePosition);			               
//			                startActivity(i);
//			                
//			                dialog.dismiss();
//						}
//					});
//            		ll.addView(gameBtn);
            	}
            	dialog.setContentView(installedGames.getView());
            	dialog.show();
                } 
             });       
        joinBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            	
            	GameEnvironment.get().getPlayerInfo().setServer(false);            	

            	GameEnvironment.get().getPlayerInfo().setUsername(username.getText().toString());
            	
            	//making dialog to get ip            
            	final Dialog dialog = new Dialog(CarddeckplatformActivity.this);
            	AvailableHosts availableHosts= new AvailableHosts(CarddeckplatformActivity.this,dialog);
            	//set table size
            	LayoutParams params=new LayoutParams(700, 500);
            	dialog.setContentView(availableHosts.getTable(),params);           	
            	
            	LinearLayout ll = (LinearLayout)dialog.findViewById(R.id.gameListLayout);
            	// gets user prefs.
                getPrefs();

            	if(GameEnvironment.get().getConnectionType()==ConnectionType.TCP){
            		
            		final HostFinder hostFinder = new HostFinder();
            		hostFinder.addObserver(availableHosts);
            		setDialogListeners(dialog, hostFinder);
                	hostFinder.findHosts();

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
           	            // String Address = device.getAddress(); 
           	             Button hostBtn = new Button(getApplicationContext());
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
            	
            	dialog.show();

                }

			private void setDialogListeners(Dialog dialog, final HostFinder hostFinder) {

            	//make sure host finder will stop when dialog is dismissed
            	dialog.setOnDismissListener(new OnDismissListener() {
					
					@Override
					public void onDismiss(DialogInterface dialog) {
						hostFinder.stop();
						
					}
				});
				
			} 
             });
        
        Button prefsBtn = (Button) findViewById(R.id.optionsButton);
        prefsBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent(getBaseContext(), PrefsActivity.class);
				//Intent i = new Intent(getBaseContext(), TutorialActivity.class);
				startActivity(i);
			}
		});

		Button market = (Button) findViewById(R.id.marketButton);
		market.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!haveInternet(getContext()))
					showNoConnectionDialog(getContext(), MARKET);
				else{
					Intent i = new Intent(getBaseContext(),
							MarketActivity.class);
					startActivity(i);
					
				}
			}
		});

		Button rank = (Button) findViewById(R.id.rateButton);
		rank.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!haveInternet(getContext()))
					showNoConnectionDialog(getContext(), PLUGINRANK);
				else{
					Intent i = new Intent(getBaseContext(),
							RankingActivity.class);
					startActivity(i);
					
				}
			}
		});
		
		Button score = (Button) findViewById(R.id.scoreButton);
		score.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(),
						ScoringActivity.class);
				startActivity(i);
			}
		});
		
		// START setting layout animation
		AnimationSet set = new AnimationSet(true);

		Animation animation = new AlphaAnimation(0.0f, 1.0f);
		animation.setDuration(300);
		set.addAnimation(animation);
		animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				-1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		animation.setDuration(700);
		set.addAnimation(animation);

		LayoutAnimationController controller = new LayoutAnimationController(
				set, 0.25f);
		
		LinearLayout layout=(LinearLayout) findViewById(R.id.LinearLayout1);
		layout.setLayoutAnimation(controller);
		// END setting layout animation

	}

	/**
	 * Checks if we have a valid Internet Connection on the device.
	 * 
	 * @param ctx
	 * @return True if device has internet
	 * 
	 *         Code from: http://www.androidsnippets.org/snippets/131/
	 */
	public static boolean haveInternet(Context ctx) {
		NetworkInfo info = (NetworkInfo) ((ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE))
				.getActiveNetworkInfo();

		if (info == null || !info.isConnected()) {
			return false;
		}
		if (info.isRoaming()) {
			// here is the roaming option you can change it if you want to
			// disable internet while roaming, just return false
			return true;
		}
		return true;
	}

	/**
	 * Display a dialog that user has no internet connection
	 * 
	 * @param ctx1
	 * 
	 *            Code from:
	 *            http://osdir.com/ml/Android-Developers/2009-11/msg05044.html
	 */
	public void showNoConnectionDialog(Context ctx1, final int requestCode) {
		final Context ctx = ctx1;
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setCancelable(true);
		builder.setMessage(R.string.no_connection);
		builder.setTitle(R.string.no_connection_title);
		builder.setPositiveButton(R.string.settings,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						startActivityForResult(new Intent(
								Settings.ACTION_WIRELESS_SETTINGS), requestCode);
					}
				});
		builder.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						return;
					}
				});
		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
			public void onCancel(DialogInterface dialog) {
				return;
			}
		});

		builder.show();
	}	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
//		if(resultCode==RESULT_OK){
			if(resultCode==this.MARKET){
				Intent i = new Intent(getBaseContext(),
						MarketActivity.class);
				startActivity(i);
			}
			else if(resultCode==this.PLUGINRANK){
				Intent i = new Intent(getBaseContext(),
						RankingActivity.class);
				startActivity(i);
				
			}
			else if(resultCode==this.FINDHOSTS){
				HostFinder hostFinder = new HostFinder();
				hostFinder.addObserver(availableHosts);
				hostFinder.findHosts();
			}
			
			
//		}
		
	}
	
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu){
    	menu.add(0, Menu.FIRST, Menu.NONE, "Tutorial").setIcon(R.drawable.info);	
    	return true;
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {    	
    	switch(item.getItemId()){
    		case Menu.FIRST:
    			Intent i = new Intent(getBaseContext(), TutorialActivity.class);
				startActivity(i);
    			return true;		
    		default:
    			return true;
    		
    	
    	
    	}

    }
    public class InstalledGamesTable{
    	private TableLayout table;
     	private ScrollView scrollView;
    	private int ids;
    	private Dialog dialog;
    	
    	private class enterGameClickListener implements OnClickListener{

    		@Override
    		public void onClick(View v) {    						
    			Intent i;
    			TableRow row = (TableRow)v;
    			String gameName=((TextView)row.getChildAt(0)).getText().toString().trim();
				if(gameName.equals("free play"))
					i = new Intent(CarddeckplatformActivity.this, ProfileCatalogActivity.class);
				else
					i = new Intent(CarddeckplatformActivity.this, GameActivity.class);
				// always use the tcp server socket since we always need it to connect the hosting player.
                GameEnvironment.get().getTcpInfo().initServerSocket();
                
                i.putExtra("gameName", gameName);			                
                i.putExtra("livePosition", livePosition);			               
                startActivity(i);
                
                dialog.dismiss();
    			
    		}
    		
    	}
    	
    	public View getView(){
    		return scrollView;
    	}
    	private int getId(){
    		return ids++;
    	}
    	private View getTextView(String text) {		
    		TextView textView = new TextView(context);
    		textView.setText(text);
    		textView.setGravity(Gravity.CENTER);
    		textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12); 		
    		textView.setTypeface(Typeface.SERIF, Typeface.BOLD);  
    		return textView;
    	}
    	public InstalledGamesTable(Dialog dialog) {
    	
    		ids=0;
    		scrollView = new ScrollView(context);		
    		table= new TableLayout(context);
    		this.dialog=dialog;
    		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    		
    		scrollView.setBackgroundResource(R.drawable.availablegamesbkgrnd);    		
    		table.setColumnStretchable(0,true);
    		table.setColumnStretchable(10,true);
    		table.setShrinkAllColumns(true);
    		
    		//make title row
    		TableRow rowTitle = new TableRow(context);	
    		rowTitle.setId(getId());
    		rowTitle.setGravity(Gravity.CENTER);
    		rowTitle.setPadding(0, 0, 0, 10);
    		rowTitle.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,TableRow.LayoutParams.WRAP_CONTENT));
    		TextView title= new TextView(context);
    		title.setText("Installed Games");
    		title.setGravity(Gravity.CENTER);		
    		title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);  
    	    title.setTypeface(Typeface.SERIF, Typeface.BOLD); 
    	    TableRow.LayoutParams params = new TableRow.LayoutParams();  
    	    params.span = 11;  
    	  	rowTitle.addView(title,params);
    	  	table.addView(rowTitle);
    	  	
    	  	//make mapping row
    	  	TableRow mappingRow = new TableRow(context);	  	
    	  	mappingRow.setPadding(0, 0, 0, 10);	  	
    	    params.span = 4; 
    	    mappingRow.setId(getId());
    	  	mappingRow.addView(getTextView("Game Name"));
    	  	mappingRow.addView(getTextView("Min player"),params);
    	  	mappingRow.addView(getTextView(""));
    	  	mappingRow.addView(getTextView("Max player"),params);
    	  	mappingRow.addView(getTextView("Rules"));	  	
    	  	table.addView(mappingRow);

    		scrollView.setSmoothScrollingEnabled(true);
    		scrollView.setScrollbarFadingEnabled(true);
    		scrollView.addView(table);		

		}
    	public void addRow(Game game){
    		TableRow installedGame=new TableRow(context);					
			installedGame.setId(getId());
			installedGame.setLayoutParams(new LayoutParams(TableRow.LayoutParams.FILL_PARENT,TableRow.LayoutParams.WRAP_CONTENT));
			installedGame.setOnClickListener(new enterGameClickListener());
			installedGame.setPadding(0, 0, 0, 0);
			installedGame.setBackgroundDrawable(context.getResources().getDrawable(android.R.drawable.list_selector_background));
			installedGame.setGravity(Gravity.CENTER);
			//make game name
			TextView gameId=new TextView(context);
			gameId.setText(game.toString());
			gameId.setGravity(Gravity.CENTER);
			
			gameId.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);  
			gameId.setTypeface(Typeface.SERIF, Typeface.BOLD);
			installedGame.addView(gameId);
			
			//make min players
			addPlayersDetails(game.minPlayers(),installedGame);
		
			
			//make separator
			ImageView separator=new ImageView(context);
			separator.setImageResource(R.drawable.separator);
			separator.setPadding(5, 0, 5, 0);					
			installedGame.addView(separator);
			
			//make max players
			addPlayersDetails(game.minPlayers(),installedGame);
		
			//make instructionIcon
			ImageView instructionIcon = new ImageView(context);
			instructionIcon.setOnClickListener(new showInstructionsClickListener(game.instructions()));
			instructionIcon.setImageResource(R.drawable.info);
			instructionIcon.setPadding(40, 0, 40, 0);
			instructionIcon.setId(installedGame.getId());
			//change color when pressed
			
			instructionIcon.setOnTouchListener(new View.OnTouchListener(){
			
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getActionMasked()==MotionEvent.ACTION_DOWN 
							|| event.getActionMasked()==MotionEvent.ACTION_MOVE){
						((ImageView)v).setImageResource(R.drawable.pressedinfo);
						return true;
					}else if (event.getActionMasked()==MotionEvent.ACTION_UP){
						v.performClick();
					}else{						
						((ImageView)v).setImageResource(R.drawable.info);
					}
					
					
					return true;
				}
				
			});
			
			installedGame.addView(instructionIcon);	

			table.addView(installedGame);
		}
		
	

    	private void addPlayersDetails(int players,	TableRow availableGame) {
    		int i=0;
    		for (;i<players;i++){
    			ImageView playerImage=new ImageView(context);
    			playerImage.setImageResource(R.drawable.man);					
    			availableGame.addView(playerImage);
    		}
    		for (;i<4;i++){
    			availableGame.addView(new ImageView(context));
    		}
		
    	}
    	
    	private class showInstructionsClickListener implements OnClickListener{
    		String explanation=null;
    		public showInstructionsClickListener(String explanation) {
    			this.explanation=explanation;
			}
    		@Override
    		public void onClick(View v) {
    			((ImageView)v).setImageResource(R.drawable.info);
    			final Dialog dialog=new Dialog(context);
    			dialog.setContentView(R.layout.instructionsdialog);
    			dialog.setTitle("Instructions");
    			
    			TextView instructions=(TextView) dialog.findViewById(R.id.instructionsText);
    			instructions.setText(this.explanation);
    			Button closeButton = (Button) dialog.findViewById(R.id.closingButton);    			
    			closeButton.setGravity(Gravity.CENTER);
    			closeButton.setOnClickListener(new OnClickListener() {
    				
    				@Override
    				public void onClick(View arg0) {
    					dialog.dismiss();
    				}
    			});

    			dialog.show();
    			
    			
    		}
    		
    	}


    }
}

