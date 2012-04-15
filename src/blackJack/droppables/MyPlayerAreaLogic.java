package blackJack.droppables;

import carddeckplatform.game.GameStatus;
import client.controller.ClientController;
import client.controller.actions.EndTurnAction;
import logic.card.CardLogic;
import logic.client.LogicDroppable;

public class MyPlayerAreaLogic extends PlayerAreaLogic{

	public MyPlayerAreaLogic(int id, Type type) {
		super(id, type);		
	}
	@Override
	public void addCard(CardLogic card) {
		card.setRevealed(true);
		card.setMoveable(true);
		cards.push(card);
		int sum=sumCardsInHand(this);
		if (sum>21){
			//lose
			ClientController.getController().declareLoser();
			ClientController.getController().disableUi();
			ClientController.outgoingAPI().outgoingCommand(new EndTurnAction(GameStatus.me.getPosition()));
		}
		
	}
	private int sumCardsInHand(LogicDroppable player){
		int sum=0;
		for (CardLogic card : player.getCards()){
			sum+=card.getValue();
		}
		return sum;
	}
	

}
