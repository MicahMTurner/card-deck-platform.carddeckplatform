package war.droppables;

import war.War;
import war.actions.RoundWinnerAction;
import client.controller.Controller;
import logic.card.CardLogic;

import logic.client.LogicDroppable;

public class PublicAreaLogic extends LogicDroppable{

	public PublicAreaLogic(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onClickHandler() {
		
		
	}

	@Override
	public void onDropHandler(CardLogic card) {
		//Controller.outgoingAPI().putInPublic(player, card, isRevealed)
		for (LogicDroppable logicDroppable : War.getDroppables() ){
			if (logicDroppable.equals(this)){
				continue;
			}
			if(!logicDroppable.getCards().empty()){
				if(logicDroppable.getCards().peek().getValue()>card.getValue()){
					//lose
					//Controller.outgoingAPI().outgoingCommand(new RoundWinnerAction());
				}
				
				else if(logicDroppable.getCards().peek().getValue()<(card.getValue())){
					//win
					
				}
				//tie
				else{
					
				}
			}
				
			
		}
		
	}

	@Override
	public void addCard(CardLogic card) {
		// TODO Auto-generated method stub
		cards.push(card);
	}

}
