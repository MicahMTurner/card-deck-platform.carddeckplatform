package communication.actions;

import logic.client.Game;
import client.controller.ClientController;
import client.dataBase.ClientDataBase;
import freeplay.customization.FreePlayProfile;

public class LoadGameAction implements Action{
	private String gameId;	
	FreePlayProfile freePlayProfile;
	
	public LoadGameAction(String gameId, FreePlayProfile freePlayProfile) {
		this.gameId=gameId;
		this.freePlayProfile = freePlayProfile;
	}
	@Override
	public void execute() {
			Game game=ClientDataBase.getDataBase().getGame(gameId);
			ClientController.get().setGame(game);
			if(freePlayProfile!=null){
				game.setFreePlayProfile(freePlayProfile);
			}
			game.setLayouts();
	}
}
