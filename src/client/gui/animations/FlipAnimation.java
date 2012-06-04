package client.gui.animations;

import java.util.ArrayList;

import client.controller.ClientController;
import client.gui.entities.Droppable;

import utils.Card;
import utils.Point;
import carddeckplatform.game.StaticFunctions;
import android.os.AsyncTask;

public class FlipAnimation extends AsyncTask<Void, Void, Void> {

	public FlipAnimation(Droppable source, Droppable destination,Card card,boolean sendToCommunication) {
		super();
		this.source = source;
		this.destination = destination;
		this.card=card;
		this.sendToCommunication=sendToCommunication;
	}

	private Card card;
	final Droppable source;
	final Droppable destination;
	boolean sendToCommunication;
	
	@Override
	protected Void doInBackground(Void... params) {
		float x = card.getX();
		float y = card.getY();
		final ArrayList<Point> vector = StaticFunctions.midLine((int)x, (int)y, (int)destination.getX(), (int)destination.getY());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {			        			
			e.printStackTrace();
		}
    	for(int i=0; i<vector.size(); i++){
    		final int index = i;

    		try {
    			Thread.sleep(5);
    		} catch (InterruptedException e) {			            			
    			e.printStackTrace();
    		}					
    		
    		card.setLocation(vector.get(index).getX(), vector.get(index).getY());
    		card.setAngle(i*10);            		
    	}
    	card.setLocation(destination.getX(), destination.getY());
    	card.setAngle(0);
		
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(sendToCommunication){
			destination.onDrop(ClientController.get().getMe(), source,
					((Card) card));
			
		}else{
			source.removeCard(null,card);
			destination.addCard(null,card);
		}
		
		
		
		
	}

}
