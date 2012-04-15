package blackJack.droppables;

import carddeckplatform.game.GameStatus;
import client.controller.ClientController;
import client.controller.actions.EndTurnAction;
import logic.card.CardLogic;
import logic.client.LogicDroppable;

public class PressButtonLogic extends LogicDroppable{

	public PressButtonLogic(int id, Type type) {
		super(id, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onClickHandler() {
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
