package blackJack.droppables;

import blackJack.actions.EndRoundAction;
import carddeckplatform.game.GameStatus;
import client.controller.ClientController;
import client.controller.actions.EndTurnAction;
import logic.card.CardLogic;
import logic.client.LogicDroppable;
import logic.client.Player;

public class PressButtonLogic extends LogicDroppable{

	public PressButtonLogic(int id, Type type) {
		super(id, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onClickHandler() {
		if (GameStatus.me.getPosition().equals(Player.Position.TOP)){
			ClientController.outgoingAPI().outgoingCommand(new EndRoundAction());
			ClientController.incomingAPI().incomingCommand(new EndRoundAction());
		}
		ClientController.outgoingAPI().outgoingCommand(new EndTurnAction(GameStatus.me.getPosition()));
		
	}

	@Override
	public void onDropHandler(CardLogic card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCard(CardLogic card) {
		// TODO Auto-generated method stub
		
	}

}
