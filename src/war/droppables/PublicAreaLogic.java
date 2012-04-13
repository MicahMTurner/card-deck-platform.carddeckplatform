package war.droppables;

import communication.messages.Message;

import war.War;
import war.actions.RoundWinnerAction;
import war.actions.TurnAction;
import carddeckplatform.game.GameStatus;
import client.controller.ClientController;
import client.controller.actions.EndTurnAction;
import logic.card.CardLogic;

import logic.client.LogicDroppable;

public class PublicAreaLogic extends LogicDroppable{

	public PublicAreaLogic(int id,Type type) {
		super(id,type);		
	}

	@Override
	public void onClickHandler() {
		
		
	}

	@Override
	public void onDropHandler(CardLogic card) {		
		for (LogicDroppable logicDroppable : War.getDroppables() ){
			if (logicDroppable.getType().equals(LogicDroppable.Type.PUBLIC)){
				if (logicDroppable.equals(this)){
					continue;
				}
				
				if(!logicDroppable.getCards().empty()){
					CardLogic otherPublicCard=logicDroppable.getCards().peek();
					CardLogic winnerCard = null;
					if(otherPublicCard.getValue()>card.getValue()){						
						winnerCard=otherPublicCard;
					}
					
					else if(logicDroppable.getCards().peek().getValue()<(card.getValue())){
						winnerCard=card;
					}
				
					else{
						//TODO add tie mode (game state?)
						
					}
					if (winnerCard.getOwner().equals(GameStatus.username)){
						//won
						for (LogicDroppable droppable : War.getDroppables()){
							if (droppable.getType().equals(LogicDroppable.Type.PUBLIC)){
								for (CardLogic cardlogic : droppable.getCards()){
									cardlogic.setOwner(GameStatus.username);
									ClientController.getController().addCard(cardlogic);
									droppable.getCards().clear();
								}
							}
						}						
					}
					else {
						//lost					
						for (LogicDroppable droppable : War.getDroppables()){
					
							if (droppable.getType().equals(LogicDroppable.Type.PUBLIC)){
								droppable.getCards().clear();
							}
						}
					}
				}
			}				
			
		}
		//end turn
		ClientController.outgoingAPI().outgoingCommand(new EndTurnAction(GameStatus.me.getPosition()));
		
	}

	@Override
	public void addCard(CardLogic card) {		
		card.setRevealed(true);
		card.setMoveable(false);
		cards.push(card);
	}

}
