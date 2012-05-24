package client.controller;

import java.util.ArrayList;

import utils.Card;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Gravity implements SensorEventListener {
	private SensorManager sensorManager;
	private Sensor gravitySensor;
	
	private boolean protect = false;
	
	
	private ArrayList<Card> hiddenCards;
	
	public void start(){
		sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	public void stop(){
		sensorManager.unregisterListener(this);
	}
	
	
	public Gravity(Context context){
		sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null){
        	gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        }
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];
		
		if(x<5 && !protect){
			protect = true;
			ArrayList<Card> cards = ClientController.get().getMe().getCards();
			hiddenCards = new ArrayList<Card>();
			for(Card c : cards){
				if(c.isRevealed()){
					hiddenCards.add(c);
					c.hide();
				}
			}
		}
		
		if(x>=5 && protect){
			protect = false;
			for(Card c : hiddenCards){
				c.reveal();
			}
			
		}
	
		
		
		
		
	}

}
