package communication.actions;

import java.util.ArrayList;

import utils.Player;
import utils.Position;


import carddeckplatform.game.gameEnvironment.GameEnvironment;
import client.controller.ClientController;
import client.controller.LivePosition;
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
		if (newPlayers.isEmpty() && LivePosition.get().isRunning()){			
				game.addMe(GameEnvironment.get().getPlayerInfo(), 
						LivePosition.translatePositionByAzimute(GameEnvironment.get().getPlayerInfo().getAzimute()),position.getId());
			
		}
		else if (newPlayers.get(0).getAzimute()==null){
			game.addMe(GameEnvironment.get().getPlayerInfo(),position,position.getId());
		}else{
			LivePosition.get().start();
			game.addMe(GameEnvironment.get().getPlayerInfo(), 
					LivePosition.translatePositionByAzimute(GameEnvironment.get().getPlayerInfo().getAzimute()),position.getId());
		}
		
		ClientController.get().addPlayer(game.getMe());
		
				
		
		
		
		for (Player newPlayer : newPlayers){			
			ClientController.get().addPlayer(newPlayer);
		}		
		
	}

}
