package war.droppables;

import carddeckplatform.game.GameStatus;
import logic.card.CardLogic;
import logic.client.GameLogic;
import logic.client.LogicDroppable;

public class PlayerAreaLogic extends LogicDroppable{

	public PlayerAreaLogic(int id,Type type) {
		super(id,type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onClickHandler() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDropHandler(CardLogic card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCard(CardLogic card) {
		card.setRevealed(false);
		card.setMoveable(false);
		cards.push(card);
		
	}

}
