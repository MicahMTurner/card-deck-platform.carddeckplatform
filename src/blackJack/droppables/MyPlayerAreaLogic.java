package blackJack.droppables;

import blackJack.actions.EndRoundAction;
import carddeckplatform.game.GameStatus;
import client.controller.ClientController;
import client.controller.actions.EndTurnAction;
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
				ClientController.getController().popMessage("Exceeded 21!");
			}
			ClientController.getController().disableUi();
			if (GameStatus.me.getPosition().equals(Player.Position.TOP)){
				ClientController.outgoingAPI().outgoingCommand(new EndRoundAction());
				ClientController.incomingAPI().incomingCommand(new EndRoundAction());
			}
			ClientController.outgoingAPI().outgoingCommand(new EndTurnAction(GameStatus.me.getPosition()));
		}
		
	}
	
	private int sumCardsInHand(LogicDroppable player){
		int sum=0;
		for (CardLogic card : player.getCards()){
			int value=card.getValue();
			if (value>10 ){
				sum+=10;
			}
			else if (value==14){
				sum+=11;
			}else{
				sum+=value;
			}
		}
		return sum;
	}
	

}
