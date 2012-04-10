package war.actions;

import communication.server.ConnectionsManager;

import logic.card.CardLogic;
import war.War;
import client.controller.actions.ClientAction;

public class RecieveCardAction extends ClientAction {

	CardLogic cardLogic;
	
	@Override
	public void incoming() {
		// TODO Auto-generated method stub
		War.getMe().getHand().add(cardLogic);
		// update gui.
	}

	@Override
	public void outgoing() {
		
		
	}

}
