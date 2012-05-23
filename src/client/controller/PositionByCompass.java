package client.controller;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import utils.Position;
import communication.actions.LivePositionChangedAction;
import communication.link.ServerConnection;
import communication.messages.Message;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class PositionByCompass implements SensorEventListener{
	private Sensor accelerometer;
	private Sensor magnetometer;
	private SensorManager mSensorManager;
	private float azimut;
	private float[] mGravity;
	private float[] mGeomagnetic;
	private LivePosition sendLivePosition;
	private ScheduledExecutorService execService;
	private ScheduledFuture<?> tasks;
	
	private class LivePosition implements Runnable{	
		float previousRead;
		public LivePosition(float initialRead) {
			this.previousRead=initialRead;
		}
		@Override
		public void run() {
			Position.Player newPosition=getNewPosition();
			if (Math.abs((previousRead-azimut))>65){
				//notify others about the change
				ServerConnection.getConnection().send(new Message(
						new LivePositionChangedAction(ClientController.get().getMe().getId(),newPosition)));
				//update my position
				ClientController.get().positionUpdate(ClientController.get().getMe().getId(), newPosition);
				
			}
			previousRead=azimut;
			
		}
		private Position.Player getNewPosition() {
			if (azimut>60 && azimut<100){
				
			}else if(azimut>60 && azimut<100){
				
			}else if(azimut>60 && azimut<100){
				
			}else if(azimut>60 && azimut<100){
				
			}
			return null;
		}		
	}
	
	public PositionByCompass(Context context) {
		mSensorManager=(SensorManager)(context.getSystemService(Context.SENSOR_SERVICE));		
		accelerometer=mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		
		execService=Executors.newSingleThreadScheduledExecutor();
	}
	
	public void start(){
		mSensorManager.registerListener(this,accelerometer, SensorManager.SENSOR_DELAY_UI);
		mSensorManager.registerListener(this,magnetometer, SensorManager.SENSOR_DELAY_UI);
		if (sendLivePosition==null){
			sendLivePosition=new LivePosition(azimut);
		}
		tasks=execService.scheduleAtFixedRate(sendLivePosition, 0, 4, TimeUnit.SECONDS);
	}
	public void stop(){
		mSensorManager.unregisterListener(this);
		tasks.cancel(true);
	}

		
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {}

	@Override
	public void onSensorChanged(SensorEvent event) {
		    if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
		      mGravity = event.values;
		    if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
		      mGeomagnetic = event.values;
		    if (mGravity != null && mGeomagnetic != null) {
		      float R[] = new float[9];
		      float I[] = new float[9];
		      boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
		      if (success) {
		        float orientation[] = new float[3];
		        //orientation contains: azimut, pitch and roll
		        SensorManager.getOrientation(R, orientation);
		        azimut = orientation[0]; 
		        System.out.println(azimut*(180/Math.PI));
		      }
		    }
	}
		    		
	}
