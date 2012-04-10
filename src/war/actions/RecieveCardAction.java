package war.actions;

import java.util.ArrayList;

import communication.server.ConnectionsManager;

import logic.card.CardLogic;
import logic.client.LogicDroppable;
import war.War;
import client.controller.actions.ClientAction;

public class RecieveCardAction extends ClientAction {

	private ArrayList<CardLogic> cardLogics = new ArrayList<CardLogic>();
	private int droppableId;
	
	public RecieveCardAction(ArrayList<CardLogic> cardLogics, int droppableId){
		this.cardLogics = cardLogics;
		this.droppableId = droppableId;
	}
	
	@Override
	public void incoming() {
		// TODO Auto-generated method stub
		for(CardLogic cardLogic : cardLogics){
			War.getMe().getHand().add(cardLogic);
			gui.addDraggable(cardLogic, gui.getDroppableById(droppableId));
		}
		// update gui.
	}

	@Override
	public void outgoing() {
		
		
	}

}
