package carddeckplatform.game;


import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import javax.annotation.PostConstruct;

import president.PublicAndButtonHandler;

import utils.Button;
import utils.Pair;

import logic.client.Game;
import logic.host.Host;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import carddeckplatform.game.gameEnvironment.GameEnvironment;
import carddeckplatform.game.gameEnvironment.GameEnvironment.ConnectionType;
import client.controller.AutoHide;
import client.controller.ClientController;
import client.controller.LivePosition;
import client.dataBase.ClientDataBase;
import client.gui.entities.Droppable;

import communication.link.ServerConnection;
import communication.link.TcpIdListener;
import communication.messages.RestartMessage;
import freeplay.customization.FreePlayProfile;

public class GameActivity extends Activity {
	private final int SPINNERPROGBAR=0;
	private static Context context;
	private static Intent intent;
	public static Activity thisActivity;
	private ProgressDialog progDialog;
	private TableView tableview;
	private TcpIdListener tcpIdListener;
	private Host host;
	//private boolean disableLivePosition;
	
	//private LivePosition posByComp;
	
	

	public static Intent getMyIntent(){
		return intent;
	}

	public static Context getContext(){
		return context;
	}
	
	@Override
	protected void onPause() {	
		super.onPause();
		LivePosition.get().stop();
		AutoHide.get().stop();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();		
		LivePosition.get().stop();
		AutoHide.get().stop();
		if (host!=null){
			host.shutDown();
			host=null;
		}
		if (tcpIdListener!=null){
			tcpIdListener.stop();
		}
	}
	
	@Override
	protected void onResume() {	
		super.onResume();
		//posByComp.start();
		//AutoHide.get().start();
		
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        host=null;
        tcpIdListener=null;       
        //disableLivePosition=false;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); 

        //gravity = new AutoHide(getApplicationContext());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        context=getApplicationContext();
        intent=getIntent();
        thisActivity=this;
        
        
        //GameStatus.metrics = getApplicationContext().getResources().getDisplayMetrics();
        
        // draw the view
        setContentView(R.layout.game);
        tableview = (TableView)findViewById(R.id.TableView1);
        // necessary to transparent background!!!!
        tableview.setZOrderOnTop(true);        
        tableview.getHolder().setFormat(PixelFormat.TRANSPARENT);
        tableview.setxDimention(GameEnvironment.get().getDeviceInfo().getScreenWidth());
        tableview.setyDimention(GameEnvironment.get().getDeviceInfo().getScreenHeight());
        ClientController.get().setGui(tableview);       
        //setupGame();
        
        new GameSetup().execute(getMyIntent().getStringExtra("gameName"));
        
    }
    
//    public static void restart(){
//    	Intent intent = getMyIntent();
//    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//    	
//    	context.startActivity(intent);
//    	thisActivity.finish();
//    }
    
    private void initialServer(final String gameName) {    	
    	// added by Michael: bluetooth throws exception if the server socket isn't instantiated from main thread or handler.
    	GameEnvironment.get().getHandler().post(new Runnable() {
			@Override
			public void run() {

				if(GameEnvironment.get().getConnectionType()==ConnectionType.TCP){
		    		  //if in tcp mode start id listener.
		   			  tcpIdListener = new TcpIdListener(GameEnvironment.get().getPlayerInfo().getUsername() , gameName);
		    		  tcpIdListener.start();
		    	  }
		    	  else if(GameEnvironment.get().getConnectionType()==ConnectionType.BLUETOOTH){
		    		// in blue-tooth mode there is no need for host id listener.
		          	GameEnvironment.get().getBluetoothInfo().initServerSocket();
		    	  }
				Game game = ClientDataBase.getDataBase().getGame(gameName);
				
				if(gameName.equals("free play"))
					game.setFreePlayProfile((FreePlayProfile)getIntent().getSerializableExtra("profile"));
		    	host=new Host(game);
		    	new Thread(host).start();
		    	//cdl.countDown();
			}
			
		});
    	  
	}
    
	private void buildLayout(Pair<ArrayList<Droppable>,ArrayList<Button>> publicsAndButtons){
    	for (Droppable publicZone : publicsAndButtons.getFirst()){
    		//set public zone according to my position
    		publicZone.setPosition(publicZone.getPosition().getRelativePosition(ClientController.get().getMe().getGlobalPosition()));
    		tableview.addDroppable(publicZone);
    	}
    	for (Button button : publicsAndButtons.getSecond()){
    		button.setPosition(button.getPosition().getRelativePosition(ClientController.get().getMe().getGlobalPosition()));
    		tableview.addButton(button);
    	}
    	
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch(id) {
        	//spinner
        	case SPINNERPROGBAR:{
        		progDialog= new ProgressDialog(this);
        		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        		progDialog.setButton("Cancel", new DialogInterface.OnClickListener() {
        			
        			@Override
        			public void onClick(DialogInterface arg0, int arg1) {
        				ServerConnection.getConnection().closeConnection();
        				finish();        				
        			}
        		});
          	
        		progDialog.setMessage("Connecting...");          	 
        		return progDialog;
        	}
        default:
            return super.onCreateDialog(id);
        }
    }
    
  @Override
  public void onBackPressed() {
	  super.onBackPressed();
	  ServerConnection.getConnection().closeConnection();	
}
  
  private void setupLayout(){
	  //Position posistion=ClientController.getController().getPosition();
      ArrayList<Droppable>publics=new ArrayList<Droppable>();

      //insert public areas into publics array
      Pair<ArrayList<Droppable>,ArrayList<Button>> publicsAndButtons=ClientController.get().getLayouts();
      
      //ArrayList<Button>buttons=new ArrayList<Public>();
      
      //build the layout
      buildLayout(publicsAndButtons);      
  }
  
  private class GameSetup extends AsyncTask<String, Void, String>{

	@Override
	protected String doInBackground(String... params) {
		if (GameEnvironment.get().getPlayerInfo().isServer()){
			//CountDownLatch cdl=new CountDownLatch(1);
	        initialServer(params[0]);
	       // try {
			//	cdl.await();
			//} catch (InterruptedException e) {		
			//	e.printStackTrace();
			//}
	    }
		return setupGame();
	}
	
	@Override						
	protected void onPreExecute() {	
		showDialog(SPINNERPROGBAR);	
	}								
	
	@Override
	protected void onPostExecute(String result) {
		progDialog.dismiss();
		if (result!=null){
			//create an alert dialog
			AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);			
			builder.setMessage(result).setCancelable(false).setTitle("Connection Error");
			builder.setNeutralButton("Back", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					GameActivity.this.finish();
					
				}
			});
			
			builder.create().show();			
		}
	}
	
	  
	  
  }
  private String setupGame(){  
	  	//check if live position was enabled
	  	if (getIntent().getBooleanExtra("livePosition", false)){
			LivePosition.get().start();
		}

		//-------CONNECT TO SERVER(HOST)------//			
		try {
			ServerConnection.getConnection().openConnection();					
			   
		    //setup all layout prefs
		    setupLayout();		          
		    AutoHide.get().start();
		} catch (IOException e) {
			return e.getMessage();
		}
		return null;
		
  }
	 
	 
      
     
    	   	
    	
    



	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus){
//    	if(!GameStatus.isServer)
//    		return;
//    	//making the ip dialog in case its the first time that we entered
//    	final Dialog dialog = new Dialog(GameActivity.this);
//    	dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
//                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
//    	dialog.setContentView(R.layout.showmyip);
//    	dialog.setTitle("Host IP Dialog");
//    	TextView myIP=(TextView)dialog.findViewById(R.id.myipnumbertext);
//    	Button contin= (Button) dialog.findViewById(R.id.myipbutton);
//    	System.out.println("Your ip address is:\n "+GameStatus.localIp);
//		myIP.setText(GameStatus.localIp);
//		contin.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {	
//				dialog.dismiss();
//			}
//		});
//    	//dialog.show();
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	menu.add(0, Menu.FIRST, Menu.NONE, "Restart").setIcon(R.drawable.restart);
    	menu.add(0, Menu.FIRST+1, Menu.NONE, "Ranking").setIcon(R.drawable.rank);
    	menu.add(0, Menu.FIRST+2, Menu.NONE, "Main Menu").setIcon(R.drawable.exit);
    	if (GameEnvironment.get().getPlayerInfo().isServer()){
    		menu.add(0, Menu.FIRST+3, Menu.NONE, "Stop Live Position").setIcon(R.drawable.exit);
    	}    	
    	
    	return true;
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {    	
    	switch(item.getItemId()){
    		case Menu.FIRST:
    			Toast.makeText(this, "Restart", 2000).show();
    			ServerConnection.getConnection().send(new RestartMessage());
    			return true;
    		case Menu.FIRST+1:
    			Toast.makeText(this, "Ranking", 2000).show();
    			return true;
    		case Menu.FIRST+2:
    			Toast.makeText(this, "Main Menu", 2000).show();
    			ServerConnection.getConnection().closeConnection();
    			//add new message - shut down server
    			
    			finish();
    			return true;
    		case Menu.FIRST+3:{    			
    			LivePosition.get().stop();
    			
    			item.setTitle("Start Live Position");
    			item.setIcon(R.drawable.rank);
    			return true;
    		}
    		
    		
    		default:
    			Toast.makeText(this, "NOthing", 2000).show();
    			System.out.println(item.getItemId());
    			return true;
    		
    	
    	
    	}

    } 
}

