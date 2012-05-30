package carddeckplatform.game;


import java.io.IOException;
import java.util.ArrayList;

import logic.host.Host;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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

public class GameActivity extends Activity {
	private final int SPINNERPROGBAR=0;
	private static Context context;
	private ProgressDialog progDialog;
	private TableView tableview;
	private TcpIdListener tcpIdListener;
	private Host host;
	//private boolean disableLivePosition;
	
	//private LivePosition posByComp;
	
	

	

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
        Display display = getWindowManager().getDefaultDisplay();        
        GameEnvironment.get().getDeviceInfo().setScreenWidth(display.getWidth());
        GameEnvironment.get().getDeviceInfo().setScreenHeight(display.getHeight());
        GameEnvironment.get().getDeviceInfo().setRotationAngle(display.getRotation());
        
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
        
        new GameSetup().execute(getIntent().getStringExtra("gameName"));

    }
    
    private void initialServer(String gameName) {
    	  if(GameEnvironment.get().getConnectionType()==ConnectionType.TCP){
    		  //if in tcp mode start id listener.
   			  tcpIdListener = new TcpIdListener(GameEnvironment.get().getPlayerInfo().getUsername() , gameName);
    		  tcpIdListener.start();
    	  }
    	  else if(GameEnvironment.get().getConnectionType()==ConnectionType.BLUETOOTH){
    		// in blue-tooth mode there is no need for host id listener.
          	GameEnvironment.get().getBluetoothInfo().initServerSocket();
    	  }
    	  host=new Host(ClientDataBase.getDataBase().getGame(gameName));
    	  new Thread(host).start();		
	}
	private void buildLayout( ArrayList<Droppable> publics){
    	for (Droppable publicZone : publics){
    		//set public zone according to my position
    		publicZone.setPosition(publicZone.getPosition().getRelativePosition(ClientController.get().getMe().getGlobalPosition()));
    		tableview.addDroppable(publicZone);
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
      ClientController.get().setLayouts(publics);
      
      //ArrayList<Button>buttons=new ArrayList<Public>();
      
      //build the layout
      buildLayout(publics);      
  }
  
  private class GameSetup extends AsyncTask<String, Void, String>{

	@Override
	protected String doInBackground(String... params) {
		if (GameEnvironment.get().getPlayerInfo().isServer()){
	        initialServer(params[0]);
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
	  
		if (GameEnvironment.get().getPlayerInfo().isServer()){
			//start live position feature
			//LivePosition.get().start();
		}
		//-------CONNECT TO SERVER(HOST)------//
			
		try {
			ServerConnection.getConnection().openConnection();			
			
			//progDialog.dismiss();		    
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

