package communication.actions;

import java.util.ArrayList;

import utils.Pair;
import utils.Position;
import utils.Position.Player;
import client.controller.ClientController;

public class LivePositionChangedAction implements Action {
	private int playerId;
	private Position.Player newPosition;
	private ArrayList<Pair<Player, Player>> movingList;
	
	public LivePositionChangedAction() {}
	
	public LivePositionChangedAction(int playerId,Position.Player newPosition) {
		this.playerId=playerId;
		this.newPosition=newPosition;
	}
	
	public LivePositionChangedAction(ArrayList<Pair<Player, Player>> movingList) {
		this.movingList=movingList;
	}

	@Override
	public void execute() {
		
		//ClientController.get().positionUpdate(playerId, newPosition);
		ClientController.get().positionUpdate2(movingList);
	}

}
