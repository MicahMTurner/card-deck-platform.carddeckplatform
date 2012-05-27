package carddeckplatform.game;


import java.util.ArrayList;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import client.controller.ClientController;
import client.controller.AutoHide;
import client.controller.PositionByCompass;
import client.gui.entities.Droppable;
import communication.link.ServerConnection;

public class GameActivity extends Activity {
	private final int SPINNERPROGBAR=0;
	
	private ProgressDialog progDialog;
	private TableView tableview;	
	private PositionByCompass posByComp;
	private AutoHide gravity;
	private boolean livePosition=true;
	

	
	public GameActivity() {
		
	}
	
	@Override
	protected void onPause() {	
		super.onPause();
		posByComp.stop();
		gravity.stop();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		posByComp.stop();
		gravity.stop();
	}
	
	@Override
	protected void onResume() {	
		super.onResume();
		//posByComp.start();
		gravity.start();
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); 

        gravity = new AutoHide(getApplicationContext());
        
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        Display display = getWindowManager().getDefaultDisplay();
        GameEnvironment.getGameEnvironment().getDeviceInfo().setScreenWidth(display.getWidth());
        GameEnvironment.getGameEnvironment().getDeviceInfo().setScreenHeight(display.getHeight());
        
        //GameStatus.metrics = getApplicationContext().getResources().getDisplayMetrics();
        
        // draw the view
        setContentView(R.layout.game);
        tableview = (TableView)findViewById(R.id.TableView1);
        // necessary to transparent background!!!!
        tableview.setZOrderOnTop(true);        
        tableview.getHolder().setFormat(PixelFormat.TRANSPARENT);
        tableview.setxDimention(GameEnvironment.getGameEnvironment().getDeviceInfo().getScreenWidth());
        tableview.setyDimention(GameEnvironment.getGameEnvironment().getDeviceInfo().getScreenHeight());
        ClientController.get().setGui(tableview);
        posByComp=new PositionByCompass(getApplicationContext());
        setupGame();

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
        case SPINNERPROGBAR:
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
          	//progDialog.setContentView(R.layout.gamelistdialog); 
            return progDialog;
//        case 1:                      // Horizontal
//            progDialog = new ProgressDialog(this);
//            progDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//            progDialog.setMax(maxBarValue);
//            progDialog.setMessage("Dollars in checking account:");
//            progThread = new ProgressThread(handler);
//            progThread.start();
//            return progDialog;
        default:
            return null;
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
  
  private void setupGame(){    
	  showDialog(SPINNERPROGBAR);
	  new Thread(new ProgressBarThread(new ActionWhileWaiting() {
		
		@Override
		public void execute() {	
			//start live position feature
			posByComp.start();
			//-------CONNECT TO SERVER(HOST)------//
		    ServerConnection.getConnection().openConnection();
		    progDialog.dismiss();
		    //setup all layout prefs
		    setupLayout();
		          
		    gravity.start();
		     
			
		}
	})).start();
	 
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
    	if (GameEnvironment.getGameEnvironment().getPlayerInfo().isServer()){
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
    			posByComp.stop();
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

