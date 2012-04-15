package freeplay.droppables;

import logic.card.CardLogic;
import logic.client.LogicDroppable;

public class DeckAreaLogic extends LogicDroppable {

	public DeckAreaLogic(int id, Type type) {
		super(id, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onClickHandler() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDropHandler(CardLogic card) {
		// TODO Auto-generated method stub
		addCard(card);
	}

	@Override
	public void addCard(CardLogic card) {
		// TODO Auto-generated method stub
		card.setRevealed(false);
		card.setMoveable(true);
	}

}
