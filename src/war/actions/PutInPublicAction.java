package war.actions;

import war.War;
import logic.client.LogicDroppable;
import client.controller.actions.Action;

public class PutInPublicAction extends Action {

	private LogicDroppable droppable;
	
	@Override
	public void incoming() {
		for (LogicDroppable d : War.getDroppables()){
			if (d.getId()==droppable.getId()){
				d.addCard(droppable.getCards().peek());
			}
		}
		// update gui.
	}

	@Override
	public void outgoing() {
		// TODO Auto-generated method stub
		
	}

}
