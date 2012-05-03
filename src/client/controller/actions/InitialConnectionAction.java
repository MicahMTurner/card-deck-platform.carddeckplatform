package client.controller.actions;

import java.util.ArrayList;

import utils.Player;
import utils.Position;

import communication.actions.Action;
import carddeckplatform.game.GameStatus;
import client.controller.ClientController;
import client.dataBase.ClientDataBase;
import logic.client.Game;


public class InitialConnectionAction implements Action{
	
	private String gameId;
	private Position.Player position;
	private ArrayList<Player> newPlayers;
	
	public InitialConnectionAction(String gameId, Position.Player position, ArrayList<Player> newPlayers) {		
		this.gameId=gameId;
		this.position=position;
		this.newPlayers=newPlayers;
	}

	@Override
	public void execute() {
		Game game=ClientDataBase.getDataBase().getGame(gameId);
		//create my instance		
		game.addMe(GameStatus.username, position);

		
				
		
		ClientController.getController().setGame(game);
		for (Player newPlayer : newPlayers){
			ClientController.getController().addPlayer(newPlayer);
		}		
		
	}

}
