package blackJack.droppables;

import blackJack.actions.EndRoundAction;
import carddeckplatform.game.GameStatus;
import client.controller.ClientController;
import logic.card.CardLogic;
import logic.client.LogicDroppable;
import logic.client.Player;

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
		if (sum>21 || cards.size()>4){
			if (sum>21){
				ClientController.getController().declareLoser();
			}
			ClientController.getController().disableUi();
			if (GameStatus.me.getPosition().equals(Player.Position.TOP)){
				ClientController.outgoingAPI().outgoingCommand(new EndRoundAction());
			}
			ClientController.outgoingAPI().outgoingCommand(new EndRoundAction());
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
