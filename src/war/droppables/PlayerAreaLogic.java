package war.droppables;

import logic.card.CardLogic;
import logic.client.LogicDroppable;

public class PlayerAreaLogic extends LogicDroppable{

	public PlayerAreaLogic(int id) {
		super(id);
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
