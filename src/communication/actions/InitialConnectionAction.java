package communication.actions;

import java.util.ArrayList;

import utils.Player;
import utils.Position;

import carddeckplatform.game.GameStatus;
import client.controller.ClientController;
import client.controller.PositionByCompass;
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
		ClientController.get().setGame(game);
		
		//create my instance		
		if (newPlayers.get(0).getAzimute()==null){
			game.addMe(GameStatus.username, position);
		}else{
			//start live position
			
			//add me
			game.addMe(GameStatus.username, PositionByCompass.translatePositionByAzimute(GameStatus.playerInfo.getAzimute()));
		}
		
		ClientController.get().addPlayer(game.getMe());
		
				
		
		
		
		for (Player newPlayer : newPlayers){
		
			newPlayer.setRelativePosition(game.getMe().getGlobalPosition());
			ClientController.get().addPlayer(newPlayer);
		}		
		
	}

}
