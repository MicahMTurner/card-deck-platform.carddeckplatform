package communication.actions;

import java.util.ArrayList;

import utils.GamePrefs;
import utils.Player;
import utils.Position;


import carddeckplatform.game.gameEnvironment.GameEnvironment;
import client.controller.ClientController;
import client.controller.LivePosition;
import client.dataBase.ClientDataBase;
import freeplay.FreePlayPrefs;
import freeplay.customization.FreePlayProfile;
import logic.client.Game;


public class InitialConnectionAction implements Action{
	

	private Position.Player position;
	private ArrayList<Player> newPlayers;
	FreePlayProfile freePlayProfile;
	
	
	
	public InitialConnectionAction(Position.Player position, ArrayList<Player> newPlayers,FreePlayProfile freePlayProfile) {
		this.position=position;
		this.newPlayers=newPlayers;
		this.freePlayProfile = freePlayProfile;
	}
	


	@Override
	public void execute() {


		
		//create my instance

		if (GameEnvironment.get().getPlayerInfo().isServer()){
			//i'm the host
			if (LivePosition.get().isRunning()){
				//live position running, get position from live position system
				if (freePlayProfile==null){
				ClientController.get().addMe(GameEnvironment.get().getPlayerInfo(), 
						LivePosition.translatePositionByAzimute(GameEnvironment.get().getPlayerInfo().getAzimute()),position.getId());
				}else{
					//free play game
					ClientController.get().addMe(GameEnvironment.get().getPlayerInfo(),position,position.getId());
				}
			}else{
				//live position off, use position from server
				ClientController.get().addMe(GameEnvironment.get().getPlayerInfo(),position,position.getId());
			}
		}else{
			//check if host live position is on
			if (newPlayers.get(0).getAzimute()!=null){
				//true, activate my live position system and get position from it				
				LivePosition.get().start();
				if (freePlayProfile==null){
					ClientController.get().addMe(GameEnvironment.get().getPlayerInfo(), 
							LivePosition.translatePositionByAzimute(GameEnvironment.get().getPlayerInfo().getAzimute()),position.getId());
				}else{
					//free play game
					ClientController.get().addMe(GameEnvironment.get().getPlayerInfo(),position,position.getId());
					
				}
				
			}else{
				//false, user host given position
				ClientController.get().addMe(GameEnvironment.get().getPlayerInfo(),position,position.getId());
			}
		}
		
		//ClientController.get().addMe(game.getMe());
		
				
		
		
		//add all players
		for (Player newPlayer : newPlayers){			
			ClientController.get().addPlayer(newPlayer);
		}		
		
	}

	
}
