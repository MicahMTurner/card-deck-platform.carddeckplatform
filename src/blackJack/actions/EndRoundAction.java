package blackJack.actions;

import blackJack.BlackJack;
import logic.card.CardLogic;
import logic.client.LogicDroppable;
import communication.link.ServerConnection;
import communication.messages.Message;

import client.controller.ClientController;
import client.controller.actions.ClientAction;

public class EndRoundAction extends ClientAction{

	@Override
	public void incoming() {
		int mySum=0;
		//calc my sum
		for (CardLogic card : BlackJack.getDroppables().get(2).getCards()){
			mySum+=card.getValue();
		}
		for (LogicDroppable droppable : BlackJack.getDroppables()){
			if (droppable.getType().equals(LogicDroppable.Type.PLAYER)){
				int othersSum=0;
				for (CardLogic card : droppable.getCards()){
					othersSum+=card.getValue();
				}
				if (mySum<othersSum){
					//lose
					ClientController.getController().disableUi();
					ClientController.getController().declareLoser();
					return;
				}
			}
		}
		//win
		ClientController.getController().disableUi();
		ClientController.getController().declareWinner();
		
	}

	@Override
	public void outgoing() {
		ServerConnection.getConnection().getMessageSender().send(new Message(this));
		
	}

}
