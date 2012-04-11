package war.actions;

import java.util.ArrayList;

import communication.link.ServerConnection;
import communication.messages.Message;
import communication.server.ConnectionsManager;

import logic.card.CardLogic;
import logic.client.LogicDroppable;
import war.War;
import client.controller.ClientController;
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
			
		}
		ClientController.getController().getGui().addDraggable(cardLogics, ClientController.getController().getGui().getDroppableById(droppableId));
		// update gui.
	}

	@Override
	public void outgoing() {
		System.out.println("ReceiveCardAction: outgoing");
		ServerConnection.getConnection().getMessageSender().sendMessage(new Message(this));
		
	}

}
