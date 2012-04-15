package blackJack.droppables;

import logic.card.CardLogic;
import logic.client.LogicDroppable;

public class DeckAreaLogic extends LogicDroppable{

	public DeckAreaLogic(int id, Type type) {
		super(id, type);
		
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
		card.setMoveable(true);
		card.setRevealed(false);
		
	}

}
