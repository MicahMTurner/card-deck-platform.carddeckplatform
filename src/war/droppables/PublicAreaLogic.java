package war.droppables;

import communication.messages.Message;

import war.War;
import war.actions.RoundWinnerAction;
import war.actions.TurnAction;
import carddeckplatform.game.GameStatus;
import client.controller.ClientController;
import client.controller.actions.EndTurnAction;
import client.gui.entities.Table;
import logic.card.CardLogic;

import logic.client.Game;
import logic.client.LogicDroppable;

public class PublicAreaLogic extends LogicDroppable{
	private int cardsPlacedWhileTie;
	private boolean guiLocked;
	public PublicAreaLogic(int id,Type type) {
		super(id,type);		
		this.cardsPlacedWhileTie=0;
	}

	@Override
	public void onClickHandler() {
		
		
	}

	@Override
	public void onDropHandler(CardLogic card) {		
		//addCard(card);
		//end turn
		//ClientController.outgoingAPI().outgoingCommand(new EndTurnAction(GameStatus.me.getPosition()));
		
	}

	@Override
	public void addCard(CardLogic card) {
		cards.push(card);
		card.setMoveable(false);
		if (War.isTie() && this.cardsPlacedWhileTie<2){
			card.setRevealed(false);
			this.cardsPlacedWhileTie++;
		}
		else{
			if (this.cardsPlacedWhileTie==2 && guiLocked){
				ClientController.getController().getGui().setUiEnabled(false);
			}
			War.setTie(false);
			cardsPlacedWhileTie=0;
			card.setRevealed(true);
			calculateWinner(card);
		
			}
	}

	private void calculateWinner(CardLogic card) {
		for (LogicDroppable logicDroppable : War.getDroppables() ){
			if (logicDroppable.getType().equals(LogicDroppable.Type.PUBLIC) 
					&& !logicDroppable.equals(this)){
				
			
				if( logicDroppable.getCards().size()!=getCards().size()){
					if (ClientController.getController().isMyTurn()){
						//end turn
						ClientController.outgoingAPI().outgoingCommand(new EndTurnAction(GameStatus.me.getPosition()));
					}
				}else{
					CardLogic winnerCard = getWinnerCard(card,logicDroppable.getCards().peek());
					if (winnerCard!=null){
					for (LogicDroppable droppable : War.getDroppables()){
						if (droppable.getType().equals(LogicDroppable.Type.PUBLIC)){
							for (CardLogic cardlogic : droppable.getCards()){
								cardlogic.setOwner(winnerCard.getOwner());
								if (winnerCard.getOwner().equals(GameStatus.username)){
									//won									
									ClientController.getController().runCardAnimation(cardlogic, War.getDroppables().get(3), 1000, 10, true, false, Table.GetMethod.PutInBack);
									
									War.getDroppables().get(3).addCard(cardlogic);	// get the droppable that represents my area.
								}
								else {
									//lost
									ClientController.getController().runCardAnimation(cardlogic, War.getDroppables().get(2), 1000, 10, true, false, Table.GetMethod.PutInBack);
									
									War.getDroppables().get(2).addCard(cardlogic);	// get the droppable that represents oponent's area.
									
								}
								
							
							}
							if (!winnerCard.getOwner().equals(GameStatus.username) 
									&& ClientController.getController().isMyTurn()){								
									//end turn
									ClientController.outgoingAPI().outgoingCommand(new EndTurnAction(GameStatus.me.getPosition()));
								}
							}
							droppable.getCards().clear();
					}
					}
				}
			}
		}
		

		
	}

	private CardLogic getWinnerCard(CardLogic card,CardLogic otherPublicCard) {
		CardLogic answer=null;
		if(otherPublicCard.getValue()>card.getValue()){						
			answer=otherPublicCard;
		}
		
		else if(otherPublicCard.getValue()<(card.getValue())){
			answer=card;
		}	
		else{
			War.setTie(true);
			if (!ClientController.getController().isMyTurn()){
				ClientController.getController().getGui().setUiEnabled(true);
				guiLocked=true;
			}
			
		}
		return answer;
	}

}
