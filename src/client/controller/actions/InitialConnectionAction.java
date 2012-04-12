package client.controller.actions;

import java.util.ArrayList;

import communication.link.ServerConnection;
import communication.messages.InitialMessage;

import client.controller.ClientController;
import client.dataBase.ClientDataBase;
import logic.client.Game;
import logic.client.Player;

public class InitialConnectionAction extends ClientAction{
	
	private String gameId;
	private Player.Position position;
	private ArrayList<Player> newPlayers;
	
	public InitialConnectionAction(String gameId, Player.Position position, ArrayList<Player> newPlayers) {		
		this.gameId=gameId;
		this.position=position;
		this.newPlayers=newPlayers;
	}
	
	@Override
	public void incoming() {
		Game game=ClientDataBase.getDataBase().getGame(gameId);
		game.addMe();
		game.getMe().setPosition(position);
		ClientController.getController().setLogic(game);
		for (Player newPlayer : newPlayers){
			ClientController.getController().addPlayer(newPlayer);
		}
		
		
	}

	@Override
	public void outgoing() {
		ServerConnection.getConnection().getMessageSender().send(new InitialMessage(ClientController.getController().getLogic().getMe()));
		
	}

}
