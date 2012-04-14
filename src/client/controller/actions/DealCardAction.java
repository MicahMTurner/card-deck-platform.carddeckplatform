package client.controller.actions;

import java.util.ArrayList;

import logic.card.CardLogic;
import client.controller.ClientController;


public class DealCardAction extends ClientAction{
	private ArrayList<CardLogic> cardLogics = new ArrayList<CardLogic>();
	private int droppableId;
	
	public DealCardAction(ArrayList<CardLogic> logics, int droppableId){
		//this.cardLogics = cardLogics;
		this.droppableId = droppableId;
		for(CardLogic cl : logics){
			cardLogics.add(cl);
		}
	}
	
	@Override
	public void incoming() {
		System.out.println("Deal cards action received, cards received: " + cardLogics.size() + " target: " + droppableId);
		for(CardLogic cardLogic : cardLogics){
			ClientController.getController().addCard(cardLogic);
		}
		//update gui
		ClientController.getController().getGui().addDraggable(cardLogics, ClientController.getController().getGui().getDroppableById(droppableId));
	}

	@Override
	public void outgoing() {
		// only incoming command can occur
		
	}
}
