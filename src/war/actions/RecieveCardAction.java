package war.actions;

import logic.card.CardLogic;
import war.War;
import client.controller.actions.Action;

public class RecieveCardAction extends Action {

	CardLogic cardLogic;
	
	@Override
	public void incoming() {
		// TODO Auto-generated method stub
		War.getMe().getHand().add(cardLogic);
		// update gui.
	}

	@Override
	public void outgoing() {
		// TODO Auto-generated method stub
		
	}

}
