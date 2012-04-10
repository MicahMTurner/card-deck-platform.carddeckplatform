package client.controller.actions;

import logic.client.Game;
import logic.client.Player;

public class SetPositionAction extends ClientAction{
	private Player.Position position;
	
	public SetPositionAction(Player.Position position) {
		this.position=position;
	}
	
	@Override
	public void incoming() {
		Game.getMe().setPosition(position);
		
	}

	@Override
	public void outgoing() {
		//this action happens only on incoming, never outgoing.
		//only server sends this message, never the client.
		
	}

}
