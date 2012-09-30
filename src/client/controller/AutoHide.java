package client.controller;

import java.util.ArrayList;

import carddeckplatform.game.GameActivity;
import carddeckplatform.game.gameEnvironment.GameEnvironment;

import utils.Card;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.Surface;

public class AutoHide implements SensorEventListener {
	private SensorManager sensorManager;
	private final float[] mValuesMagnet      = new float[3];
	private final float[] mValuesAccel       = new float[3];
	private final float[] mValuesOrientation = new float[3];
	private final float[] mRotationMatrix    = new float[9];	
	private ArrayList<Card>revealedCards;
	private boolean running;
	private int cardsInHand;
	
	//-------Singleton implementation--------//
	private static class AutoHideHolder
	{
		private final static AutoHide autoHide=new AutoHide();
	}
			
					
	/**
	 * get AutoHide instance
	 */
	public static AutoHide get(){
		return AutoHideHolder.autoHide;
	}
	
	
	
	
	private AutoHide(){
		sensorManager=null;
		running=false;
	}
	
	private void initiate(Context context){
		  
		sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
//      if (sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null){
//      	gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
//      }
      this.revealedCards=new ArrayList<Card>();
      this.cardsInHand=0;
	}
	
	public void start(Context context){
		if (!running){
			running=true;
					initiate(context);
					sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 
			                SensorManager.SENSOR_DELAY_NORMAL);
			        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), 
			        		SensorManager.SENSOR_DELAY_NORMAL);
		}
	}
	
	public void stop(){
		if (running){
			running=false;
			if (sensorManager!=null){
				sensorManager.unregisterListener(this);
			}
		}
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		 switch (event.sensor.getType()) {
         case Sensor.TYPE_ACCELEROMETER:
             System.arraycopy(event.values, 0, mValuesAccel, 0, 3);
             break;

         case Sensor.TYPE_MAGNETIC_FIELD:
             System.arraycopy(event.values, 0, mValuesMagnet, 0, 3);
             break;
     }
     //get rotation matrix using both sensors for greater accuracy
     SensorManager.getRotationMatrix(mRotationMatrix, null, mValuesAccel, mValuesMagnet);
     //get orientation from rotation matrix
     SensorManager.getOrientation(mRotationMatrix, mValuesOrientation);
     //calculate angle plus convert from radians to degrees
     double angle;
     if (GameEnvironment.get().getDeviceInfo().getRotationAngle()==Surface.ROTATION_90){
    	 angle=-(mValuesOrientation[2]*(180/Math.PI));    	
     }else{
    	 angle=-(mValuesOrientation[1]*(180/Math.PI));
     }
     
    

    	 if ((angle<17) && cardsInHand!=ClientController.get().getMe().cardsHolding()){
        	 
    	    	// System.out.println("hide");
    	    	 
    	    	 ArrayList<Card>myCards=(ArrayList<Card>) ClientController.get().getMe().getCards();
    	    	 cardsInHand=myCards.size();
    	    	 //angel is almost horizontal, hide your cards
    	    	 for (Card card : myCards){
    	    		 if (card.isRevealed() && !revealedCards.contains(card)){
    	    			 revealedCards.add(card);    			 
    	    		 }
    	    		 card.hide();    		 
    	    	 }
    	     }else if ((angle>=20) && !revealedCards.isEmpty()){
    	    	 //angel is back to normal, reveal your cards again
    	    	 
    	    	 //System.out.println("reveale");
    	    	 for (Card card : revealedCards){
    	    		 card.reveal();    		
    	    	 }
    	    	 revealedCards.clear();
    	    	 cardsInHand=0;
    	     }	
	}

}
