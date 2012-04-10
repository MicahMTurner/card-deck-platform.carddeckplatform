package client.controller.actions;

import java.util.ArrayList;

import communication.link.ServerConnection;
import communication.messages.InitialMessage;

import logic.client.Game;
import logic.client.Player;

public class AddPlayerAction extends ClientAction{
	private Player myself;
	private ArrayList<Player> newPlayers;
	public AddPlayerAction(Player myself,ArrayList<Player> newPlayers) {
		this.newPlayers=newPlayers;
		this.myself=myself;
	}
	@Override
	public void incoming() {
		for (Player newPlayer : newPlayers){
			Game.players.add(newPlayer);
		}
		
	}

	@Override
	public void outgoing() {
		ServerConnection.getConnection().getMessageSender().sendMessage(new InitialMessage(this,myself));
	}

}
