package client.controller.actions;

import java.util.ArrayList;

import client.controller.ClientController;

import communication.link.ServerConnection;
import communication.messages.InitialMessage;

import logic.client.Game;
import logic.client.Player;

public class AddPlayerAction extends ClientAction{
	
	private Player newPlayer;
	
	public AddPlayerAction(Player newPlayer) {
		this.newPlayer=newPlayer;
	}
	@Override
	public void incoming() {		
		ClientController.getController().addPlayer(newPlayer);
	}

	@Override
	public void outgoing() {
		//action is only incoming action.
	}

}
