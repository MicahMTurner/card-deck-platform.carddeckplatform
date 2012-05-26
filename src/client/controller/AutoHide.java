package client.controller;

import java.util.ArrayList;

import utils.Card;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class AutoHide implements SensorEventListener {
	private SensorManager sensorManager;
	private Sensor gravitySensor;
	private final float[] mValuesMagnet      = new float[3];
	private final float[] mValuesAccel       = new float[3];
	private final float[] mValuesOrientation = new float[3];
	private final float[] mRotationMatrix    = new float[9];	
	private ArrayList<Card>revealedCards;
	private int cardsInHand;
	
	
	private ArrayList<Card> hiddenCards;
	
	public void start(){
		sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 
                SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), 
                SensorManager.SENSOR_DELAY_FASTEST);
	}
	
	public void stop(){
		sensorManager.unregisterListener(this);
	}
	
	
	public AutoHide(Context context){
		  
		sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
//        if (sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null){
//        	gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
//        }
        this.revealedCards=new ArrayList<Card>();
        this.cardsInHand=0;
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
     double angle1=-(mValuesOrientation[1]*(180/Math.PI));
     double angle2=-(mValuesOrientation[2]*(180/Math.PI));
     //System.out.println("test: "+angle);
     
     if ((angle1<27 && angle2<27) && cardsInHand!=ClientController.get().getMe().cardsHolding()){
    	 
    	 System.out.println("hide");
    	 
    	 ArrayList<Card>myCards=ClientController.get().getMe().getCards();
    	 cardsInHand=myCards.size();
    	 //angel is almost horizontal, hide your cards
    	 for (Card card : myCards){
    		 if (card.isRevealed() && !revealedCards.contains(card)){
    			 revealedCards.add(card);    			 
    		 }
    		 card.hide();    		 
    	 }
     }else if ((angle1>=30 || angle2>=30) && !revealedCards.isEmpty()){
    	 //angel is back to normal, reveal your cards again
    	 
    	 System.out.println("reveale");
    	 for (Card card : revealedCards){
    		 card.reveal();    		
    	 }
    	 revealedCards.clear();
    	 cardsInHand=0;
     }
     else
    	 System.out.println("idle");

//		float x = event.values[0];
//		
//		if(x<5 && !protect){
//			protect = true;
//			ArrayList<Card> cards = ClientController.get().getMe().getCards();
//			hiddenCards = new ArrayList<Card>();
//			for(Card c : cards){
//				if(c.isRevealed()){
//					hiddenCards.add(c);
//					c.hide();
//				}
//			}
//		}
//		
//		if(x>=5 && protect){
//			protect = false;
//			for(Card c : hiddenCards){
//				c.reveal();
//			}
//			
//		}
	
		
		
		
		
	}

}
