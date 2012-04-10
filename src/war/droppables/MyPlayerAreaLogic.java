package war.droppables;

import logic.card.CardLogic;

public class MyPlayerAreaLogic extends PlayerAreaLogic {

	public MyPlayerAreaLogic(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void addCard(CardLogic card) {
		card.setMoveable(true);
		cards.push(card);
	}
}
