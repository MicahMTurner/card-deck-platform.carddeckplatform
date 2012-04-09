package war.actions;

import communication.link.ServerConnection;

import war.War;
import war.messages.RoundWinnerMessage;
import client.controller.ClientController;
import client.controller.actions.Action;
import logic.card.CardLogic;
import logic.client.GameLogic;
import logic.client.LogicDroppable;
import logic.client.Player;
import carddeckplatform.game.GameStatus;
import carddeckplatform.game.TableView;

public class RoundWinnerAction extends Action{
	String winner;
	public RoundWinnerAction(String winner) {
		this.winner=winner;
	}

	
	@Override
	public void incoming() {
		if (winner.equals(GameStatus.username)){
			//i'm the winner
			for (LogicDroppable droppable : War.getDroppables()){
				for (CardLogic card : droppable.getCards()){
					War.getMe().getHand().add(card);
				}
			}
		}
		//i'm not the winner
		else{
			for (LogicDroppable droppable : War.getDroppables()){
				droppable.getCards().clear();
			}
		}
	}

	/**
	 * 
	 */
	@Override
	public void outgoing() {
		ServerConnection.getConnection().getMessageSender().sendMessage(new RoundWinnerMessage(winner));
		
	}

}
