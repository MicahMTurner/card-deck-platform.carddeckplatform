package client.controller.actions;

import java.util.ArrayList;

import utils.Player;
import utils.Position;

import communication.actions.Action;
import carddeckplatform.game.GameStatus;
import client.controller.ClientController;
import client.dataBase.ClientDataBase;
import client.gui.entities.GuiPlayer;
import logic.client.Game;


public class InitialConnectionAction implements Action{
	
	private String gameId;
	private Position.Player position;
	private ArrayList<Player> newPlayersInfo;
	
	public InitialConnectionAction(String gameId, Position.Player position, ArrayList<Player> newPlayersInfo) {		
		this.gameId=gameId;
		this.position=position;
		this.newPlayersInfo=newPlayersInfo;
	}

	@Override
	public void execute() {
		Game game=ClientDataBase.getDataBase().getGame(gameId);
		ClientController.getController().setGame(game);
		
		//create my instance		
		
		game.addMe(GameStatus.username, position);
		
		ClientController.getController().addPlayer(game.getMe());
		
				
		
		
		
		for (Player newPlayer : newPlayersInfo){			
			ClientController.getController().addPlayer(newPlayer);
		}		
		
	}

}
