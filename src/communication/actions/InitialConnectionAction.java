package communication.actions;

import java.util.ArrayList;

import utils.Player;
import utils.Position;

import carddeckplatform.game.GameEnvironment;
import client.controller.ClientController;
import client.dataBase.ClientDataBase;
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
		ClientController.get().setGame(game);
		
		//create my instance		
		
		game.addMe(GameEnvironment.getGameEnvironment().getPlayerInfo().getUsername(), position);
		
		ClientController.get().addPlayer(game.getMe());
		
				
		
		
		
		for (Player newPlayer : newPlayersInfo){			
			ClientController.get().addPlayer(newPlayer);
		}		
		
	}

}
