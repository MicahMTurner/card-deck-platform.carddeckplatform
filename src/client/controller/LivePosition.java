package client.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import carddeckplatform.game.GameActivity;
import carddeckplatform.game.gameEnvironment.GameEnvironment;
import utils.Pair;
import utils.Position;
import utils.Position.Player;
import communication.actions.LivePositionChangedAction;
import communication.link.ServerConnection;
import communication.messages.Message;
import communication.server.ConnectionsManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Surface;

public class LivePosition implements SensorEventListener{
	public static final int SAFETYDISTANCE=20;
	private Sensor accelerometer;
	private Sensor magnetometer;
	private SensorManager mSensorManager;
	private Double azimut;
	private float[] mGravity;
	private float[] mGeomagnetic;
	private volatile boolean running;	
	private float[] rotationMatrix;
	private float inclinationMatrix[];
    private float outRotationMatrix[];
    private float orientation[];
	//private ScheduledExecutorService execService;
	//private ScheduledFuture<?> tasks;
	private CountDownLatch cdl;
	private SwapAgreementManager agreementManager;	
	//private SetPosition setPosition;
	private Timer timer;
	
	private class SetPosition extends TimerTask{	
		public SetPosition() {
		}
		@Override
		public void run() {			
			GameEnvironment.get().getPlayerInfo().setAzimute(azimut);
			System.out.println("releasing thread with azimute: "+ azimut);
			cdl.countDown();
		}	
	}
	
	private class SwapAgreementManager{
		
		private HashMap<Position.Player, Position.Player> movingEdges=new HashMap<Position.Player, Position.Player>();
		

		public void moveReqeuested(Position.Player from, Position.Player to) {
			synchronized(movingEdges){
				
				//checks if player didn't return to it's place without swapping
				if (!movedBack(from,to)){				
					ArrayList<Pair<Position.Player,Position.Player>> movingList;

					//check if place is not occupied
					if (ClientController.get().getZone(to.getRelativePosition(ClientController.get().getMe().getGlobalPosition()))==null){						
					
						//place isn't occupied
						movingList=new ArrayList<Pair<Player,Player>>();
						movingList.add(new Pair<Position.Player,Position.Player>(from,to));
						
						/*check if someone requested to move to 
						 * the area that just got unoccupied and update moving list */
						checkAndUpdatePendingRequests(from,movingList);
						
					}else{					
						//place is occupied,find cycle(starting vertex)				
						movingList = findCycle(from,to);
					}
				
					if (movingList!=null){
						//found a circle,notify all
						ConnectionsManager.getConnectionsManager().sendToAll(new Message(new LivePositionChangedAction(movingList)));
					
					}else{
						//didn't find a circle, insert new request					
						movingEdges.put(from, to);
					}
					//clearing to avoid memory leaks
					clearStage(movingList);
				
				}
			}
		}
			
		private void checkAndUpdatePendingRequests(Position.Player from,ArrayList<Pair<Player, Player>> movingList) {
			//check if someone requested to move to the area that just got unoccupied
			for (Position.Player position : movingEdges.keySet()){
				if (movingEdges.get(position).equals(from)){
					movingList.add(new Pair<Position.Player,Position.Player>(position,from));
					break;
				}
			
			}
			
		}
		private boolean movedBack(Player from, Player to) {
			if (from.equals(to)){
				//remove his request
				movingEdges.remove(from);
				return true;
			}
			return false;
		}
		private void clearStage(ArrayList<Pair<Player, Player>> movingList) {

			if (movingList!=null){			
				//clear fulfilled requests
				for (Pair<Position.Player,Position.Player> pair : movingList){
					movingEdges.remove(pair.getFirst());
				}
			
				//clear list to prevent leaking in memory
				movingList.clear();
			}
		}
		
		private ArrayList<Pair<Player, Player>> findCycle(Position.Player beginningVertex,Position.Player nextVertex){
			
			Position.Player previousVertex=beginningVertex;
			
			ArrayList<Pair<Position.Player, Position.Player>> movingList=new ArrayList<Pair<Player,Player>>();			

			//go over untill no circle or circle has been found
			while (nextVertex!=null && nextVertex!=beginningVertex){
				
				movingList.add(new Pair<Position.Player,Position.Player>(previousVertex,nextVertex));				
				//move one step forward
				previousVertex=nextVertex;
				nextVertex=movingEdges.get(nextVertex);				
			}
			
			if (nextVertex==null){
				//didn't find circle, clear list to prevent leaking in memory
				movingList.clear();
				movingList=null;
			}
			return movingList;
		}
		
		
		
	}
	//-------Singleton implementation--------//
	private static class LivePositionHolder
	{
		private final static LivePosition livePosition=new LivePosition(GameActivity.getContext());
	}
				
						
	/**
	 * get LivePosition instance
	 */
	public static LivePosition get(){
		return LivePositionHolder.livePosition;
	}
		
	private LivePosition(Context context) {
		mSensorManager=(SensorManager)(context.getSystemService(Context.SENSOR_SERVICE));		
		accelerometer=mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		//tasks=null;
		//execService=Executors.newSingleThreadScheduledExecutor();
		azimut=null;
		running=false;
		agreementManager=new SwapAgreementManager();
		cdl=new CountDownLatch(1);		
		//setPosition=new SetPosition();
		//initiate matrixes for rotation and azimuth calculations
		timer=new Timer();
		rotationMatrix=new float[9];
		inclinationMatrix=new float[9];
		outRotationMatrix=new float[9];
		orientation=new float[3];
	}	
		

	
	
	
	public void start(){
		if (!running){
			running=true;
		
			mSensorManager.registerListener(this,accelerometer, SensorManager.SENSOR_DELAY_UI);
			mSensorManager.registerListener(this,magnetometer, SensorManager.SENSOR_DELAY_UI);
			if (azimut==null){
				try {
					//System.out.println("waiting: "+System.currentTimeMillis());
					//tasks=execService.scheduleAtFixedRate(new SetPosition(), 1, 2, TimeUnit.SECONDS);
					timer.schedule(new SetPosition(),1000,4000);
					//System.out.println("locking thread.");
					cdl.await();
				} catch (InterruptedException e) {			
					e.printStackTrace();
				}
			}

		
			
		}
		
	}
	public void stop(){
		mSensorManager.unregisterListener(this);
		running=false;
		//if (tasks!=null){
		//	tasks.cancel(true);
		//}
		timer.cancel();	
	}
	public boolean isRunning(){
		return running;
	}
//	public void swapRequest(Position.Player withOrByWhom){
//		agreementManager.swapReqeusted(withOrByWhom);
//		
//	}
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {}

	@Override
	public void onSensorChanged(SensorEvent event) {
		
		    if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
		      mGravity = event.values;
		    if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
		      mGeomagnetic = event.values;
		    if (mGravity != null && mGeomagnetic != null) {
		    	
		      //float R[] = new float[9];
		     
		      boolean success = SensorManager.getRotationMatrix(rotationMatrix, inclinationMatrix, mGravity, mGeomagnetic);
		      if (success) {
		       
		    	//check if natural device position is not landscape
		        if (GameEnvironment.get().getDeviceInfo().getRotationAngle()==Surface.ROTATION_90){
		        	//rotate matrix to fit sensor reading
		        	SensorManager.remapCoordinateSystem(rotationMatrix, SensorManager.AXIS_Y, 
		        										SensorManager.AXIS_MINUS_X, outRotationMatrix);
		        	SensorManager.getOrientation(outRotationMatrix, orientation);
		        }else{
		        	//device natural position is landscape
		        	SensorManager.getOrientation(rotationMatrix, orientation);
		        }
		        //orientation contains: azimuth, pitch and roll
		        azimut = (orientation[0]*(180/Math.PI));
		        if (azimut<0){
		        	azimut+=360;
		        }
		        System.out.println("azimut: "+azimut);
		      }
		      
		    }
	}
	public static Position.Player translatePositionByAzimute(double azimute){
		Position.Player answer=null;		
		if (315<azimute || azimute<46){
			answer=Position.Player.BOTTOM;
			
		}else if (45<azimute && azimute<136){
			answer=Position.Player.LEFT;
		}else if (136<azimute && azimute<226){
			answer=Position.Player.TOP;
		}else{
			answer=Position.Player.RIGHT;
		}
		return answer;
	}

	//fromWho-from who i shall wait for the agreement 
//	public void waitForAgreement(int fromWho,Position.Player desiredPosition) {
//		agreementManager.waitForAgreement(fromWho,desiredPosition);
//		
//	}
	//clear all agreements that could have happened but didn't
//	public void clearPendingAgreements(){
//		agreementManager.clearSwappedPlayersPendingAgreement();
//	}



	public void moveRequested(Position.Player from, Position.Player to) {
		agreementManager.moveReqeuested(from,to);
		
	}
		    		
}
